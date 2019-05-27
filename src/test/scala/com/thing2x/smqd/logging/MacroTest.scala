package com.thing2x.smqd.logging
import com.thing2x.smqd.logging.MacroTest.Demo
import org.scalatest.FlatSpec

object Position extends SourcePositionAware {
  val str: String = s"$FILE $LINE"
}

object MacroTest {
  abstract class AbstractDemo extends SourcePositionLogging {
    def doDemo(): Unit
  }

  class Demo extends AbstractDemo {
    def doDemo(): Unit = {
      logger.info(s"| $FILE $LINE | Demo logging")
    }
  }
}

class MacroTest extends FlatSpec with SourcePositionLogging {

  "Macro" should "work" in {

    val str = Position.str
    assert(str == "MacroTest.scala 6")

    println(s"==> $str")

    var n = 0
    logger.trace(s"some trace message ${n = n + 1; n}")
    logger.debug(s"debug log message ${n = n + 1; n}")
    logger.info(s"info log message ${n = n + 1; n}")
    logger.warn(s"warn log message ${n = n + 1; n}")
    logger.error(s"error log message ${n = n + 1; n}")
  }

  it should "work within class inherits conflicting" in {
    logger.info("starting demo")
    val demo = new Demo
    demo.doDemo()
    logger.info("finish demo")
  }
}
