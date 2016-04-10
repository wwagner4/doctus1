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
import javafx.stage.WindowEvent
import javafx.event.EventHandler
import javafx.event.ActionEvent
import javafx.event.Event
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
import javafx.scene.layout.VBox
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import doctus.core.template.DoctusTemplateController

object ShowcaseTemplatJvmKeys extends App {

  Application.launch(classOf[FxApp], args: _*);

  class FxApp extends Application {

    override def start(stage: Stage) {

      val width = 700
      val height = 500

      val canvas = new Canvas()

      val doctCanvas = DoctusTemplateCanvasFx(canvas)
      val doctSched = DoctusSchedulerJvm

      val grp = new Group();
      grp.getChildren().add(canvas);
      val bgCol = Color.WHITE;
      val scene = new Scene(grp, width, height, bgCol);

      canvas.widthProperty().bind(scene.widthProperty())
      canvas.heightProperty().bind(scene.heightProperty())

      stage.setScene(scene);
      stage.show();

      // TODO Find a better solution to exit
      stage.setOnCloseRequest(DoctusJvmUtil.handler(e => System.exit(0)))

      // Start the controller
      val templ = DoctusTemplateKeys(doctCanvas)
      DoctusTemplateController(templ, doctSched, doctCanvas)

    }
  }

}

