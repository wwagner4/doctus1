package doctus.showcase

import doctus.core.template._
import doctus.core._
import doctus.core.util._
import doctus.core.color.DoctusColorRed
import doctus.core.color.DoctusColorBlack

case class Button(pos: DoctusPoint)

case class ShowcaseTemplateKeys(canvas: DoctusCanvas) extends DoctusTemplate {

  def center: DoctusPoint = DoctusPoint(canvas.width * 0.5, canvas.height * 0.5)

  var button = new Button(center)

  val size = 100
  val incr = 50

  def draw(g: DoctusGraphics): Unit = {
    g.noStroke()
    g.fill(DoctusColorRed, 255)
    g.ellipse(button.pos, size, size)
    g.fill(DoctusColorBlack, 10)
    g.rect(0, 0, canvas.width, canvas.height)
  }
  def keyPressed(code: DoctusKeyCode): Unit = code match {
    case DKC_Down  => button = Button(button.pos + DoctusVector(0, incr))
    case DKC_Up    => button = Button(button.pos + DoctusVector(0, -incr))
    case DKC_Left  => button = Button(button.pos + DoctusVector(-incr, 0))
    case DKC_Right => button = Button(button.pos + DoctusVector(incr, 0))
    case _         => // Nothing to do
  }
  
  def pointableDragged(pos: DoctusPoint): Unit = ()
  def pointablePressed(pos: DoctusPoint): Unit = ()
  def pointableReleased(pos: DoctusPoint): Unit = ()

}