package doctus.jvm

import javafx.application.Application
import javafx.event.{Event, EventHandler}
import javafx.scene._
import javafx.scene.canvas.Canvas
import javafx.scene.paint._
import javafx.stage.Stage

import doctus.core._
import doctus.core.template.DoctusTemplateController

object ShowcaseJvmWrongPointPosition extends App {

  Application.launch(classOf[MyApp], args: _*)

  class MyApp extends Application {

    override def start(stage: Stage) {

      val width = 500
      val height = 500

      val canvasFx = new Canvas(width, height)

      val canvas = DoctusTemplateCanvasFx(canvasFx)

      val grp = new Group()
      grp.getChildren.add(canvasFx)

      val bgCol = Color.WHITE
      val scene = new Scene(grp, width, height, bgCol)

      val sched = DoctusSchedulerJvm

      // Start the controller
      val templ = DoctusTemplateWrongPointPosition(canvas)

      DoctusTemplateController(templ, sched, canvas)
      canvas.repaint()


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

