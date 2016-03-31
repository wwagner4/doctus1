package doctus.scalajs.inner

import scala.scalajs.js
import scala.scalajs.js.Any.fromFunction1

import org.scalajs.dom.raw._

import doctus.core._
import doctus.core.util.DoctusPoint
import doctus.scalajs._

private[scalajs] trait DoctusCanvasScalajs1 extends DoctusCanvas {

  def elem: HTMLCanvasElement

  val ctx: CanvasRenderingContext2D = elem.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
  ctx.translate(0.5, 0.5)

  var fopt: Option[(DoctusGraphics) => Unit] = None

  def onRepaint(f: (DoctusGraphics) => Unit) = {
    fopt = Some(f)
  }

  def repaint(): Unit = {
    fopt foreach (f => f(DoctusGraphicsScalajs(ctx)))
  }

  def width = elem.clientWidth

  def height = elem.clientHeight
}

private[scalajs] trait DoctusDraggableScalajs1 extends DoctusPointableScalajs1 with DoctusDraggable {

  def onDrag(f: (DoctusPoint) => Unit): Unit = em.onDrag(f)

}

private[scalajs] trait DoctusPointableScalajs1 extends DoctusPointable {

  def elem: HTMLElement

  def scrollTopLeft(elem: HTMLElement): (Double, Double) = {
    if (elem.localName.equals("html")) (elem.scrollTop, elem.scrollLeft)
    else scrollTopLeft(elem.parentElement) match {
      case (t, l) => (t + elem.scrollTop, l + elem.scrollLeft)
    }
  }

  val em = new DoctusEventManager

  elem.addEventListener("mousedown", (e: Event) => {
    // 'mousedown' always produces a MouseEvent
    // Patternmatching can cause problems
    val me = e.asInstanceOf[MouseEvent]
    e.preventDefault()
    em.addEvent(MouseDown(point(me)))
  })

  elem.addEventListener("mouseup", (e: Event) => {
    // 'mouseup' always produces a MouseEvent
    val me = e.asInstanceOf[MouseEvent]
    e.preventDefault()
    em.addEvent(MouseUp(point(me)))
  })

  elem.addEventListener("mousemove", (e: Event) => {
    // 'mousemove' always produces a MouseEvent
    val me = e.asInstanceOf[MouseEvent]
    e.preventDefault()
    em.addEvent(MouseMove(point(me)))
  })

  elem.addEventListener("touchstart", (e: Event) => {
    // 'touchstart' always produces a TouchEvent
    val te = e.asInstanceOf[TouchEvent]
    e.preventDefault()
    em.addEvent(TouchStart(idpoints(te)))
  })

  elem.addEventListener("touchend", (e: Event) => {
    // 'touchstart' always produces a TouchEvent
    val te = e.asInstanceOf[TouchEvent]
    e.preventDefault()
    em.addEvent(TouchEnd(idpoints(te)))
  })

  elem.addEventListener("touchmove", (e: Event) => {
    // 'touchstart' always produces a TouchEvent
    val te = e.asInstanceOf[TouchEvent]
    e.preventDefault()
    em.addEvent(TouchMove(idpoints(te)))
  })

  def onStart(f: (DoctusPoint) => Unit): Unit = {
    em.onStart(f)
  }

  def onStop(f: (DoctusPoint) => Unit): Unit = {
    em.onStop(f)
  }

  private def point(m: MouseEvent): DoctusPoint = {
    val (st, sl) = scrollTopLeft(elem)
    val x = m.clientX - elem.offsetLeft + sl - 1
    val y = m.clientY - elem.offsetTop + st - 1
    DoctusPoint(x, y)
  }

  private def idpoints(te: TouchEvent): List[DoctusIdPoint] = {
    val (st, sl) = scrollTopLeft(elem)
    val tl = te.targetTouches
    (for (i <- 0 until tl.length.intValue) yield {
      val t: Touch = tl(i)
      val x = t.clientX - elem.offsetLeft + sl - 1
      val y = t.clientY - elem.offsetTop + st - 1
      DoctusIdPoint(t.identifier, DoctusPoint(x, y))
    }).toList
  }

}

trait DoctusKeyScalajs1 extends DoctusKey {

  def elem: Element

  private def mapKeyCode(code: Int): Option[DoctusKeyCode] = {

    if (org.scalajs.dom.ext.KeyCode.Down == code) Some(DKC_Down)
    else if (org.scalajs.dom.ext.KeyCode.Up == code) Some(DKC_Up)
    else if (org.scalajs.dom.ext.KeyCode.Right == code) Some(DKC_Right)
    else if (org.scalajs.dom.ext.KeyCode.Left == code) Some(DKC_Left)
    else if (org.scalajs.dom.ext.KeyCode.Space == code) Some(DKC_Space)
    else if (org.scalajs.dom.ext.KeyCode.Enter == code) Some(DKC_Enter)
    else None
  }

  private var onPressedOpt: Option[(DoctusKeyCode) => Unit] = None
  private var onReleasedOpt: Option[(DoctusKeyCode) => Unit] = None

  private var active = false

  elem.addEventListener("keydown", (e: Event) => {
    val kevent: KeyboardEvent = e.asInstanceOf[KeyboardEvent]
    if (!active) mapKeyCode(kevent.keyCode) match {
      case Some(key) =>
        e.preventDefault()
        e.cancelBubble
        active = true
        onPressedOpt.foreach(f => f(key))
      case None => // Nothing to do 
    }
  })

  elem.addEventListener("keyup", (e: Event) => {
    val kevent: KeyboardEvent = e.asInstanceOf[KeyboardEvent]
    if (active) mapKeyCode(kevent.keyCode) match {
      case Some(key) =>
        e.preventDefault()
        e.cancelBubble
        active = false
        onReleasedOpt.foreach(f => f(key))
      case None => // Nothing to do
    }
  })

  def onKeyPressed(f: (DoctusKeyCode) => Unit): Unit = onPressedOpt = Some(f)
  def onKeyReleased(f: (DoctusKeyCode) => Unit): Unit = onReleasedOpt = Some(f)

}



