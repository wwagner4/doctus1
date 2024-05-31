package doctus.core

import doctus.core.color.{DoctusColorBlack, DoctusColorYellow}
import doctus.core.template._
import doctus.core.util._

case class DoctusTemplateWrongPointPosition(canvas: DoctusCanvas)
    extends DoctusTemplate {

  var pos = Option.empty[DoctusPoint]

  override def frameRate = Some(1)

  def draw(g: DoctusGraphics): Unit = {
    g.noStroke()
    g.fill(DoctusColorYellow, 255)
    g.rect(0, 0, canvas.width, canvas.height)
    g.stroke(DoctusColorBlack, 255)
    g.fill(DoctusColorBlack, 255)
    g.textSize(35)
    pos.foreach(p => g.text(p.toString, DoctusPoint(5, 40), 0))
  }
  def keyPressed(code: DoctusKeyCode): Unit = ()

  def pointableDragged(pos: DoctusPoint): Unit = ()
  def pointablePressed(pos: DoctusPoint): Unit = {
    this.pos = Some(pos)
  }
  def pointableReleased(pos: DoctusPoint): Unit = ()

}
