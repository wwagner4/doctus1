package doctus.core

import doctus.core.template.DoctusTemplate
import doctus.core.util.DoctusPoint
import doctus.core.color.DoctusColorBlue
import java.util.Random
import doctus.core.color.DoctusColorUtil
import doctus.core.color.DoctusColorRgb
import doctus.core.color.DoctusColorRgb
import doctus.core.color.DoctusColorRgb
import doctus.core.template.DoctusTemplate
import doctus.core.color.DoctusColorUtil
import doctus.core.color.DoctusColorRgb
import doctus.core.template.DoctusTemplate
import doctus.core.color.DoctusColorUtil
import doctus.core.util.DoctusPoint
import doctus.core.color.DoctusColorWhite

case class DoctusTemplateStillImage(canvas: DoctusCanvas) extends DoctusTemplate {

  val ran = new Random

  // None means there is no animation. The draw method is called ones
  override def frameRate = None

  def draw(g: DoctusGraphics): Unit = {

    val w = canvas.width
    val h = canvas.height

    def ranColor: DoctusColor = {
      val hue = ran.nextInt(360)
      val (r, g, b) = DoctusColorUtil.hsv2rgb(hue, 100, 100)
      DoctusColorRgb(r, g, b)
    }

    g.fill(DoctusColorWhite, 255)
    g.rect(0, 0, w, h)

    g.noStroke()
    var t1 = ran.nextDouble * 100
    var t2 = ran.nextDouble * 100
    for (i <- 1 to 100) {
      val c = ranColor
      g.fill(c, 4)
      val x = ran.nextDouble() * w
      val y = ran.nextDouble() * h
      (10 to (100 + ran.nextInt(500), 10)) foreach { d =>
        val dx = math.sin(t1) * 50
        val dy = math.sin(t2) * 50
        g.ellipse(x + dx, y + dy, d, d)
        t1 += 0.2
        t1 += 0.21
      }
    }
  }

  def pointableDragged(pos: DoctusPoint): Unit = () // Nothing to do in a still image
  def pointablePressed(pos: DoctusPoint): Unit = () // Nothing to do in a still image
  def pointableReleased(pos: DoctusPoint): Unit = () // Nothing to do in a still image
  def keyPressed(code: DoctusKeyCode): Unit = {
    if (code == DKC_Space) this.canvas.repaint()
  }

}