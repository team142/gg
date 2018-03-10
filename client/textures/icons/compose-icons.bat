setlocal enableDelayedExpansion 

for %%f in (*.svg) do (
    echo ===========================  Exporting PNG for %%~nf ===========================
    inkscape --file="%%~nf.svg" --export-png="..\%%~nf.png" --export-width=1024 --export-height=1024 -C 
)
