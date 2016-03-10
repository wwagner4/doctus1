package doctus.core.template

import doctus.core.DoctusGraphics
import doctus.core.util.DoctusPoint
import doctus.core.DoctusCanvas
import doctus.core.DoctusScheduler
import doctus.core.DoctusPointable
import doctus.core.DoctusDraggable

trait DoctusTemplate {

  def canvas: DoctusCanvas

  def width: Int = canvas.width

  def height: Int = canvas.height

  def frameRate: Option[Int] = Some(60)

  def draw(g: DoctusGraphics): Unit

  def pointablePressed(pos: DoctusPoint): Unit

  def pointableReleased(pos: DoctusPoint): Unit

  def pointableDragged(pos: DoctusPoint): Unit

}

trait DoctusTemplateController[T <: DoctusTemplate] {

  def template: T

  def sched: DoctusScheduler

  def canvas: DoctusCanvas

  def pointable: DoctusPointable

  def draggable: DoctusDraggable

  if (template.frameRate.isDefined) {
    val fr = template.frameRate.get
    require(fr > 0)
    sched.start(canvas.repaint, fr)
  }

  canvas.onRepaint(template.draw)
  // TODO There could be a graphic context on all these on... methods. ???
  pointable.onStart(template.pointablePressed)

  pointable.onStop(template.pointableReleased)

  draggable.onDrag(template.pointableDragged)

}

case class DoctusTemplateControllerImpl[T <: DoctusTemplate](
  template: T,
  sched: DoctusScheduler,
  canvas: DoctusCanvas,
  pointable: DoctusPointable,
  draggable: DoctusDraggable) extends DoctusTemplateController[T]





