package doctus.jvm

import javafx.application.Application
import javafx.event.{Event, EventHandler}
import javafx.geometry.{Insets, Pos}
import javafx.scene._
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.layout.{BorderPane, FlowPane}
import javafx.scene.paint._
import javafx.stage.Stage

import doctus.core._

object ShowcaseJvmSchedulerStop extends App {

  Application.launch(classOf[FxApp], args: _*)

  class FxApp extends Application {

    override def start(stage: Stage) {

      val width = 700
      val height = 500

      val buttonStart = new Button("START")
      val buttonStop = new Button("STOP")

      val canvas = new Canvas()
      canvas.setWidth(width)
      canvas.setHeight(height)


      val contPane = new BorderPane()
      contPane.setPadding(new Insets(20, 20, 20, 20))
      val compsPane = new FlowPane()
      compsPane.setAlignment(Pos.CENTER)

      compsPane.getChildren.add(buttonStart)
      compsPane.getChildren.add(buttonStop)

      contPane.setTop(compsPane)
      contPane.setCenter(canvas)

      val bgCol = Color.WHITE
      val scene = new Scene(contPane, width, height, bgCol)

      val doctStart = DoctusActivatableFx(buttonStart)
      val doctStop = DoctusActivatableFx(buttonStop)
      val doctCanvas = DoctusCanvasFx(canvas)
      val doctSched = DoctusSchedulerJvm

      // Start the controller
      DoctusControllerSchedulerStop(doctSched, doctStart, doctStop, doctCanvas)

      stage.setScene(scene)

      stage.show()

      def handler[T <: Event](h: (T => Unit)): EventHandler[T] =
        new EventHandler[T] {
          override def handle(event: T): Unit = h(event)
        }

      // TODO Find a better solution to exit
      stage.setOnCloseRequest(handler(e => System.exit(0)))

    }
  }

}

