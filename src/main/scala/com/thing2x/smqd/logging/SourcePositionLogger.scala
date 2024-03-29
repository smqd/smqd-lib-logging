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

import com.thing2x.smqd.logging.SourcePositionMacro._
import org.slf4j.{LoggerFactory, Logger => Underlying}

import scala.language.experimental.macros

object SourcePositionLogger {
  def apply(underlying: Underlying, includeSourcePosition: Boolean): SourcePositionLogger =
    new SourcePositionLogger(underlying, includeSourcePosition)
  def apply(name: String, includeSourcePosition: Boolean): SourcePositionLogger =
    new SourcePositionLogger(LoggerFactory.getLogger(name), includeSourcePosition)
  def apply(clazz: Class[_], includeSourcePosition: Boolean): SourcePositionLogger =
    new SourcePositionLogger(LoggerFactory.getLogger(clazz), includeSourcePosition)
}

final class SourcePositionLogger private(val underlying: Underlying, val includeSourcePosition: Boolean )
  extends Serializable {
  // Error
  def error(message: String): Unit = macro errorMessage
  def error(message: String, cause: Throwable): Unit = macro errorMessageCause
  def error(message: String, args: Any*): Unit = macro errorMessageArgs
  def whenErrorEnabled(body: Unit): Unit = macro errorCode

  // Warn
  def warn(message: String): Unit = macro warnMessage
  def warn(message: String, cause: Throwable): Unit = macro warnMessageCause
  def warn(message: String, args: Any*): Unit = macro warnMessageArgs
  def whenWarnEnabled(body: Unit): Unit = macro warnCode

  // Info
  def info(message: String): Unit = macro infoMessage
  def info(message: String, cause: Throwable): Unit = macro infoMessageCause
  def info(message: String, args: Any*): Unit = macro infoMessageArgs
  def whenInfoEnabled(body: Unit): Unit = macro infoCode

  // Debug
  def debug(message: String): Unit = macro debugMessage
  def debug(message: String, cause: Throwable): Unit = macro debugMessageCause
  def debug(message: String, args: Any*): Unit = macro debugMessageArgs
  def whenDebugEnabled(body: Unit): Unit = macro debugCode

  // Trace
  def trace(message: String): Unit = macro traceMessage
  def trace(message: String, cause: Throwable): Unit = macro traceMessageCause
  def trace(message: String, args: Any*): Unit = macro traceMessageArgs
  def whenTraceEnabled(body: Unit): Unit = macro traceCode
}
