
val versionString = "0.1.0-SNAPSHOT"
val scalaVersionString = "2.12.8"

lazy val root = (project in file("."))
  .settings(
    name := "smqd-lib-logging",
    organization := "com.thing2x",
    version := versionString,
    scalaVersion := scalaVersionString,
    scalacOptions ++= Seq("-feature", "-deprecation", "-unchecked")
  )
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % "2.12.8" % Provided,
      "org.slf4j" % "slf4j-api" % "1.7.26" % Provided,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2" % Test,
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "ch.qos.logback" % "logback-classic" % "1.2.3" % Test,
    )
  )
  .settings(
    // Publishing
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org",
      sys.env.getOrElse("SONATYPE_USER", ""), sys.env.getOrElse("SONATYPE_PASS", "")),
    homepage := Some(url("https://github.com/smqd/")),
    scmInfo := Some(ScmInfo(url("https://github.com/smqd/smqd-lib-logging"), "scm:git@github.com:smqd/smqd-lib-logging.git")),
    developers := List(
      Developer("OutOfBedlam", "Kwon, Yeong Eon", sys.env.getOrElse("SONATYPE_DEVELOPER_0", ""), url("http://www.uangel.com"))
    ),
    publishArtifact in Test := false, // Not publishing the test artifacts (default)
    publishMavenStyle := true
  )
  .settings(
    // PGP signing
    pgpPublicRing := file("./travis/local.pubring.asc"),
    pgpSecretRing := file("./travis/local.secring.asc"),
    pgpPassphrase := sys.env.get("PGP_PASS").map(_.toArray),
    useGpg := false
  )
  .settings(
    // License
    organizationName := "UANGEL",
    startYear := Some(2019),
    licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt")),
    headerMappings := headerMappings.value + (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment),
    headerMappings := headerMappings.value + (HeaderFileType.conf -> HeaderCommentStyle.hashLineComment)
  ).enablePlugins(AutomateHeaderPlugin)
