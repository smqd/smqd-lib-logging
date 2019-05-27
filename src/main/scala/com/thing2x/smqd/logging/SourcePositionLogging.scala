package com.thing2x.smqd.logging

import org.slf4j.LoggerFactory

trait SourcePositionLogging extends SourcePositionAware {
  protected val logger: SourcePositionLogger =
    SourcePositionLogger(LoggerFactory.getLogger(getClass.getName))
}
