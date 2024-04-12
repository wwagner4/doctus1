package doctus.jvm

import javafx.application.Application
import javafx.event.{Event, EventHandler}
import javafx.geometry.Insets
import javafx.scene._
import javafx.scene.control.{Button, ComboBox, Label, TextField}
import javafx.scene.layout.{FlowPane, Pane, VBox}
import javafx.scene.paint._
import javafx.stage.Stage

import doctus.core._

object ShowcaseJvmComponent extends App {

  Application.launch(classOf[FxApp], args: _*)

  class FxApp extends Application {

    override def start(stage: Stage): Unit = {

      val width = 700
      val height = 500

      val label01 = new Label("Activatable 01")

      val pane02 = new Pane()
      pane02.setStyle("-fx-background-color: yellow;")
      pane02.setPrefSize(100, 100)

      val button01 = new Button("button 01")

      val comboBox03 = new ComboBox[FullName]()

      val button03 = new Button("check selection")

      val textField = new TextField()
      textField.setEditable(false)
      textField.setFocusTraversable(false)
      
      val contPane = new VBox(30)
      contPane.setPadding(new Insets(20, 20, 20, 20))
      val compsPane = new FlowPane()

      compsPane.getChildren.add(label01)
      compsPane.getChildren.add(pane02)
      compsPane.getChildren.add(button01)
      compsPane.getChildren.add(comboBox03)
      compsPane.getChildren.add(button03)
      
      contPane.getChildren.add(textField)
      contPane.getChildren.add(compsPane)
      
      val bgCol = Color.WHITE
      val scene = new Scene(contPane, width, height, bgCol)

      val pointable01 = DoctusPointableFx(label01)
      val pointable02 = DoctusPointableFx(pane02)
      val upKey04 = DoctusKeyFx(compsPane)
      val activatable01 = DoctusActivatableFx(button01)
      val select03 = DoctusSelectFx[FullName](comboBox03, (fn) => "[%s - %s]" format (fn.first, fn.last))
      val clickable03 = DoctusActivatableFx(button03)
      val infoText = DoctusTextFx(textField)

      // Start the controller
      DoctusControllerComponent(pointable01, pointable02, upKey04, activatable01, select03, clickable03, infoText)

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

