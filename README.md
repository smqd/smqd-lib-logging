
[![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/https/oss.sonatype.org/com.thing2x/smqd-lib-logging_2.12.svg)](https://oss.sonatype.org/content/groups/public/com/thing2x/smqd-lib-logging_2.12/)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/https/oss.sonatype.org/com.thing2x/smqd-lib-logging_2.12.svg)](https://oss.sonatype.org/content/groups/public/com/thing2x/smqd-lib-logging_2.12/)
[![Actions Status](https://github.com/smqd/smqd-lib-logging/workflows/CI%20build/badge.svg)](https://github.com/smqd/smqd-lib-logging/actions)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

## Source code file name and line number aware logging

```sbtshell
libraryDependencies += "com.thing2x" %% "smqd-lib-logging" % "1.0.0-SNAPSHOT"
```

```scala
import com.thing2x.smqd.logging.LoggingBase

class MacroTest extends LoggingBase {
  logger.info("Hello World!")
}
```

The log message will include file name and line number automatically,
This information is obtained by  scala macro during compile time, runtime performance is not affect.
 
```
10:27:07.153 INFO  com.example.MacroTest | MacroTest.scala 4 | Hello World!
```