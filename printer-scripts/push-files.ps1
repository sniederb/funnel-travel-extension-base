# This is an example Windows Powershell script to push files to a funnel.travel webhook.
# The file shows how to select files older than a few minutes, filter the content, then
# push the matching files and finally move all files to a 'done' folder.
#
# The check on age is important as there can be issues processing files which are still in 
# the process of being written by the GDS PNR printer.
#
# If ExecutionPolicy is an issue, the script could be executed with
# >Powershell.exe -ExecutionPolicy ByPass push-files.ps1
#
# WaNT GmbH (no copyright, feel free to copy and adapt)

Set-Variable BaseDirectory -value "C:\proprinter\airs" -option Constant
Set-Variable MinAgeInMinutes -value 2 -option Constant
# Unanimous = all substrings must match
Set-Variable UnanimousSubstrings -value @('UMB-TRAV', 'VISA/') -option Constant 
# Affirmative = at least one substring must match
Set-Variable AffirmativeSubstrings -value @(';ZRH12345A;', ';ZRH12345B;') -option Constant 
Set-Variable WebhookUrl -value "https://www.funnel.travel/p/be/publicapi/extension/webhook/eas/{extensionAccountSettingUuid}" -option Constant 
# end of configuration

Set-Variable MaxTimestamp -value $(Get-Date).AddMinutes(-$MinAgeInMinutes)
$LF = "`r`n";

New-Item -ItemType Directory -Force -Path "$BaseDirectory\sent" | Out-Null
New-Item -ItemType Directory -Force -Path "$BaseDirectory\error" | Out-Null
New-Item -ItemType Directory -Force -Path "$BaseDirectory\skipped" | Out-Null

Write-Host "Processing $BaseDirectory with a cutoff of $MaxTimestamp"
foreach ($pnr in Get-ChildItem $BaseDirectory -Attributes !Directory+!System)
{
    # this timestamp check prevents reading a PNR which is right now only being created by a ProPrinter or PrintManager
    if ($pnr.CreationTime -lt $MaxTimestamp)
    {
        Write-Host "Checking content of $pnr"
        $FileContent = Get-Content "$BaseDirectory\$pnr"
        $UnanimousVote = $true
        $UnanimousSubstrings | ForEach {
            $UnanimousVote = $UnanimousVote -and ($FileContent -match $_ )
        }
        $AffirmativeVote = $null -ne ($AffirmativeSubstrings | ? { $FileContent -match $_ })
        
        if ($UnanimousVote -and $AffirmativeVote)
        {
            Write-Host ".. Posting $pnr"
            $Response = Invoke-WebRequest -uri $WebhookUrl -Method Post -ContentType 'text/plain' -Infile "$BaseDirectory\$pnr" | ConvertFrom-Json
            if ($Response.messageCode -eq "OK")
            {
                Write-Host ".. $pnr was successfully uploaded"
                Move-Item "$BaseDirectory\$pnr" "$BaseDirectory\sent"
            }
            else 
            {
                Write-Host ".. ERROR: POST of $pnr failed: " + $Response.messageCode
                Move-Item "$BaseDirectory\$pnr" "$BaseDirectory\error"
            }
        } 
        else 
        {
            Write-Host ".. Skipping $pnr"
            Move-Item "$BaseDirectory\$pnr" "$BaseDirectory\skipped"
        }
    }
}
