// Copyright 2019 UANGEL
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.thing2x.smqd.logging
import com.thing2x.smqd.logging.MacroTest.Demo
import com.typesafe.scalalogging.StrictLogging
import org.scalatest.flatspec.AnyFlatSpec

object Position extends SourcePositionAware {
  val str: String = s"$FILE $LINE"
}

object MacroTest {
  abstract class AbstractDemo extends SourcePositionLogging {
    def doDemo(): Unit
  }

  class Demo extends AbstractDemo {
    def doDemo(): Unit = {
      logger.info(s"($FILE $LINE) Demo logging")
    }
  }
}

class MacroTest extends AnyFlatSpec with SourcePositionLogging {

  "Macro" should "work" in {

    val str = Position.str
    assert(str.matches("MacroTest.scala\\s[0-9]+$"))

    logger.info(s"Position.str ==> $str")

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

  it should "work with duplicated logger" in {
    val demo = new DemoClass
    demo.invokeMe()
  }
}

abstract class DemoImpl extends StrictLogging {
  def invokeMe(): Unit
}

class DemoClass extends DemoImpl with SourcePositionAware {
  def invokeMe(): Unit = {
    logger.info(s"| $FILE $LINE | How to solve confliction with scala-logging issue")
  }
}