# JOAk ClassFiles
A micro library that lets you read in Java .class files.  It's written in Scala and can therefore be used in any JVM project (Scala, Kotlin, Java, Clojure etc.).

The results are clean, intuitive Scala case classes.  All code is stored as easily-readable opcodes case classes, allowing you to, for instance, build your own JVM on top of this.

## Supported Java Versions
Java versions up to and including 6 are 'officially' supported, but in practice it should do something useful with any .class file from any Java version.

But features that only exist in versions of Java later than 6 will be stored as opaque blocks of bytes, rather than useful case classes.

## Usage
Clone this project into yours.

The simplest possible example:
```
    import java.io.File
    import jvmclass.JVMClassFileReader.ReadParams

    val filename = "com/example/MyClass.class"
    val (clsName: String, packageName: Option[String]) = jvmclass.ClassNameHandler.split(filename)
    val file = new File(filename)

    JVMClassFileReader.read(packageName, clsName, file, ReadParams(verbose = true)) match {
      case Some(classFile) =>
        println(s"Successfully read $filename")
      // Do something with classFile
      case _ =>
        println(s"Failed to read $filename")
    }
```

Provide verbose=false if you don't want each line output to stdout.

The resultant ```JVMClassFile``` is a clean obvious mapping to version 6 of the [JVM .class file format](https://docs.oracle.com/javase/specs/jvms/se6/html/ClassFile.doc.html).  I'm assuming that anyone interested in this library is or is willing to be very familiar with that spec, and as it maps pretty much one-to-one no additional documentation is provided as it would be superfluous.  No, YOU'RE an excuse to get out of writing docs. 