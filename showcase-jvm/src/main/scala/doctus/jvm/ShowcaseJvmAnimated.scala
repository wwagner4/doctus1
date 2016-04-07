package doctus.jvm

import doctus.core._
import java.util.ArrayList
import java.util.List
import java.util.Random
import javafx.application.Application
import javafx.scene._
import javafx.stage.Stage
import javafx.scene.canvas.Canvas
import javafx.application._
import javafx.scene._
import javafx.scene.paint._
import javafx.stage.Stage

object ShowcaseJvmAnimated extends App {

  Application.launch(classOf[MyApp], args: _*);

  class MyApp extends Application {

    override def start(stage: Stage) {

      val width = 400
      val height = 400

      val fxCanvas = new Canvas(width, height);
      val ctx = fxCanvas.getGraphicsContext2D

      val sched = DoctusSchedulerJvm
      val canvas = DoctusCanvasFx(ctx)
      val img = DoctusImageFx("logo.png")

      // Start the controller
      DoctusControllerAnimated(canvas, sched, img)
    }
  }

}

