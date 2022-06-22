
name := "smqd-lib-logging"

organization := "com.thing2x"

version := "1.0.0"

//////////////////////////////////////////////////////
// scala
val scala_2_12 = "2.12.16"
val scala_2_13 = "2.13.8"

crossScalaVersions := Seq(scala_2_12, scala_2_13)

scalaVersion := scala_2_12

//////////////////////////////////////////////////////
// dependencies
libraryDependencies ++= Seq(
  /// macro
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  /// logging
  "org.slf4j" % "slf4j-api" % "1.7.36",
  "ch.qos.logback" % "logback-classic" % "1.2.11",
  /// test
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5" % Test,
  "org.scalatest" %% "scalatest" % "3.2.12" % Test,
)

//////////////////////////////////////////////////////
// scala options
scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-Xlint:nullary-unit",
  "-Xlint:inaccessible",
  "-Xlint:infer-any",
  "-Xlint:missing-interpolator",
  "-Xlint:doc-detached",
  "-Xlint:private-shadow",
  "-Xlint:type-parameter-shadow",
  "-Xlint:poly-implicit-overload",
  "-Xlint:option-implicit",
  "-Xlint:delayedinit-select",
  "-Xlint:package-object-classes",
  "-Xlint:stars-align",
  "-Xlint:constant",
  "-Xlint:unused",
)

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 13)) => Seq("-Ymacro-annotations")
    case Some((2, 12)) => Seq("-Xlint:nullary-override")
    case _ => Nil
  }
}

//////////////////////////////////////////////////////
// publish
homepage := Some(url("https://github.com/smqd/"))
developers := List(
  Developer("OutOfBedlam", "Kwon, Yeong Eon", sys.env.getOrElse("SONATYPE_DEVELOPER_0", ""), url("http://www.uangel.com"))
)
ThisBuild / versionScheme := Some("early-semver")
ThisBuild / dynverSonatypeSnapshots := true
Test / publishArtifact := false // Do not publish test artifacts (default)

//////////////////////////////////////////////////////
// license header
enablePlugins(AutomateHeaderPlugin)

organizationName := "UANGEL"
startYear := Some(2019)
licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
headerMappings := headerMappings.value + (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment)
headerMappings := headerMappings.value + (HeaderFileType.conf -> HeaderCommentStyle.hashLineComment)
