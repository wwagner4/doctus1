package doctus.swing

import doctus.core.DoctusCanvas
import doctus.core.DoctusGraphics
import java.awt.image.BufferedImage
import java.awt.Graphics2D
import java.awt.RenderingHints

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

case class DoctusCanvasSwingBufferedImage(img: DoctusBufferedImage) extends DoctusCanvas {

  def height: Int = img.height

  def onRepaint(f: DoctusGraphics => Unit): Unit = img.paintOpt = Some(f)

  def repaint(): Unit = img.paint()

  def width: Int = img.width

}