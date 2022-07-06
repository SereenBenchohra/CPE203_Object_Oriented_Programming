echo "This command file might work to build and run on Windows."
set LIBS="%MATERIALS_DIR_203%\lib\spritely.jar;%MATERIALS_DIR_203%\lib\testy.jar"

rem
rem Compile the program:
rem
mkdir out
javac -Xlint:unchecked -Xmaxerrs 5 -sourcepath src  -cp %LIBS% -d out src\*.java
java -cp "%LIBS%;out" -ea Main %1 %2 %3 %4 %5 %6 %7 %8 %9
