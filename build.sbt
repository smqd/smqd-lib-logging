
val versionString = "0.1.0-SNAPSHOT"
val scalaVersionString = "2.12.8"

name := "smqd-lib-logging"

version := versionString

scalaVersion := scalaVersionString

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.12.8" % Provided,
  "org.slf4j" % "slf4j-api" % "1.7.26" % Provided,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
)