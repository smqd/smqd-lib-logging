
val versionString = "0.1.2-SNAPSHOT"

val scala_2_12 = "2.12.16"
val scala_2_13 = "2.13.8"

val scalaVersionString = scala_2_12

lazy val root = (project in file("."))
  .settings(
    name := "smqd-lib-logging",
    organization := "com.thing2x",
    project / version := versionString,
    ThisBuild / versionScheme := Some("early-semver"),
    scalaVersion := scalaVersionString,
    scalacOptions ++= Seq("-feature", "-deprecation", "-unchecked"),
    crossScalaVersions := Seq(scala_2_12, scala_2_13)
  )
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersionString % Provided,
      "org.slf4j" % "slf4j-api" % "1.7.36" % Provided,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5" % Test,
      "org.scalatest" %% "scalatest" % "3.2.12" % Test,
      "ch.qos.logback" % "logback-classic" % "1.2.11" % Provided,
    )
  )
  .settings(
    homepage := Some(url("https://github.com/smqd/")),
    developers := List(
      Developer("OutOfBedlam", "Kwon, Yeong Eon", sys.env.getOrElse("SONATYPE_DEVELOPER_0", ""), url("http://www.uangel.com"))
    ),
    ThisBuild / dynverSonatypeSnapshots := true,
    Test / publishArtifact := false, // Not publishing the test artifacts (default)
  )
  .settings(
    // License
    organizationName := "UANGEL",
    startYear := Some(2019),
    licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt")),
    headerMappings := headerMappings.value + (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment),
    headerMappings := headerMappings.value + (HeaderFileType.conf -> HeaderCommentStyle.hashLineComment)
  ).enablePlugins(AutomateHeaderPlugin)
