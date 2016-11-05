# calculator
A command line application performing arithmetical operation described in an external file.

## How to run the application

The application is based on *Spring* *Boot*. It uses several external libraries. Exception the *Spring*
 it depends on *SLF4J* (Simple Logging Framework for Java) and *Logback* (Logging provider)*[]: 
 
All of the dependencies are packed in the jar file. So the only jar that is needed to run the application 
is the jar of the application itself.

Use the following command to start the application:

```java
java -jar Calculator-1.0-SNAPSHOT.jar --input=instructions.txt
```

## Mandatory parameters

    * `input` whose value is a path to a file containing arithmetical instructions
    
## Optional parameters
    
The application is based on *Spring* *Boot* so there are many others parameters automatically available.
    
    * `logging.level.root=<LEVEL>` enables printing additional info from loggers enabled for log level <LEVEL> which can be TRACE, DEBUG, INFO, WARN, ERROR or FATAL.
    * `--debugz is similar to previous one but allows printing Spring internal messages regarding configuration of the app and many other types of messages 

## Format of an external file

A list of instructions. Every instruction is placed on separate line consisting of two 
parts:

    * instruction - can be any of the following operations: add, subtract, divide, multiply
    * operand - any integer number
    
The last line of the file must be a special command - `apply` followed by an integer that
represents a starting number that will be used as a first operand of the arithmetic instruction
on the firsr line. Result of the operation is used as an operand of the second instruction etc.
until one operand remains which is the final result.