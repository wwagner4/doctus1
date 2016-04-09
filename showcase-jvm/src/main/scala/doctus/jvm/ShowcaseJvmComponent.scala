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

object ShowcaseJvmComponent extends App {

  Application.launch(classOf[FxApp], args: _*);

  class FxApp extends Application {

    override def start(stage: Stage) {

      val width = 700
      val height = 500

      val grp = new Group();
      //grp.getChildren().add(canvasFx);

      val bgCol = Color.WHITE;
      val scene = new Scene(grp, width, height, bgCol);

      val label01 = new Label("Activatable 01")

      val pane02 = new Pane();
      pane02.setStyle("-fx-background-color: yellow;");
      pane02.setPrefSize(100, 100);

      val button01 = new Button("button 01")

      val comboBox03 = new ComboBox[FullName]()

      val button03 = new Button("check selection")
      
      val textField = new TextField()

      val pointable01 = DoctusPointableFx(label01)
      val pointable02 = DoctusPointableFx(pane02)
      val upKey04 = DoctusKeyFx(grp)
      val activatable01 = DoctusActivatableFx(button01)
      val select03 = DoctusSelectFx[FullName](comboBox03, (fn) => "[%s - %s]" format (fn.first, fn.last))
      val clickable03 = DoctusActivatableFx(button03)
      val infoText = DoctusTextFx(textField)

      // Start the controller
      DoctusControllerComponent(pointable01, pointable02, upKey04, activatable01, select03, clickable03, infoText)

      stage.setScene(scene);

      stage.show();
      
      def handler[T <: Event](h: (T => Unit)): EventHandler[T] =
        new EventHandler[T] {
          override def handle(event: T): Unit = h(event)
        }

      // TODO Find a better solution to exit
      stage.setOnCloseRequest(handler(e => System.exit(0)))

    }
  }

}

