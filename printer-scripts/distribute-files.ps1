# This is an example Windows Powershell script to take files created by a file printer
# such as the Amadeus ProPrinter, and copy/move them to a configurable target directory.
#
# If ExecutionPolicy is an issue, the script could be executed with
# >Powershell.exe -ExecutionPolicy ByPass distribute-files.ps1
#
# WaNT GmbH (no copyright, feel free to copy and adapt)

Set-Variable BaseDirectory -value "C:\proprinter\airs" -option Constant
Set-Variable TrashDirectory -value "C:\proprinter\funneltravel\unassigned" -option Constant
Set-Variable Logfile -value "C:\proprinter\logs\distribute.log"
Set-Variable FileMustEndWith -value "END" -option Constant

Set-Variable TargetDirectories -value @{}
$TargetDirectories.Add('ZRH12345A', 'C:\proprinter\funneltravel\customer1')
$TargetDirectories.Add('ZRH12345B', 'C:\proprinter\funneltravel\customer2')
# end of configuration

Function LogWrite
{
   Param ([string]$logstring)
   $Stamp = (Get-Date).toString("yyyy-MM-dd HH:mm:ss")
   $today = (Get-Date).toString("yyyy-MM-dd")
   $LogfileForToday = $Logfile.replace(".log","-$today.log")
   Add-content $LogfileForToday -value "$Stamp $logstring"
}

New-Item -ItemType Directory -Force -Path "$TrashDirectory" | Out-Null

LogWrite "Distributing files from $BaseDirectory"
foreach ($pnr in Get-ChildItem $BaseDirectory -Attributes !Directory+!System)
{
    $FileContentLineArray = Get-Content "$BaseDirectory\$pnr"
    $LastLine = $FileContentLineArray[-1]
    if ($LastLine.Contains($FileMustEndWith))
    {
        $FoundMatch = $FALSE
        foreach($TargetSubstring in $TargetDirectories.keys)
        {
            if ($FileContentLineArray -match $TargetSubstring)
            {
                $TargetDirectory = $TargetDirectories.$TargetSubstring
                LogWrite "Moving $pnr to $TargetDirectory"
                $FoundMatch = $TRUE
                New-Item -ItemType Directory -Force -Path "$TargetDirectory" | Out-Null
                Move-Item "$BaseDirectory\$pnr" "$TargetDirectory"
            }
        }
        if (-Not $FoundMatch) 
        {
        	LogWrite "Skipping $pnr to $TrashDirectory"
            Move-Item "$BaseDirectory\$pnr" "$TrashDirectory"
        }
    }
}
