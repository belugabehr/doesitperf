# Does It Perf?

This project is a collection of Java JMH collections testing the mundane.

## Motivation

From my experience, speed (a.k.a performance) is rarely a top contender when it comes to most important aspect of good programming. With the rise of cloud computing, the typical answer to performance bottlenecks is simply to scale. Optimizing an application for performance is incredibly expensive in terms of engineering time and requires engineers with specialized knowledge. The most optimal way to reduce the execution time of an application is simply to scale. When one can magically summon yet another compute node for dollars (or pennies) a day, there is no need to burn engineering time on performance. 

There will always be use cases that do not scale in this fashion, but they are becomes the exception, not the norm. For example, Google spent a considerable amount of time optimizing their big data storage system's central metadata repository.  However, the repository has since been replaced with a sharded system that expands at linear scale. 

Software developers should be focused on increased readability, improving testability, reducing ambiguity, documenting, and generally improving the idiot-proof-ness of their code.

With all that said, I've received a lot of push-back over the years when I provide patches which improve the everyday lives of application developers but may negatively impact performance. Even if one does not (yet) believe in the simplicity of scaling for performance, it is helpful to have some data when discussing these things.

## JMH

JMH is a Java harness for building, running, and analysing nano/micro/milli/macro benchmarks written in Java and other languages targetting the JVM.

[Code Tools: JMH](https://openjdk.java.net/projects/code-tools/jmh/)

## Test Suites

* Base64 Encoding / Decoding
* List Insertion and Iteration
* Queue Insertion
* Converting Java Collection to Native Array

## Requirements
1. Java 1.8
2. Maven

## Additional Reading

* [Avoid Using Null Values](https://github.com/google/guava/wiki/UsingAndAvoidingNullExplained)
* [When to Use ArrayList over LinkedList](https://stackoverflow.com/questions/322715/when-to-use-linkedlist-over-arraylist-in-java)