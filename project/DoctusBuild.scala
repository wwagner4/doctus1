import sbt._
import Keys._

import com.typesafe.sbteclipse.plugin.EclipsePlugin._

import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object DoctusBuild extends Build {

  object D {
    val scalaVersion = s"2.11.5"
    val doctusVersion = s"1.0.5-SNAPSHOT"
    val mockitoVersion = "1.9.5"
  }

  object S {

    lazy val baseSettings: Seq[Def.Setting[_]] =
      Seq(
        scalaVersion := D.scalaVersion,
        organization := "net.entelijan",
        organizationHomepage := Some(url("http://entelijan.net/")),
        publishTo := Some("entelijan-repo" at "http://entelijan.net/artifactory/libs-release-local"),
        credentials += Credentials("Artifactory Realm", "entelijan.net", "deploy", "deploy"),
        resolvers += "entelijan" at "http://entelijan.net/artifactory/repo",
        EclipseKeys.withSource := true)

    lazy val defaultSettings =
      baseSettings ++
        Seq(
          version := D.doctusVersion)

    lazy val coreSettings =
      defaultSettings ++
        Seq(
          libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0" % "test",
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
          libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0",
          libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.8.0",
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
