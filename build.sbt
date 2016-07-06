lazy val _scalaVersion = "2.11.8"
lazy val doctusVersion = "1.0.6-SNAPSHOT"
lazy val mockitoVersion = "1.9.5"
lazy val utestVersion = "0.4.1"
lazy val scalaJsDomVersion = "0.9.0"
lazy val scalaJsJqueryVersion = "0.9.0"

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

lazy val jvmSettings =
  coreSettings ++
    Seq(
      libraryDependencies += "commons-lang" % "commons-lang" % "2.6" % "test",
      fork := true,
      testFrameworks += new TestFramework("utest.runner.Framework"))

lazy val scalajsSettings =
  coreSettings ++
    Seq(
      jsDependencies += RuntimeDOM,
      libraryDependencies += "org.scala-js" %%% "scalajs-dom" % scalaJsDomVersion,
      libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % scalaJsJqueryVersion,
      testFrameworks += new TestFramework("utest.runner.Framework"))

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "doctus-root")
  .aggregate(
    core,
    jvm,
    scalajs,
    showcase_core,
    showcase_jvm, 
    showcase_scalajs)

lazy val core = (project in file("core"))
  .settings(coreSettings: _*)
  .settings(
    name := "doctus-core")
  .enablePlugins(ScalaJSPlugin)

lazy val jvm = (project in file("jvm"))
  .settings(jvmSettings: _*)
  .settings(
    name := "doctus-jvm")
  .dependsOn(core)

lazy val scalajs = (project in file("scalajs"))
  .settings(scalajsSettings: _*)
  .settings(
    name := "doctus-scalajs")
  .dependsOn(core)
  .enablePlugins(ScalaJSPlugin)

lazy val showcase_core = (project in file("showcase-core"))
  .settings(showcaseSettings: _*)
  .settings(
    name := "showcase-doctus-core")
  .dependsOn(core)
  .enablePlugins(ScalaJSPlugin)

lazy val showcase_scalajs = (project in file("showcase-scalajs"))
  .settings(showcaseSettings: _*)
  .settings(
    name := "showcase-doctus-scalajs")
  .dependsOn(showcase_core, scalajs)
  .enablePlugins(ScalaJSPlugin)

lazy val showcase_jvm = (project in file("showcase-jvm"))
  .settings(showcaseSettings: _*)
  .settings(
    name := "showcase-doctus-jvm")
  .dependsOn(showcase_core, jvm)

    
