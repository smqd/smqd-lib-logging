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

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply

import java.util.concurrent.atomic.AtomicLong

object LogLevelCounter {
  val warnings = new AtomicLong()
  val errors = new AtomicLong()
}

/**
 * configuration example
 *
 * {{{
 * <configuration>
 * <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
 *
 *   <filter class="com.thing2x.smqd.logging.LogLevelCounter" />
 *
 *   <encoder>
 *     <pattern>%-4relative [%thread] %-5level %logger - %msg%n</pattern>
 *   </encoder>
 * </appender>
 * </configuration>
 * }}}
 */
class LogLevelCounter extends Filter[ILoggingEvent]{
  override def decide(event: ILoggingEvent): FilterReply = {
    event.getLevel match {
      case Level.WARN =>  LogLevelCounter.warnings.incrementAndGet()
      case Level.ERROR => LogLevelCounter.errors.incrementAndGet()
      case _ =>
    }
    FilterReply.NEUTRAL
  }
}

