package doctus.core

import doctus.core.comp.DoctusCard
import doctus.core.util.DoctusPoint

//noinspection DuplicatedCode
case class OwlDoctusController(
    canvas: DoctusCanvas,
    scheduler: DoctusScheduler,
    logo: DoctusImage,
    card: DoctusCard,
    nextButton: DoctusActivatable,
    previousButton: DoctusActivatable,
    owlButton: DoctusActivatable,
    leafButton: DoctusActivatable
) {

  val center: DoctusPoint = util.DoctusPoint(1000, 800)
  private val radius = 300

  var count = 0

  canvas.onRepaint(paint)
  canvas.repaint()
  scheduler.start(() => canvas.repaint(), 25)

  nextButton.onActivated { () =>
    card.next()
  }
  previousButton.onActivated { () =>
    card.previous()
  }
  owlButton.onActivated { () =>
    card.show("owl")
  }
  leafButton.onActivated { () =>
    card.show("leaf")
  }

  def paint(g: DoctusGraphics): Unit = {

    // Fill the background
    g.fill(color.DoctusColorWhite, 256)
    g.rect(DoctusPoint(0, 0), canvas.width, canvas.height)

    for (i <- 0 to 5) {
      val pos = calcPos(count + 500 * i)
      val scale = calcScale(count + 500 * i)
      g.image(logo.scale(scale), DoctusPoint(pos.x, pos.y))
    }

    count = (count + 1) % 100000
  }

  private def calcPos(i: Int): DoctusPoint = {
    val speed = 0.02 + math.sin(i * 0.05) * 0.0005
    val rad = i * speed
    val diff = math.sin(rad / 10) * radius
    val x = math.sin(rad) * radius * 2 + diff
    val y = math.cos(rad) * radius + diff
    util.DoctusPoint(x + center.x, y + center.y)
  }

  private def calcScale(i: Int): Double = {
    0.4 + math.sin(i * 0.01) * 0.3
  }

}

//noinspection DuplicatedCode
case class LeafDoctusController(
    canvas: DoctusCanvas,
    scheduler: DoctusScheduler,
    leafs: Seq[DoctusImage],
    card: DoctusCard,
    nextButton: DoctusActivatable,
    previousButton: DoctusActivatable,
    owlButton: DoctusActivatable,
    leafButton: DoctusActivatable
) {

  val center: DoctusPoint = util.DoctusPoint(1000, 800)
  private val radius = 300

  var count = 0

  canvas.onRepaint(paint)
  canvas.repaint()
  scheduler.start(() => canvas.repaint(), 25)

  nextButton.onActivated { () =>
    card.next()
  }
  previousButton.onActivated { () =>
    card.previous()
  }
  owlButton.onActivated { () =>
    card.show("owl")
  }
  leafButton.onActivated { () =>
    card.show("leaf")
  }

  def paint(g: DoctusGraphics): Unit = {

    // Fill the background
    g.fill(color.DoctusColorBlack, 256)
    g.rect(DoctusPoint(0, 0), canvas.width, canvas.height)

    for (i <- 0 until 40) {
      val pos = calcPos(count + 500 * i)
      val scale = calcScale(count + 500 * i)
      g.image(leafs(i % 4).scale(scale), DoctusPoint(pos.x, pos.y))
    }

    count = (count + 1) % 100000
  }

  private def calcPos(i: Int): DoctusPoint = {
    val speed = 0.02 + math.sin(i * 0.05) * 0.0001
    val rad = i * speed
    val diff = math.sin(rad / 10) * radius
    val x = math.sin(rad) * radius * 2 + diff
    val y = math.cos(rad) * radius + diff
    util.DoctusPoint(x + center.x, y + center.y)
  }

  private def calcScale(i: Int): Double = {
    0.4 + math.sin(i * 0.01) * 0.3
  }

}
