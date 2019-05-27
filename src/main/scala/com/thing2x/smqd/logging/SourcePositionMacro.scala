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

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object SourcePositionMacro {

  def line(c: blackbox.Context): c.Expr[Int] = {
    import c.universe._
    val line = Literal(Constant(c.enclosingPosition.line))
    c.Expr[Int](line)
  }

  def file(c: blackbox.Context): c.Expr[String] = {
    import c.universe._
    val path = Literal(Constant(c.enclosingPosition.source.file.file.getName))
    c.Expr[String](path)
  }

  type LoggerContext = blackbox.Context { type PrefixType = SourcePositionLogger }

  ////////////////////////////////////////////////////////
  // Error
  def errorMessage(c: LoggerContext)(message: c.Expr[String]): c.universe.Tree = {
    val (messageFormat, args) = deconstructInterpolatedMessage(c)(message)
    errorMessageArgs(c)(messageFormat, args: _*)
  }

  def errorMessageCause(c: LoggerContext)(message: c.Expr[String], cause: c.Expr[Throwable]): c.universe.Tree = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $underlying.error($message, $cause)"
  }

  def errorMessageArgs(c: LoggerContext)(message: c.Expr[String], args: c.Expr[Any]*): c.universe.Tree = {
    import c.universe._
    val msg = q"""s"| $$FILE $$LINE | "+$message"""
    val underlying = q"${c.prefix}.underlying"
    val anyRefArgs = formatArgs(c)(args: _*)
    if (args.length == 2)
      q"if ($underlying.isErrorEnabled) $underlying.error($msg, _root_.scala.Array(${anyRefArgs.head}, ${anyRefArgs(1)}): _*)"
    else
      q"if ($underlying.isErrorEnabled) $underlying.error($msg, ..$anyRefArgs)"
  }

  def errorCode(c: LoggerContext)(body: c.Expr[Unit]): c.universe.Tree = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isErrorEnabled) $body"
  }

  ////////////////////////////////////////////////////////
  // Warn
  def warnMessage(c: LoggerContext)(message: c.Expr[String]): c.universe.Tree = {
    val (messageFormat, args) = deconstructInterpolatedMessage(c)(message)
    warnMessageArgs(c)(messageFormat, args: _*)
  }

  def warnMessageCause(c: LoggerContext)(message: c.Expr[String], cause: c.Expr[Throwable]): c.universe.Tree = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isWarnEnabled) $underlying.warn($message, $cause)"
  }

  def warnMessageArgs(c: LoggerContext)(message: c.Expr[String], args: c.Expr[Any]*): c.universe.Tree = {
    import c.universe._
    val msg = q"""s"| $$FILE $$LINE | "+$message"""
    val underlying = q"${c.prefix}.underlying"
    val anyRefArgs = formatArgs(c)(args: _*)
    if (args.length == 2)
      q"if ($underlying.isWarnEnabled) $underlying.warn($msg, _root_.scala.Array(${anyRefArgs.head}, ${anyRefArgs(1)}): _*)"
    else
      q"if ($underlying.isWarnEnabled) $underlying.warn($msg, ..$anyRefArgs)"
  }

  def warnCode(c: LoggerContext)(body: c.Expr[Unit]): c.universe.Tree = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isWarnEnabled) $body"
  }

  ////////////////////////////////////////////////////////
  // Info
  def infoMessage(c: LoggerContext)(message: c.Expr[String]): c.universe.Tree = {
    val (messageFormat, args) = deconstructInterpolatedMessage(c)(message)
    infoMessageArgs(c)(messageFormat, args: _*)
  }

  def infoMessageCause(c: LoggerContext)(message: c.Expr[String], cause: c.Expr[Throwable]): c.universe.Tree = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isInfoEnabled) $underlying.info($message, $cause)"
  }

  def infoMessageArgs(c: LoggerContext)(message: c.Expr[String], args: c.Expr[Any]*): c.universe.Tree = {
    import c.universe._
    val msg = q"""s"| $$FILE $$LINE | "+$message"""
    val underlying = q"${c.prefix}.underlying"
    val anyRefArgs = formatArgs(c)(args: _*)
    if (args.length == 2)
      q"if ($underlying.isInfoEnabled) $underlying.info($msg, _root_.scala.Array(${anyRefArgs.head}, ${anyRefArgs(1)}): _*)"
    else
      q"if ($underlying.isInfoEnabled) $underlying.info($msg, ..$anyRefArgs)"
  }

  def infoCode(c: LoggerContext)(body: c.Expr[Unit]): c.universe.Tree = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isInfoEnabled) $body"
  }

  ////////////////////////////////////////////////////////
  // Debug
  def debugMessage(c: LoggerContext)(message: c.Expr[String]): c.universe.Tree = {
    val (messageFormat, args) = deconstructInterpolatedMessage(c)(message)
    debugMessageArgs(c)(messageFormat, args: _*)
  }

  def debugMessageCause(c: LoggerContext)(message: c.Expr[String], cause: c.Expr[Throwable]): c.universe.Tree = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isDebugEnabled) $underlying.debug($message, $cause)"
  }

  def debugMessageArgs(c: LoggerContext)(message: c.Expr[String], args: c.Expr[Any]*): c.universe.Tree = {
    import c.universe._
    val msg = q"""s"| $$FILE $$LINE | "+$message"""
    val underlying = q"${c.prefix}.underlying"
    val anyRefArgs = formatArgs(c)(args: _*)
    if (args.length == 2)
      q"if ($underlying.isDebugEnabled) $underlying.debug($msg, _root_.scala.Array(${anyRefArgs.head}, ${anyRefArgs(1)}): _*)"
    else
      q"if ($underlying.isDebugEnabled) $underlying.debug($msg, ..$anyRefArgs)"
  }

  def debugCode(c: LoggerContext)(body: c.Expr[Unit]): c.universe.Tree = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isDebugEnabled) $body"
  }

  ////////////////////////////////////////////////////////
  // Trace

  def traceMessage(c: LoggerContext)(message: c.Expr[String]): c.universe.Tree = {
    val (messageFormat, args) = deconstructInterpolatedMessage(c)(message)
    traceMessageArgs(c)(messageFormat, args: _*)
  }

  def traceMessageCause(c: LoggerContext)(message: c.Expr[String], cause: c.Expr[Throwable]): c.universe.Tree = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isTraceEnabled) $underlying.trace($message, $cause)"
  }

  def traceMessageArgs(c: LoggerContext)(message: c.Expr[String], args: c.Expr[Any]*): c.universe.Tree = {
    import c.universe._
    val msg = q"""s"| $$FILE $$LINE | "+$message"""
    val underlying = q"${c.prefix}.underlying"
    val anyRefArgs = formatArgs(c)(args: _*)
    if (args.length == 2)
      q"if ($underlying.isTraceEnabled) $underlying.trace($msg, _root_.scala.Array(${anyRefArgs.head}, ${anyRefArgs(1)}): _*)"
    else
      q"if ($underlying.isTraceEnabled) $underlying.trace($msg, ..$anyRefArgs)"
  }

  def traceCode(c: LoggerContext)(body: c.Expr[Unit]): c.universe.Tree = {
    import c.universe._
    val underlying = q"${c.prefix}.underlying"
    q"if ($underlying.isTraceEnabled) $body"
  }

  /** Checks whether `message` is an interpolated string and transforms it into SLF4J string interpolation. */
  private def deconstructInterpolatedMessage(c: LoggerContext)(message: c.Expr[String]): (c.Expr[String], Seq[c.Expr[Any]]) = {
    import c.universe._

    message.tree match {
      case q"scala.StringContext.apply(..$parts).s(..$args)" =>
        val format = parts.iterator.map({ case Literal(Constant(str: String)) => str })
          // Emulate standard interpolator escaping
          .map(StringContext.treatEscapes)
          // Escape literal slf4j format anchors if the resulting call will require a format string
          .map(str => if (args.nonEmpty) str.replace("{}", "\\{}") else str)
          .mkString("{}")

        val formatArgs: Seq[c.Expr[Any]] = args.map(t => c.Expr[Any](t))

        (c.Expr(q"$format"), formatArgs)

      case _ => (message, Seq.empty)
    }
  }

  private def formatArgs(c: LoggerContext)(args: c.Expr[Any]*) = {
    import c.universe._
    args.map { arg =>
      c.Expr[AnyRef](if (arg.tree.tpe <:< weakTypeOf[AnyRef]) arg.tree else q"$arg.asInstanceOf[_root_.scala.AnyRef]")
    }
  }
}
