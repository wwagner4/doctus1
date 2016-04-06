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

    for (i <- 1 to 1000) {
      g.fill(ranColor, 20)
      g.stroke(DoctusColorBlue, 5)
      g.strokeWeight(3)
      g.ellipse(DoctusPoint(ran.nextInt(w), ran.nextInt(h)), 100, 100)
    }
  }

  def pointableDragged(pos: DoctusPoint): Unit = () // Nothing to do in a still image
  def pointablePressed(pos: DoctusPoint): Unit = () // Nothing to do in a still image
  def pointableReleased(pos: DoctusPoint): Unit = () // Nothing to do in a still image
  def keyPressed(code: DoctusKeyCode): Unit = () // Nothing to do in a still image

}