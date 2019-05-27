package com.thing2x.smqd.logging

import scala.language.experimental.macros

trait SourcePositionAware {
  def LINE: Int = macro SourcePositionMacro.line
  def FILE: String = macro SourcePositionMacro.file
}

