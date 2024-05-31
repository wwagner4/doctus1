ThisBuild / scalaVersion := "2.13.14"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

lazy val javaVersion = "17" // Info for scalac in order to optimise
lazy val utestVersion = "0.8.3"
lazy val scalaJsJqueryVersion = "1.0.0"
lazy val doctusVersion = "1.1.0-SNAPSHOT"
lazy val organisation = "net.entelijan"

lazy val doctus = project.in(file(".")).
  aggregate(
    doctusCore.js,
    doctusCore.jvm,
  )

lazy val doctusCore = crossProject(JSPlatform, JVMPlatform).in(file("core")).
  settings(
    name := "doctus-core",
    organization := organisation,
    version := doctusVersion,
    organizationHomepage := Some(url("http://entelijan.net/")),
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    testFrameworks += new TestFramework("utest.runner.Framework"),
    scalacOptions ++= Seq(
      "-Wunused",
      // "-deprecation",
    )

  ).
  jvmSettings(
    libraryDependencies += "com.lihaoyi" %% "utest" % utestVersion % "test",
    javacOptions ++= Seq("-source", javaVersion, "-target", javaVersion),
    fork := true,

  ).
  jsSettings(
    libraryDependencies += "com.lihaoyi" %%% "utest" % utestVersion % "test",
    libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % scalaJsJqueryVersion,
  )

lazy val showcase = crossProject(JSPlatform, JVMPlatform).in(file("showcase")).
  settings(
    name := "doctus-showcase",
    organization := organisation,
    version := doctusVersion,
    testFrameworks += new TestFramework("utest.runner.Framework"),
  ).
  jvmSettings(
    libraryDependencies += organisation %% "doctus-core" % doctusVersion,
    fork := true,
    libraryDependencies += "com.lihaoyi" %% "utest" % utestVersion % "test",
    scalacOptions ++= Seq(
      "-Wunused",
      // "-deprecation",
    ),
  ).
  jsSettings(
    libraryDependencies += organisation %%% "doctus-core" % doctusVersion,
    libraryDependencies += "com.lihaoyi" %%% "utest" % utestVersion % "test",
  )
