lazy val _scalaVersion = "2.11.8"
lazy val doctusVersion = "1.0.6-SNAPSHOT"
lazy val mockitoVersion = "1.9.5"
lazy val utestVersion = "0.4.1"
lazy val scalaJsDomJqueryVersion = "0.9.0"

lazy val commonSettings = 
  Seq(
    version := doctusVersion,
    scalaVersion := _scalaVersion,
    organization := "net.entelijan",
    organizationHomepage := Some(url("http://entelijan.net/")),
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    EclipseKeys.withSource := true)
    
lazy val coreSettings =
  commonSettings ++
    Seq(
      libraryDependencies += "com.lihaoyi" %%% "utest" % utestVersion % "test",
      libraryDependencies += "org.scala-lang" % "scala-reflect" % _scalaVersion,
      testFrameworks += new TestFramework("utest.runner.Framework"))

lazy val showcaseSettings =
  coreSettings

lazy val swingSettings =
  coreSettings ++
    Seq(
      libraryDependencies += "commons-lang" % "commons-lang" % "2.6" % "test",
      fork := true,
      testFrameworks += new TestFramework("utest.runner.Framework"))

lazy val scalajsSettings =
  coreSettings ++
    Seq(
      jsDependencies += RuntimeDOM,
      libraryDependencies += "org.scala-js" %%% "scalajs-dom" % scalaJsDomJqueryVersion,
      libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % scalaJsDomJqueryVersion,
      testFrameworks += new TestFramework("utest.runner.Framework"))

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "doctus-root")
  .aggregate(
    core,
    swing,
    scalajs,
    core_showcase,
    swing_showcase, 
    scalajs_showcase)

lazy val core = (project in file("core"))
  .settings(coreSettings: _*)
  .settings(
    name := "doctus-core")
  .enablePlugins(ScalaJSPlugin)

lazy val swing = (project in file("swing"))
  .settings(swingSettings: _*)
  .settings(
    name := "doctus-swing")
  .dependsOn(core)

lazy val scalajs = (project in file("scalajs"))
  .settings(scalajsSettings: _*)
  .settings(
    name := "doctus-scalajs")
  .dependsOn(core)
  .enablePlugins(ScalaJSPlugin)

lazy val core_showcase = (project in file("core-showcase"))
  .settings(showcaseSettings: _*)
  .settings(
    name := "doctus-core-showcase")
  .dependsOn(core)
  .enablePlugins(ScalaJSPlugin)

lazy val scalajs_showcase = (project in file("scalajs-showcase"))
  .settings(showcaseSettings: _*)
  .settings(
    name := "doctus-scalajs-showcase")
  .dependsOn(core_showcase, scalajs)
  .enablePlugins(ScalaJSPlugin)

lazy val swing_showcase = (project in file("swing-showcase"))
  .settings(showcaseSettings: _*)
  .settings(
    name := "doctus-swing-showcase")
  .dependsOn(core_showcase, swing)

    
