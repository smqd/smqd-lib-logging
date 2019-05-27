package com.thing2x.smqd.logging

import com.thing2x.smqd.logging.SourcePositionMacro._
import org.slf4j.{LoggerFactory, Logger => Underlying}

import scala.language.experimental.macros

object SourcePositionLogger {
  def apply(underlying: Underlying): SourcePositionLogger = new SourcePositionLogger(underlying)
  def apply(name: String): SourcePositionLogger = new SourcePositionLogger(LoggerFactory.getLogger(name))
  def apply(clazz: Class[_]): SourcePositionLogger = new SourcePositionLogger(LoggerFactory.getLogger(clazz))
}

final class SourcePositionLogger private(val underlying: Underlying ) extends Serializable {
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
