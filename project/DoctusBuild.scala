import sbt._
import Keys._

import com.typesafe.sbteclipse.plugin.EclipsePlugin._

import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object DoctusBuild extends Build {

  object D {
    val scalaVersion = "2.11.8"
    val doctusVersion = "1.0.5-SNAPSHOT"
    val mockitoVersion = "1.9.5"
    val utestVersion = "0.4.1"
    val scalaJsDomJqueryVersion = "0.9.0"
  }

  object S {

    val keyfile = new java.io.File("/Users/wwagner4/.ssh/id_rsa")

    lazy val baseSettings: Seq[Def.Setting[_]] =
      Seq(
        scalaVersion := D.scalaVersion,
        organization := "net.entelijan",
        organizationHomepage := Some(url("http://entelijan.net/")),
        publishTo := Some(Resolver.sftp("entelijan", "entelijan.net", "/var/www/ivy2")(Resolver.ivyStylePatterns) as ("root", keyfile)),
        EclipseKeys.withSource := true)

    lazy val defaultSettings =
      baseSettings ++
        Seq(
          version := D.doctusVersion)

    lazy val coreSettings =
      defaultSettings ++
        Seq(
          libraryDependencies += "com.lihaoyi" %%% "utest" % D.utestVersion % "test",
          libraryDependencies += "org.scala-lang" % "scala-reflect" % D.scalaVersion,
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
          libraryDependencies += "org.scala-js" %%% "scalajs-dom" % D.scalaJsDomJqueryVersion,
          libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % D.scalaJsDomJqueryVersion,
          testFrameworks += new TestFramework("utest.runner.Framework"))
  }

  lazy val root = Project(
    id = "doctus-root",
    base = file("."),
    settings = S.defaultSettings)
    .aggregate(core, swing, scalajs, core_showcase, swing_showcase, scalajs_showcase)

  lazy val core = Project(
    id = "doctus-core",
    base = file("core"),
    settings = S.coreSettings)
    .enablePlugins(ScalaJSPlugin)

  lazy val swing = Project(
    id = "doctus-swing",
    base = file("swing"),
    settings = S.swingSettings)
    .dependsOn(core)

  lazy val scalajs = Project(
    id = "doctus-scalajs",
    base = file("scalajs"),
    settings = S.scalajsSettings)
    .dependsOn(core)
    .enablePlugins(ScalaJSPlugin)

  lazy val core_showcase = Project(
    id = "doctus-core-showcase",
    base = file("core-showcase"),
    settings = S.showcaseSettings)
    .dependsOn(core)
    .enablePlugins(ScalaJSPlugin)

  lazy val scalajs_showcase = Project(
    id = "doctus-scalajs-showcase",
    base = file("scalajs-showcase"),
    settings = S.scalajsSettings)
    .dependsOn(core_showcase, scalajs)
    .enablePlugins(ScalaJSPlugin)

  lazy val swing_showcase = Project(
    id = "doctus-swing-showcase",
    base = file("swing-showcase"),
    settings = S.swingSettings)
    .dependsOn(core_showcase, swing)

}
