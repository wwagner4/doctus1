package doctus.jvm.awt

import java.awt.geom.AffineTransform
import java.awt.{BasicStroke, Component, Font, Graphics2D}
import javax.swing.ImageIcon

import doctus.core._
import doctus.core.template.DoctusTemplateCanvas
import doctus.core.util.DoctusPoint
import doctus.jvm.awt.impl.{DoctusCanvasSwing1, DoctusDraggableSwing1, DoctusKeySwing1}

case class DoctusGraphicsSwing(graphics: Graphics2D) extends DoctusGraphics {

  var fill = new java.awt.Color(100, 100, 100, 255)
  var strokeColor = new java.awt.Color(0, 0, 0, 255)
  var isStroke: Boolean = true
  var isFill: Boolean = true

  def ellipse(centerX: Double, centerY: Double, a: Double, b: Double): Unit = {
    if (isFill) {
      graphics.setColor(fill)
      val w = a * 2
      val h = b * 2
      graphics.fillOval((centerX - a).toInt, (centerY - b).toInt, w.toInt, h.toInt)
    }
    if (isStroke) {
      graphics.setColor(strokeColor)
      val w = a * 2
      val h = b * 2
      graphics.drawOval((centerX - a).toInt, (centerY - b).toInt, w.toInt, h.toInt)
    }

  }

  def fill(c: doctus.core.DoctusColor, alpha: Double): Unit = {
    fill = toAwtColor(c, alpha)
    isFill = true
  }

  def noFill(): Unit = isFill = false

  protected var imageMode: ImageMode = ImageModeCORNER

  def imageMode(imageMode: ImageMode): Unit = {
    this.imageMode = imageMode
  }

  def image(img: DoctusImage, originX: Double, originY: Double): Unit = {
    img match {
      case i: DoctusImageSwing =>
        val trans = this.imageMode match {
          case ImageModeCORNER => AffineTransform.getTranslateInstance(originX, originY)
          case ImageModeCENTER =>
            val x = originX - img.width * i.scaleFactor / 2.0
            val y = originY - img.height * i.scaleFactor / 2.0
            AffineTransform.getTranslateInstance(x, y)
        }
        trans.concatenate(AffineTransform.getScaleInstance(i.scaleFactor, i.scaleFactor))
        graphics.drawImage(i.icon.getImage, trans, null)
      case _ => throw new IllegalStateException("Unknown implementation for 'DoctusImage'. %s" format img.getClass)
    }
  }

  def line(fromX: Double, fromY: Double, toX: Double, toY: Double): Unit = {
    if (isStroke) {
      graphics.setColor(strokeColor)
      graphics.drawLine(fromX.toInt, fromY.toInt, toX.toInt, toY.toInt)
    }
  }

  def poli(poli: List[DoctusPoint]): Unit = {
    if (isFill) {
      graphics.setColor(fill)
      val xValues = poli.map(_.x.toInt).toArray
      val yValues = poli.map(_.y.toInt).toArray
      graphics.fillPolygon(xValues, yValues, poli.size)
    }
    if (isStroke) {
      graphics.setColor(strokeColor)
      val xValues = poli.map(_.x.toInt).toArray
      val yValues = poli.map(_.y.toInt).toArray
      graphics.drawPolygon(xValues, yValues, poli.size)
    }

  }

  def rect(originX: Double, originY: Double, width: Double, height: Double): Unit = {
    if (isFill) {
      graphics.setColor(fill)
      graphics.fillRect(originX.toInt, originY.toInt, width.toInt, height.toInt)
    }
    if (isStroke) {
      graphics.setColor(strokeColor)
      graphics.drawRect(originX.toInt, originY.toInt, width.toInt, height.toInt)
    }
  }

  def stroke(c: doctus.core.DoctusColor, alpha: Double): Unit = {
    strokeColor = toAwtColor(c, alpha)
    isStroke = true
  }

  def noStroke(): Unit = isStroke = false

  def strokeWeight(weight: Double): Unit = {
    graphics.setStroke(new BasicStroke(weight.toFloat, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f))
  }

  var textFontSize: Float = 12.0f

  def textFont(font: DoctusFont): Unit = {
    import doctus.core.text._
    val f = font match {
      case DoctusFontMonospace => new Font("Monospaced", Font.PLAIN, textFontSize.toInt)
      case DoctusFontSerif => new Font("Serif", Font.PLAIN, textFontSize.toInt)
      case DoctusFontSansSerif => new Font("SansSerif", Font.PLAIN, textFontSize.toInt)
      case DoctusFontNamed(name) => new Font(name, Font.PLAIN, textFontSize.toInt)
    }
    graphics.setFont(f)
  }

  def textSize(textSize: Double): Unit = {
    textFontSize = textSize.toFloat
    val font = graphics.getFont
    graphics.setFont(font.deriveFont(textSize.toFloat))

  }

  def text(str: String, originX: Double, originY: Double, rotation: Double): Unit = {
    graphics.setColor(fill)
    val t = graphics.getTransform
    val at = new AffineTransform()
    at.translate(originX, originY)
    at.rotate(-rotation)
    graphics.transform(at)
    graphics.drawString(str, 0, 0)
    graphics.setTransform(t)
  }

  private def toAwtColor(c: DoctusColor, alpha: Double): java.awt.Color = {
    def minmax(value: Int): Int = math.max(0, math.min(255, value))
    c.rgb match {
      case (r, g, b) =>
        val red = minmax(r)
        val green = minmax(g)
        val blue = minmax(b)
        val a = minmax(alpha.toInt)
        new java.awt.Color(red, green, blue, a)
    }
  }

}

/**
  * Extended scala.swing.Component with some extra functionality
  * needed for SwingCanvas
  */
trait DoctusComponent extends Component {
  def paintOpt_=(paintOpt: Option[DoctusGraphics => Unit])

  def paintOpt: Option[DoctusGraphics => Unit]
}


case class DoctusCanvasSwing(comp: DoctusComponent) extends DoctusCanvasSwing1

case class DoctusTemplateCanvasSwing(comp: DoctusComponent)
  extends DoctusTemplateCanvas with DoctusCanvasSwing1 with DoctusDraggableSwing1
    with DoctusKeySwing1

case class DoctusImageSwing(resource: String, scaleFactor: Double = 1.0) extends DoctusImage {

  val icon: ImageIcon = {
    val imgPath = resource
    val imgr = getClass.getClassLoader.getResource(imgPath)
    assert(imgr != null, s"Found no resource for $imgPath")
    new ImageIcon(imgr)
  }

  def scale(factor: Double): doctus.core.DoctusImage = DoctusImageSwing(resource, scaleFactor * factor)

  def width = icon.getIconWidth

  def height = icon.getIconHeight

}

