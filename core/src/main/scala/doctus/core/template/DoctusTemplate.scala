package doctus.core.template

import doctus.core.DoctusGraphics
import doctus.core.util.DoctusPoint
import doctus.core.DoctusCanvas
import doctus.core.DoctusScheduler
import doctus.core.DoctusPointable
import doctus.core.DoctusDraggable

trait DoctusTemplateCanvas extends DoctusCanvas with DoctusPointable with DoctusDraggable

trait DoctusTemplate {

  def canvas: DoctusCanvas

  def width: Int = canvas.width

  def height: Int = canvas.height

  /* 
   * In frames per second. Default: 20 fps
   */
  def frameRate: Option[Int] = Some(20)

  def draw(g: DoctusGraphics): Unit

  def pointablePressed(pos: DoctusPoint): Unit

  def pointableReleased(pos: DoctusPoint): Unit

  def pointableDragged(pos: DoctusPoint): Unit

}

trait DoctusTemplateController[T <: DoctusTemplate] {

  def template: T

  def sched: DoctusScheduler

  def canvas: DoctusTemplateCanvas

  if (template.frameRate.isDefined) {
    val fr = template.frameRate.get
    require(fr > 0)
    sched.start(canvas.repaint, (1000.0 / fr).toInt)
  }

  canvas.onRepaint(template.draw)
  // TODO There could be a graphic context on all these on... methods. ???
  canvas.onStart(template.pointablePressed)

  canvas.onStop(template.pointableReleased)

  canvas.onDrag(template.pointableDragged)

}

case class DoctusTemplateControllerImpl[T <: DoctusTemplate](
  template: T,
  sched: DoctusScheduler,
  canvas: DoctusTemplateCanvas) extends DoctusTemplateController[T]





