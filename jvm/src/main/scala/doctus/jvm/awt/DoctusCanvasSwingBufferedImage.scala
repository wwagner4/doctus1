package doctus.jvm.awt

import java.awt.image.BufferedImage
import java.awt.{Graphics2D, RenderingHints}

import doctus.core.template.DoctusTemplateCanvas
import doctus.core.util.DoctusPoint
import doctus.core.{DoctusGraphics, DoctusKeyCode}

case class DoctusBufferedImage(img: BufferedImage) {

  var paintOpt: Option[DoctusGraphics => Unit] = None

  def width: Int = img.getWidth

  def height: Int = img.getHeight

  def paint(): Unit = {
    val g = img.getGraphics.asInstanceOf[Graphics2D]
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    val doctusGraphics = DoctusGraphicsSwing(g)
    paintOpt.foreach(f => f(doctusGraphics))
  }

}

case class DoctusCanvasSwingBufferedImage(img: DoctusBufferedImage) extends DoctusTemplateCanvas {

  def height: Int = img.height

  def onRepaint(f: DoctusGraphics => Unit): Unit = img.paintOpt = Some(f)

  def repaint(): Unit = img.paint()

  def width: Int = img.width

  override def onStart(f: (DoctusPoint) => Unit): Unit = ()

  override def onStop(f: (DoctusPoint) => Unit): Unit = ()

  override def onKeyReleased(f: (DoctusKeyCode) => Unit): Unit = ()

  override def onKeyPressed(f: (DoctusKeyCode) => Unit): Unit = ()

  override def onDrag(f: (DoctusPoint) => Unit): Unit = ()
}