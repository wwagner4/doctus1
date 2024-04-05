package doctus.scalajs

import doctus.core._
import doctus.core.comp.DoctusText
import doctus.core.template.DoctusTemplateCanvas
import doctus.core.util.DoctusPoint
import doctus.scalajs.impl._
import org.scalajs.dom
import org.scalajs.dom.raw._

import scala.scalajs.js.Any._

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
    ctx.translate(originX, originY)
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
      ctx.globalAlpha = 1.0
      _imageMode match {
        case ImageModeCORNER =>
          ctx.drawImage(_img.image, x, y)
        case ImageModeCENTER =>
          val _x = x - _img.width / 2.0 / _img.scaleFactor
          val _y = y - _img.height / 2.0 / _img.scaleFactor
          ctx.drawImage(_img.image, _x, _y)
      }
      ctx.globalAlpha = fillAlpha
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

case class DoctusTemplateCanvasScalajs(elem: HTMLCanvasElement)
  extends DoctusTemplateCanvas with DoctusCanvasScalajs1 with DoctusDraggableScalajs1
  with DoctusKeyScalajs1

/**
 * Implementation using a HTML5 canvas
 */
case class DoctusCanvasScalajs(elem: HTMLCanvasElement) extends DoctusCanvasScalajs1

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

  def onActivated(f: () => Unit): Unit = p.onStart { _ => f() }
  def onDeactivated(f: () => Unit): Unit = p.onStop { _ => f() }

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
        stopped = true
        if (id >= 0) dom.window.clearInterval(id)
      }
    }
  }

}

case class DoctusKeyScalajs(elem: Element) extends DoctusKeyScalajs1

