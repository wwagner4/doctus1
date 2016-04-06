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

case class DoctusGraphicsFx(graphics: Any) extends DoctusGraphics {

  def ellipse(centerX: Double, centerY: Double, a: Double, b: Double): Unit = ???

  def fill(c: doctus.core.DoctusColor, alpha: Double): Unit = ???

  def noFill(): Unit = ???

  def imageMode(imageMode: ImageMode): Unit = ???

  def image(img: DoctusImage, originX: Double, originY: Double): Unit = ???

  def line(fromX: Double, fromY: Double, toX: Double, toY: Double): Unit = ???

  def poli(poli: List[DoctusPoint]): Unit = ???

  def rect(originX: Double, originY: Double, width: Double, height: Double): Unit = ???

  def stroke(c: doctus.core.DoctusColor, alpha: Double): Unit = ???

  def noStroke(): Unit = ???

  def strokeWeight(weight: Double): Unit = ???

  def textFont(font: DoctusFont): Unit = ???

  def textSize(textSize: Double): Unit = ???

  def text(str: String, originX: Double, originY: Double, rotation: Double): Unit = ???

}

case class DoctusCanvasFx(comp: Any) extends DoctusCanvas {

  def height: Int = ???
  def onRepaint(f: doctus.core.DoctusGraphics ⇒ Unit): Unit = ???
  def repaint(): Unit = ???
  def width: Int = ???

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

case object DoctusSchedulerFx extends DoctusScheduler {

  def start(f: () => Unit, duration: Int, initialDelay: Int = 0): DoctusScheduler.Stopper = ???

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

  def scale(factor: Double): doctus.core.DoctusImage = ???

  def width = ???

  def height = ???

}

