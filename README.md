
## Source code file name and line number aware logging

```sbtshell
libraryDependencies ++= Seq(
    "com.thing2x" % "smqd-lib-logging" % "0.1.0-SNAPSHOT",
    "org.scala-lang" % "scala-reflect" % "2.12.8",
    "ch.qos.logback" % "logback-classic" % "1.2.3"
)
```

```scala
import com.thing2x.smqd.logging.SourcePositionLogging

class MacroTest extends SourcePositionLogging {
  logger.info("Hello World!")
}
```

The log message will include file name and line number automatically,
Those information is obtained by  scala macro during compile time, runtime performance is not affect.
 
```
10:27:07.153 INFO  com.example.MacroTest | MacroTest.scala 4 | Hello World!
```