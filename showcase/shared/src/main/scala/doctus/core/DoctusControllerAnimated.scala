package doctus.core

import doctus.core.util._

case class DoctusControllerAnimated(
    canvas: DoctusCanvas,
    scheduler: DoctusScheduler,
    logo: DoctusImage
) {

  val ran = new java.util.Random

  val center: DoctusPoint = util.DoctusPoint(250, 150)
  val radius = 80

  var count = 0

  canvas.onRepaint(paint)
  canvas.repaint()
  scheduler.start(() => canvas.repaint(), 25)

  def paint(g: DoctusGraphics): Unit = {

    // Fill the background
    g.fill(color.DoctusColorWhite, 100)
    g.rect(DoctusPoint(0, 0), canvas.width, canvas.height)

    val pos = calcPos(count)
    val scale = calcScale(count)

    g.image(logo.scale(scale), DoctusPoint(pos.x, pos.y))
    count = (count + 1) % 1000
  }

  def calcPos(i: Int): DoctusPoint = {
    val speed = 0.02 + math.sin(i * 0.05) * 0.003
    val rad = i * speed
    val x = math.sin(rad) * radius * 2
    val y = math.cos(rad) * radius
    util.DoctusPoint(x + center.x, y + center.y)
  }

  def calcScale(i: Int): Double = {
    0.4 + math.sin(i * 0.01) * 0.3
  }

}
