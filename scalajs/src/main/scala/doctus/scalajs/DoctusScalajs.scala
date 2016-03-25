package doctus.scalajs

import doctus.core._
import doctus.core.comp._
import doctus.core.util._
import doctus.core.color._
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.raw._
import scala.scalajs.js.Any.{ fromFunction0, fromInt, fromString }
import doctus.core.template.DoctusTemplateCanvas

case class DoctusGraphicsScalajs(ctx: CanvasRenderingContext2D) extends DoctusGraphics {

  private var isFill = true
  private var isStroke = true
  private var fillAlpha = 255.0
  private var strokeAlpha = 255.0

  def fill(c: DoctusColor, alpha: Double): Unit = {
    c.rgb match {
      case (r, g, b) => ctx.fillStyle = DoctusScalajsUtil.rgbString(r, g, b)
    }
    fillAlpha = math.max(0, math.min(255, alpha)) / 255.0
    isFill = true
  }
  def stroke(c: DoctusColor, alpha: Double): Unit = {
    c.rgb match {
      case (r, g, b) =>
        ctx.strokeStyle = DoctusScalajsUtil.rgbString(r, g, b)
    }
    strokeAlpha = math.max(0, math.min(255, alpha)) / 255.0
    isStroke = true
  }
  def strokeWeight(weight: Double): Unit = {
    ctx.lineWidth = weight
  }

  def noFill(): Unit = isFill = false
  def noStroke(): Unit = isStroke = false

  case class Font(name: String, size: Int)
  var font = Font("sans-serif", 12)

  def textFont(_font: DoctusFont): Unit = {
    import doctus.core.text._

    font = _font match {
      case DoctusFontMonospace => font.copy(name = "monospace")
      case DoctusFontSansSerif => font.copy(name = "sans-serif")
      case DoctusFontSerif     => font.copy(name = "serif")
      case DoctusFontNamed(n)  => font.copy(name = n)
    }
    val style = s"%dpx %s" format (font.size, font.name)
    ctx.font = style
  }
  def textSize(textSize: Double): Unit = {
    val size = textSize.ceil.toInt
    font = font.copy(size = size)
    val style = s"%dpx %s" format (font.size, font.name)
    ctx.font = style
  }
  def text(str: String, originX: Double, originY: Double, rotation: Double): Unit = {
    ctx.globalAlpha = fillAlpha
    ctx.save()
    ctx.translate(originX, originY);
    ctx.rotate(-rotation)
    ctx.fillText(str, 0.0, 0.0)
    ctx.restore()
  }

  def ellipse(centerX: Double, centerY: Double, w: Double, h: Double): Unit = {
    def oval(cx: Double, cy: Double, rx: Double, ry: Double, commit: (CanvasRenderingContext2D) => Unit): Unit = {
      ctx.save()
      ctx.beginPath()
      ctx.translate(cx - rx, cy - ry)
      ctx.scale(rx, ry)
      ctx.arc(1, 1, 1, 0, 2 * math.Pi)
      ctx.restore()
      commit(ctx)
    }
    def circle(x: Double, y: Double, r: Double, commit: (CanvasRenderingContext2D) => Unit): Unit = {
      ctx.beginPath()
      ctx.arc(x, y, r, 0, math.Pi * 2)
      commit(ctx)
    }

    if (isFill) {
      ctx.globalAlpha = fillAlpha
      if (w == h) circle(centerX, centerY, w, ctx => ctx.fill())
      else oval(centerX, centerY, w, h, ctx => ctx.fill())
    }
    if (isStroke) {
      ctx.globalAlpha = strokeAlpha
      if (w == h) circle(centerX, centerY, w, ctx => ctx.stroke())
      else oval(centerX, centerY, w, h, ctx => ctx.stroke())
    }
  }

  def poli(poli: List[DoctusPoint]): Unit = {
    poli match {
      case Nil      => // Nothing to do with no points
      case p :: Nil => // Nothing to do with one point
      case start :: rest =>
        ctx.beginPath()
        ctx.moveTo(start.x, start.y)
        rest foreach (p => ctx.lineTo(p.x, p.y))
        ctx.closePath()
        if (isFill) {
          ctx.globalAlpha = fillAlpha
          ctx.fill()
        }
        if (isStroke) {
          ctx.globalAlpha = strokeAlpha
          ctx.stroke()
        }
    }
  }
  def rect(originX: Double, originY: Double, width: Double, height: Double): Unit = {
    if (isFill) {
      ctx.globalAlpha = fillAlpha
      ctx.fillRect(originX - 0.5, originY - 0.5, width, height)
    }
    if (isStroke) {
      ctx.globalAlpha = strokeAlpha
      ctx.strokeRect(originX - 0.5, originY - 0.5, width, height)
    }

  }

  protected var imageMode: ImageMode = ImageModeCORNER

  def imageMode(imageMode: ImageMode): Unit = {
    this.imageMode = imageMode
  }

  def image(img: DoctusImage, originX: Double, originY: Double): Unit = {
    def draw(_img: DoctusImageScalajs, x: Double, y: Double, _imageMode: ImageMode): Unit = {
      _imageMode match {
        case ImageModeCORNER =>
          ctx.drawImage(_img.image, x, y)
        case ImageModeCENTER =>
          val _x = x - _img.width / 2.0 / _img.scaleFactor
          val _y = y - _img.height / 2.0 / _img.scaleFactor
          ctx.drawImage(_img.image, _x, _y)
      }
    }

    img match {
      case i: DoctusImageScalajs =>
        if (i.image.complete) {
          try {
            ctx.globalAlpha = 255
            ctx.scale(i.scaleFactor, i.scaleFactor)
            draw(i, originX / i.scaleFactor, originY / i.scaleFactor, this.imageMode)
          } finally {
            ctx.scale(1 / i.scaleFactor, 1 / i.scaleFactor)
          }
        } else {
          val im = this.imageMode
          i.image.addEventListener("load", (e: Event) => {
            try {
              ctx.scale(i.scaleFactor, i.scaleFactor)
              ctx.globalAlpha = 255
              draw(i, originX / i.scaleFactor, originY / i.scaleFactor, im)
            } finally {
              ctx.scale(1 / i.scaleFactor, 1 / i.scaleFactor)
            }
          })
        }
    }
  }
  def line(fromX: Double, fromY: Double, toX: Double, toY: Double): Unit = {
    ctx.globalAlpha = strokeAlpha
    ctx.beginPath()
    ctx.moveTo(fromX, fromY)
    ctx.lineTo(toX, toY)
    ctx.closePath()
    ctx.stroke()
  }
}

import inner._

case class DoctusTemplateCanvasScalajs(elem: HTMLCanvasElement)
  extends DoctusTemplateCanvas with DoctusCanvasScalajs1 with DoctusDraggableScalajs1

/**
 * Implementation using a HTML5 canvas
 */
case class DoctusCanvasScalajs(elem: HTMLCanvasElement) extends DoctusCanvasScalajs1

package inner {

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

}
case class DoctusPointableScalajs(elem: HTMLElement) extends DoctusPointableScalajs1

case class DoctusDraggableScalajs(elem: HTMLElement) extends DoctusDraggableScalajs1

case class DoctusImageScalajs(path: String, scaleFactor: Double = 1.0) extends DoctusImage {

  val image: HTMLImageElement = {
    val _image = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    _image.src = path
    _image
  }

  def width = (image.width * scaleFactor).toInt

  def height = (image.height * scaleFactor).toInt

  def scale(factor: Double): DoctusImage = DoctusImageScalajs(path, scaleFactor * factor)

}
case class DoctusActivatableScalajs(elem: HTMLElement) extends DoctusActivatable {

  val p = DoctusPointableScalajs(elem)

  def onActivated(f: () ⇒ Unit): Unit = p.onStart { _ => f() }
  def onDeactivated(f: () ⇒ Unit): Unit = p.onStop { _ => f() }

}

case class DoctusTextScalajs(elem: Element) extends DoctusText {

  def text: String = {
    elem.textContent
  }

  def text_=(txt: String) = {
    val node: Node = elem.childNodes(0)
    node.textContent = txt
  }
}

case object DoctusSchedulerScalajs extends DoctusScheduler {

  def start(f: () => Unit, duration: Int, initialDelay: Int = 0): DoctusScheduler.Stopper = {
    require(duration > 0, "Duration must be greater than zero. " + duration)
    require(initialDelay >= 0, "Initial delay must be greater equal to zero. " + duration)

    var id = -1
    var stopped = false

    val startInterval = () => {
      if (!stopped) {
        f()
        id = dom.window.setInterval(f, duration)
      }
    }

    dom.window.setTimeout(startInterval, initialDelay)
    
    

    new DoctusScheduler.Stopper {
      // Stops the execution of a Scheduler
      override def stop(): Unit = {
        stopped = true;
        if (id >= 0) dom.window.clearInterval(id)
      }
    }
  }

}

case class DoctusActivatableScalajsKey(elem: Element, keycode: Number) extends DoctusActivatable {

  private var onActOpt: Option[() => Unit] = None
  private var onDeactOpt: Option[() => Unit] = None

  private var active = false

  elem.addEventListener("keydown", (e: Event) => {
    val kevent: KeyboardEvent = e.asInstanceOf[KeyboardEvent]
    if ((kevent.keyCode == keycode.intValue()) && !active) {
      e.preventDefault()
      e.cancelBubble
      active = true
      onActOpt.foreach(f => f())
    }
  })

  elem.addEventListener("keyup", (e: Event) => {
    val kevent: KeyboardEvent = e.asInstanceOf[KeyboardEvent]
    if ((kevent.keyCode == keycode.intValue()) && active) {
      e.preventDefault()
      e.cancelBubble
      active = false
      onDeactOpt.foreach(f => f())
    }
  })

  def onActivated(f: () => Unit): Unit = onActOpt = Some(f)
  def onDeactivated(f: () => Unit): Unit = onDeactOpt = Some(f)

}


