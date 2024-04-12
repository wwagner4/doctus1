package doctus.jvm

import javafx.application.Application
import javafx.event.{Event, EventHandler}
import javafx.scene._
import javafx.scene.canvas.Canvas
import javafx.scene.paint._
import javafx.stage.Stage

import doctus.core._

object ShowcaseJvmCanvas extends App {

  Application.launch(classOf[MyApp], args: _*)

  class MyApp extends Application {

    override def start(stage: Stage): Unit = {

      val width = 800
      val height = 700

      val canvasFx = new Canvas(width, height)

      val canvas = DoctusCanvasFx(canvasFx)
      val img = DoctusImageFx("logo.png")

      val grp = new Group()
      grp.getChildren.add(canvasFx)

      val bgCol = Color.WHITE
      val scene = new Scene(grp, width, height, bgCol)

      // Start the controller
      DoctusControllerCanvas(canvas, img)
      
      stage.setScene(scene)

      stage.show()
      def handler[T <: Event](h: (T => Unit)): EventHandler[T] =
        new EventHandler[T] {
          override def handle(event: T): Unit = h(event)
        }

      // Find a better solution to exit
      stage.setOnCloseRequest(handler(e => System.exit(0)))



    }
  }

}

