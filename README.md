# Kotlin Examples

## Kotlin

<https://kotlinlang.org/>

## Make a kotlin project

``` shell
mkdir hello
cd hello
gradle init --type kotlin-application --dsl kotlin
```

## Common Issue

> Gradle requires JVM 17 or later to run. Your build is currently configured to use JVM 8.

### Download JDK

<https://www.oracle.com/kr/java/technologies/downloads/>

### get JAVA_HOME

``` shell
java -XshowSettings:properties -version
```

find `java.home`

### set JAVA_HOME (temporary)

Emacs shell

``` shell
setenv JAVA_HOME `<path>`
```

e.g.)

``` shell
setenv JAVA_HOME "C:\\Program Files\\Java\\jdk-21"
```

Powershell

``` shell
$env:JAVA_HOME = "C:\\Program Files\\Java\\jdk-21"
```
