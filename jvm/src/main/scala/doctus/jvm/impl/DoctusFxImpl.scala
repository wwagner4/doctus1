package doctus.jvm.impl

import doctus.core._
import doctus.core.util.DoctusPoint
import doctus.jvm.DoctusGraphicsFx
import doctus.jvm.DoctusJvmUtil
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.beans.Observable
import javafx.beans.value.ChangeListener
import javafx.event.Event
import javafx.beans.value.ObservableValue

private[jvm] trait DoctusCanvasFxImpl extends DoctusCanvas {

  def comp: Canvas

  comp.widthProperty.addListener(new ChangeListener[Number] {
    def changed(obs: ObservableValue[_ <: Number], old: Number, nev: Number) { repaint }
  })
  
  comp.heightProperty().addListener(new ChangeListener[Number] {
    def changed(obs: ObservableValue[_ <: Number], old: Number, nev: Number) { repaint }
  })

  val g = comp.getGraphicsContext2D;

  var paintFun = Option.empty[DoctusGraphics => Unit]

  def onRepaint(f: DoctusGraphics => Unit): Unit = {
    paintFun = Some(f)
    repaint
  }

  def repaint(): Unit = {
    paintFun match {
      case Some(f) =>
        val dg = DoctusGraphicsFx(g)
        Platform.runLater(new Runnable {
          def run = f(dg)
        })
      case None => // Nothing to do
    }
  }
  def width: Int = comp.getWidth.toInt
  def height: Int = comp.getHeight.toInt

}

private[jvm] trait DoctusPointableFxImpl extends DoctusPointable {

  def comp: Node

  def onStart(f: DoctusPoint ⇒ Unit): Unit = _onStart = Some(f)
  def onStop(f: DoctusPoint ⇒ Unit): Unit = _onStop = Some(f)

  var _onStart = Option.empty[DoctusPoint ⇒ Unit]
  var _onStop = Option.empty[DoctusPoint ⇒ Unit]

  comp.setOnMousePressed(DoctusJvmUtil.handler { e =>
    val x = e.getX
    val y = e.getY
    _onStart.foreach(f => f(DoctusPoint(x, y)))
  })

  comp.setOnMouseReleased(DoctusJvmUtil.handler { e =>
    val x = e.getX
    val y = e.getY
    _onStop.foreach(f => f(DoctusPoint(x, y)))
  })
}

private[jvm] trait DoctusDraggableFxImpl extends DoctusDraggable {

  def comp: Node

  var _onStart = Option.empty[DoctusPoint ⇒ Unit]
  var _onStop = Option.empty[DoctusPoint ⇒ Unit]
  var _onDrag = Option.empty[DoctusPoint ⇒ Unit]

  // Members declared in doctus.core.DoctusDraggable
  def onDrag(f: DoctusPoint ⇒ Unit): Unit = _onDrag = Some(f)

  // Members declared in doctus.core.DoctusPointable
  def onStart(f: DoctusPoint ⇒ Unit): Unit = _onStart = Some(f)
  def onStop(f: DoctusPoint ⇒ Unit): Unit = _onStop = Some(f)

  comp.setOnMouseDragEntered(DoctusJvmUtil.handler { e =>
    println("onDragEntered...")
  })
  comp.setOnMouseDragExited(DoctusJvmUtil.handler { e =>
    println("onDragExited...")
  })
  comp.setOnMouseDragged(DoctusJvmUtil.handler { e =>
    val x = e.getX
    val y = e.getY
    _onDrag.foreach(f => f(DoctusPoint(x, y)))
  })
  comp.setOnMouseDragOver(DoctusJvmUtil.handler { e =>
    println("onDragOver...")
  })
  comp.setOnMouseDragReleased(DoctusJvmUtil.handler { e =>
    println("onDragExitedReleased...")
  })
  comp.setOnMousePressed(DoctusJvmUtil.handler { e =>
    val x = e.getX
    val y = e.getY
    _onStart.foreach(f => f(DoctusPoint(x, y)))
  })
  comp.setOnMouseReleased(DoctusJvmUtil.handler { e =>
    val x = e.getX
    val y = e.getY
    _onStop.foreach(f => f(DoctusPoint(x, y)))
  })

}

/**
 * Listens to the keyboard.
 */
private[jvm] trait DoctusKeyFxImp extends DoctusKey {

  comp.setFocusTraversable(true)
  
  def comp: Node

  var _onKeyPressed = Option.empty[DoctusKeyCode ⇒ Unit]
  var _onKeyReleased = Option.empty[DoctusKeyCode ⇒ Unit]

  def onKeyPressed(f: DoctusKeyCode ⇒ Unit): Unit = _onKeyPressed = Some(f)

  def onKeyReleased(f: DoctusKeyCode ⇒ Unit): Unit = _onKeyReleased = Some(f)

  def keyCode(e: KeyEvent): Option[DoctusKeyCode] = {
    val code: KeyCode = e.getCode
    if (code == KeyCode.SPACE) Some(DKC_Space)
    else if (code == KeyCode.UP) Some(DKC_Up)
    else if (code == KeyCode.DOWN) Some(DKC_Down)
    else if (code == KeyCode.LEFT) Some(DKC_Left)
    else if (code == KeyCode.RIGHT) Some(DKC_Right)
    else if (code == KeyCode.ENTER) Some(DKC_Enter)
    else None
  }

  comp.setOnKeyPressed(DoctusJvmUtil.handler { e =>
    _onKeyPressed.foreach { f => keyCode(e).foreach { dkc => f(dkc) } }
  })
  comp.setOnKeyReleased(DoctusJvmUtil.handler { e =>
    _onKeyReleased.foreach { f => keyCode(e).foreach { dkc => f(dkc) } }
  })
}



