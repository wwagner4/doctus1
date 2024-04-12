ThisBuild / scalaVersion := "2.13.13"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

lazy val javaFxVersion = "21.0.2" // LTS
lazy val javaVersion = "17" // Info for scalac in order to optimise
lazy val utestVersion = "0.8.2"
lazy val scalaJsJqueryVersion = "1.0.0"
lazy val doctusVersion = "1.0.8-SNAPSHOT"
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
      "-Ywarn-unused",
      // "-deprecation",
    )

  ).
  jvmSettings(
    libraryDependencies += "com.lihaoyi" %% "utest" % utestVersion % "test",
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
  ).
  jvmSettings(
    libraryDependencies += organisation %% "doctus-core" % doctusVersion,
    fork := true,
  ).
  jsSettings(
    libraryDependencies += organisation %%% "doctus-core" % doctusVersion,
  )
