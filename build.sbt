lazy val _scalaVersion = "2.13.13"
lazy val doctusVersion = "1.0.7-SNAPSHOT"
lazy val utestVersion = "0.8.2"
lazy val scalaJsJqueryVersion = "1.0.0"
lazy val javaFxVersion = "21.0.2" // LTS
lazy val javaVersion = "17" // Info for scalac in order to optimise

lazy val commonSettings = 
  Seq(
    version := doctusVersion,
    scalaVersion := _scalaVersion,
    organization := "net.entelijan",
    organizationHomepage := Some(url("http://entelijan.net/")),
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  )
    
lazy val coreSettings =
  commonSettings ++
    Seq(
      libraryDependencies += "com.lihaoyi" %%% "utest" % utestVersion % "test",
      libraryDependencies += "org.scala-lang" % "scala-reflect" % _scalaVersion,
      testFrameworks += new TestFramework("utest.runner.Framework"),
    )

lazy val showcaseSettings =
  coreSettings

lazy val jvmSettings =
  coreSettings ++
    Seq(
      libraryDependencies ++= {
      // Determine OS version of JavaFX binaries
        val osName = System.getProperty("os.name") match {
          case n if n.startsWith("Linux")   => "linux"
          case n if n.startsWith("Mac")     => "mac"
          case n if n.startsWith("Windows") => "win"
          case _                            => throw new Exception("Unknown platform!")
        }
        Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
          .map(m => "org.openjfx" % s"javafx-$m" % javaFxVersion classifier osName)
      },
      javacOptions ++= Seq("-source", javaVersion, "-target", javaVersion),
      fork := true,
      testFrameworks += new TestFramework("utest.runner.Framework"))



lazy val scalajsSettings =
  coreSettings ++
    Seq(
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

    
