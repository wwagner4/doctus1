package doctus.jvm

import javafx.application.Application
import javafx.scene._
import javafx.scene.canvas.Canvas
import javafx.scene.paint._
import javafx.stage.Stage

import doctus.core._
import doctus.core.template.DoctusTemplateController

object ShowcaseTemplateJvmKeys extends App {

  Application.launch(classOf[FxApp], args: _*)

  class FxApp extends Application {

    override def start(stage: Stage): Unit = {

      val width = 700
      val height = 500

      val canvas = new Canvas()

      val doctCanvas = DoctusTemplateCanvasFx(canvas)
      val doctSched = DoctusSchedulerJvm

      val grp = new Group()
      grp.getChildren.add(canvas)
      val bgCol = Color.WHITE
      val scene = new Scene(grp, width, height, bgCol)

      canvas.widthProperty().bind(scene.widthProperty())
      canvas.heightProperty().bind(scene.heightProperty())

      stage.setScene(scene)
      stage.show()

      // TODO Find a better solution to exit
      stage.setOnCloseRequest(DoctusJvmUtil.handler(e => System.exit(0)))

      // Start the controller
      val templ = DoctusTemplateKeys(doctCanvas)
      DoctusTemplateController(templ, doctSched, doctCanvas)

    }
  }

}

