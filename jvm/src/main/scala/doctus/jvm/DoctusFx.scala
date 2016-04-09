package doctus.jvm

import doctus.core.DoctusActivatable
import doctus.core.DoctusCanvas
import doctus.core.DoctusDraggable
import doctus.core.DoctusFont
import doctus.core.DoctusGraphics
import doctus.core.DoctusImage
import doctus.core.DoctusKey
import doctus.core.DoctusPointable
import doctus.core.DoctusScheduler
import doctus.core.ImageMode
import doctus.core.comp.DoctusSelect
import doctus.core.comp.DoctusText
import doctus.core.template.DoctusTemplateCanvas
import doctus.core.util.DoctusPoint
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import doctus.core.DoctusColor
import javafx.scene.paint.Color
import javafx.scene.image.Image
import java.net.URL
import scala.concurrent.ExecutionContext.Implicits.global
import javafx.application.Platform

case class DoctusGraphicsFx(gc: GraphicsContext) extends DoctusGraphics {

  def ellipse(centerX: Double, centerY: Double, a: Double, b: Double): Unit = ???

  def fill(c: DoctusColor, alpha: Double): Unit = {
    // TODO refactor variables
    val (r, g, b) = c.rgb
    val c1 = Color.rgb(r, g, b, alpha / 255)
    gc.setFill(c1)
  }

  def noFill(): Unit = {
    // TODO Do not ignore
  }

  def imageMode(imageMode: ImageMode): Unit = ???

  def image(img: DoctusImage, originX: Double, originY: Double): Unit = {
    img match {
      case img: DoctusImageFx =>
        val w = img.width * img.scaleFactor
        val h = img.height * img.scaleFactor
        gc.drawImage(img.image, originX, originY, w, h)
      case _ => throw new IllegalStateException("Image class not DoctusImageFx. " + img.getClass)
    }

  }

  def line(fromX: Double, fromY: Double, toX: Double, toY: Double): Unit = ???

  def poli(poli: List[DoctusPoint]): Unit = ???

  def rect(originX: Double, originY: Double, width: Double, height: Double): Unit = {
    // TODO determine between fill and no fill
    gc.fillRect(originX, originY, width, height)
  }

  def stroke(c: doctus.core.DoctusColor, alpha: Double): Unit = {
    // TODO Do not ignore
  }

  def noStroke(): Unit = {
    // TODO Do not ignore
  }
  def strokeWeight(weight: Double): Unit = ???

  def textFont(font: DoctusFont): Unit = ???

  def textSize(textSize: Double): Unit = ???

  def text(str: String, originX: Double, originY: Double, rotation: Double): Unit = ???

}

case class DoctusCanvasFx(canvas: Canvas) extends DoctusCanvas {

  val g = canvas.getGraphicsContext2D;

  var paintFun = Option.empty[DoctusGraphics => Unit]

  def onRepaint(f: DoctusGraphics => Unit): Unit = {
    // TODO remove variables
    paintFun = Some(f)
    repaint
  }
  // TODO refactor this method
  def repaint(): Unit = {
    import scala.concurrent._
    paintFun match {
      case Some(f) =>
        val dg = DoctusGraphicsFx(g)
        Platform.runLater(new Runnable {
          def run = f(dg)
        })  
      case None => println("repaint was called but nothing was done !!!")
    }
  }
  def width: Int = canvas.getWidth.toInt
  def height: Int = canvas.getHeight.toInt

}

case class DoctusTemplateCanvasFx(comp: Any)
    extends DoctusTemplateCanvas with DoctusCanvas with DoctusDraggable
    with DoctusKey {
  // Members declared in doctus.core.DoctusCanvas
  def height: Int = ???
  def onRepaint(f: doctus.core.DoctusGraphics ⇒ Unit): Unit = ???
  def repaint(): Unit = ???
  def width: Int = ???

  // Members declared in doctus.core.DoctusDraggable
  def onDrag(f: doctus.core.util.DoctusPoint ⇒ Unit): Unit = ???

  // Members declared in doctus.core.DoctusKey
  def onKeyPressed(f: doctus.core.DoctusKeyCode ⇒ Unit): Unit = ???
  def onKeyReleased(f: doctus.core.DoctusKeyCode ⇒ Unit): Unit = ???

  // Members declared in doctus.core.DoctusPointable
  def onStart(f: doctus.core.util.DoctusPoint ⇒ Unit): Unit = ???
  def onStop(f: doctus.core.util.DoctusPoint ⇒ Unit): Unit = ???
}

case class DoctusSelectFx[T](comboBox: Any, f: (T) => String = (t: T) => t.toString()) extends DoctusSelect[T] {

  def addItem(item: T): Unit = ???

  def selectedItem: T = ???
}

case class DoctusPointableFx(comp: Any) extends DoctusPointable {

  def onStart(f: doctus.core.util.DoctusPoint ⇒ Unit): Unit = ???
  def onStop(f: doctus.core.util.DoctusPoint ⇒ Unit): Unit = ???
}

case class DoctusDraggableFx(comp: Any) extends DoctusDraggable {
  // Members declared in doctus.core.DoctusDraggable
  def onDrag(f: doctus.core.util.DoctusPoint ⇒ Unit): Unit = ???

  // Members declared in doctus.core.DoctusPointable
  def onStart(f: doctus.core.util.DoctusPoint ⇒ Unit): Unit = ???
  def onStop(f: doctus.core.util.DoctusPoint ⇒ Unit): Unit = ???
}

case class DoctusActivatableFx(comp: Any) extends DoctusActivatable {

  def onActivated(f: () => Unit): Unit = ???
  def onDeactivated(f: () => Unit): Unit = ???
}

case class DoctusTextFx(textComp: Any) extends DoctusText {

  def text: String = ???

  def text_=(txt: String) = ???
}

/**
 * Listens to the keyboard.
 */
case class DoctusKeyFx(comp: Any) extends DoctusKey {
  def onKeyPressed(f: doctus.core.DoctusKeyCode ⇒ Unit): Unit = ???
  def onKeyReleased(f: doctus.core.DoctusKeyCode ⇒ Unit): Unit = ???
}

case class DoctusImageFx(resource: String, scaleFactor: Double = 1.0) extends DoctusImage {

  def scale(factor: Double): doctus.core.DoctusImage = DoctusImageFx(resource, scaleFactor * factor)

  lazy val image = new Image(resource)
  
  def width = image.getWidth.toInt

  def height = image.getHeight.toInt

}

