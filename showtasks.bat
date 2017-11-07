call runcrud
if "%ERRORLEVEL%" == "0" goto open
echo.
echo runcrud has errors - breaking work
goto fail

:open
start chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo There were errors.

:end
echo The end.