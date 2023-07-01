set miditra=cd
set mivoka=cd..
@REM set chemin1=.\Framework\
set package=./etu2029
set name_jar=framework
set chemin2="../Test framework/lib"

@REM %miditra% %chemin1%
javac -d . *.java
jar -cf %name_jar%.jar %package%
xcopy %name_jar%.jar %chemin2%


