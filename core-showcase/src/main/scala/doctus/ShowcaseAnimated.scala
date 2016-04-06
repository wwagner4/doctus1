package doctus

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
    if (count % 4 == 0) {
      g.fill(color.DoctusColorBlack, 10)
      g.rect(DoctusPoint(0, 0), canvas.width, canvas.height)
    }

    val pos = calcPos(count)

    g.image(logo.scale(0.5), DoctusPoint(pos.x, pos.y))
    g.stroke(DoctusColorYellow, 30)
    g.noFill()
    g.rect(DoctusPoint(pos.x, pos.y), 100, 100)
    count += 1
  }

  def calcPos(i: Int): DoctusPoint = {
    val speedDiff = 0.01 + math.sin(i * 0.005) * 0.003
    val speed = 0.05 + speedDiff
    val rad = i * speed
    val x = math.sin(rad) * radius * 2
    val y = math.cos(rad) * radius
    util.DoctusPoint(x + center.x, y + center.y)
  }

}




