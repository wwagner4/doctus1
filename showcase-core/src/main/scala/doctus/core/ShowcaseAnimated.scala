package doctus.core

import doctus.core._
import doctus.core.comp._
import doctus.core.color._
import doctus.core.util._
import doctus.core.text._

case class DoctusControllerAnimated(canvas: DoctusCanvas, scheduler: DoctusScheduler, logo: DoctusImage) {

  val ran = new java.util.Random

  val center: DoctusPoint = util.DoctusPoint(250, 150)
  val radius = 80

  var count = 0

  canvas.onRepaint(paint)
  canvas.repaint()
  scheduler.start(() => canvas.repaint(), 10)

  def paint(g: DoctusGraphics): Unit = {
    
    // Fill the background
    g.fill(color.DoctusColorWhite, 255)
    g.rect(DoctusPoint(0, 0), canvas.width, canvas.height)

    val pos = calcPos(count)
    val scale = calcScale(count)

    g.image(logo.scale(scale), DoctusPoint(pos.x, pos.y))
    count += 1
  }

  def calcPos(i: Int): DoctusPoint = {
    val speedDiff = 0.01 + math.sin(i * 0.001) * 0.003
    val speed = 0.01 + speedDiff
    val rad = i * speed
    val x = math.sin(rad) * radius * 2
    val y = math.cos(rad) * radius
    util.DoctusPoint(x + center.x, y + center.y)
  }

  def calcScale(i: Int): Double = {
    0.4 + math.sin(i * 0.01) * 0.3
  }

}




