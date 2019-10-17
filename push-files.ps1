# This is an example Windows Powershell script to push files to a funnel.travel webhook.
# The file shows how to select files older than a few minutes, filter the content, then
# push the matching files and finally move all files to a 'done' folder.
#
# The check on age is important as there can be issues processing files which are still in 
# the process of being written by the GDS PNR printer.
#
# WaNT GmbH (no copyright, feel free to copy and adapt)

Set-Variable BaseDirectory -value "C:\printer\pnrs" -option Constant
Set-Variable MinAgeInMinutes -value 2 -option Constant
Set-Variable ContentFilterExpression -value "VISA" -option Constant 
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
    if ($pnr.CreationTime -lt $MaxTimestamp)
    {
        Write-Host "Checking content of $pnr"
        $FileContent = Get-Content "$BaseDirectory\$pnr"
        if (($FileContent | %{$_ -match $ContentFilterExpression}) -contains $true)
        {
            Write-Host ".. Posting $pnr"
            # Powershell doesn't handle multipart very well, so create the body manually
            $boundary = [System.Guid]::NewGuid().ToString(); 

            $bodyLines = ( 
                "--$boundary",
                "Content-Disposition: form-data; name=`"file`"; filename=`"$pnr`"",
                "Content-Type: application/octet-stream$LF",
                $FileContent,
                "--$boundary--$LF" 
            ) -join $LF
            $Response = Invoke-RestMethod -Uri $WebhookUrl -Method Post -ContentType "multipart/form-data; boundary=`"$boundary`"" -Body $bodyLines
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
