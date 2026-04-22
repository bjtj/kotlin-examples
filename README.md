# Kotlin Examples

## Kotlin

<https://kotlinlang.org/>


## Common Issue

> Gradle requires JVM 17 or later to run. Your build is currently configured to use JVM 8.

### Download JDK

<https://www.oracle.com/kr/java/technologies/downloads/>

### get JAVA_HOME

``` shell
java -XshowSettings:properties -version
```

find `java.home`

### set JAVA_HOME

e.g.) Emacs

setenv JAVA_HOME `<path>`

``` shell
setenv JAVA_HOME "C:\\Program Files\\Java\\jdk-21"
```

clean

``` shell
./gradlew clean
```

