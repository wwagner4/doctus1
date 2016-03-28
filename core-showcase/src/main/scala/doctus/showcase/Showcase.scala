package doctus.showcase

import doctus.core._
import doctus.core.comp._
import doctus.core.color._
import doctus.core.util._
import doctus.core.text._

case class CanvasCtrl(canvas: DoctusCanvas, logo: DoctusImage) {

  // Define what method should be called on repaint
  canvas.onRepaint(paint)
  // Paint the canvas ones
  canvas.repaint()

  val cnt: Long = 0

  // Define how the canvas should be painted
  def paint(g: DoctusGraphics): Unit = {

    // Fill the background
    g.fill(color.DoctusColorRgb(190, 190, 255), 255)
    g.rect(DoctusPoint(0, 0), canvas.width, canvas.height)

    var yoff = 50
    var xoff = 0
    g.fill(color.DoctusColorBlack, 255)
    g.textFont(DoctusFontMonospace)
    g.textSize(12)
    g.text("Doctus", DoctusPoint(xoff + 10, yoff), 0)
    g.textFont(DoctusFontSerif)
    g.textSize(20)
    g.text("Doctus", DoctusPoint(xoff + 60, yoff), 0)
    g.textFont(DoctusFontSansSerif)
    g.textSize(30)
    g.text("Doctus", DoctusPoint(xoff + 130, yoff), 0)

    yoff += 100
    g.stroke(color.DoctusColorRgb(20, 100, 20), 255)
    g.textSize(15)
    for (rot <- -20 to (210, 45)) {
      g.text("___ Doctus " + rot, DoctusPoint(xoff + 10 + 100, yoff), rot * math.Pi / 180)
    }

    yoff += 100
    g.stroke(color.DoctusColorBlack, 255)
    g.textSize(15)
    g.line(DoctusPoint(xoff + 10, yoff), DoctusPoint(xoff + 500, yoff))
    g.line(DoctusPoint(xoff + 100, yoff - 100), DoctusPoint(xoff + 100, yoff + 50))
    g.fill(DoctusColorBlue, 255)
    g.text("Doctus 90 ____", DoctusPoint(xoff + 100, yoff), math.Pi / 2.0)
    g.fill(DoctusColorGreen, 255)
    g.text("Doctus 180 ____", DoctusPoint(xoff + 100, yoff), math.Pi)
    g.fill(DoctusColorRed, 255)
    g.text("Doctus 270 ____", DoctusPoint(xoff + 100, yoff), 3.0 * math.Pi / 2.0)
    g.fill(DoctusColorBlue, 255)
    g.text("Doctus 0 ____", DoctusPoint(xoff + 100, yoff), 0.0)
    g.fill(DoctusColorBlack, 255)

    yoff += 60
    g.stroke(color.DoctusColorBlack, 255)
    g.noFill()
    g.rect(DoctusPoint(xoff + 10, yoff), 10, 10)
    g.strokeWeight(2)
    g.rect(DoctusPoint(xoff + 30, yoff), 20, 20)
    g.strokeWeight(4)
    g.rect(DoctusPoint(xoff + 60, yoff), 30, 30)
    g.strokeWeight(8)
    g.rect(DoctusPoint(xoff + 100, yoff), 40, 40)
    g.strokeWeight(16)
    g.rect(DoctusPoint(xoff + 150, yoff), 50, 50)

    yoff += 60
    g.strokeWeight(1)
    g.fill(color.DoctusColorOrange, 255)
    g.stroke(color.DoctusColorBlack, 255)
    g.rect(DoctusPoint(xoff + 10, yoff), 10, 10)
    g.fill(color.DoctusColorRed, 150)
    g.rect(DoctusPoint(xoff + 30, yoff), 20, 20)
    g.fill(color.DoctusColorBlue, 100)
    g.rect(DoctusPoint(xoff + 60, yoff), 30, 30)
    g.stroke(DoctusColorGreen, 255)
    g.fill(color.DoctusColorGreen, 50)
    g.rect(DoctusPoint(xoff + 100, yoff), 40, 40)
    g.fill(color.DoctusColorOrange, 20)
    g.rect(DoctusPoint(xoff + 150, yoff), 50, 50)

    yoff += 60
    g.noStroke()
    g.fill(color.DoctusColorOrange, 255)
    g.rect(DoctusPoint(xoff + 10, yoff), 10, 10)
    g.fill(color.DoctusColorRed, 150)
    g.rect(DoctusPoint(xoff + 30, yoff), 20, 20)
    g.fill(color.DoctusColorBlue, 100)
    g.rect(DoctusPoint(xoff + 60, yoff), 30, 30)
    g.fill(color.DoctusColorGreen, 50)
    g.rect(DoctusPoint(xoff + 100, yoff), 40, 40)
    g.fill(color.DoctusColorOrange, 20)
    g.rect(DoctusPoint(xoff + 150, yoff), 50, 50)

    yoff += 80
    g.stroke(color.DoctusColorBlack, 255)
    g.stroke(DoctusColorBlue, 255)
    g.noFill()
    g.ellipse(DoctusPoint(xoff + 10, yoff), 5, 5)
    g.ellipse(DoctusPoint(xoff + 30, yoff), 10, 10)
    g.ellipse(DoctusPoint(xoff + 60, yoff), 15, 15)
    g.stroke(DoctusColorMangenta, 255)
    g.ellipse(DoctusPoint(xoff + 100, yoff), 20, 20)
    g.ellipse(DoctusPoint(xoff + 160, yoff), 25, 5)
    g.stroke(DoctusColorTurquois, 255)
    g.ellipse(DoctusPoint(xoff + 160, yoff), 5, 25)
    g.ellipse(DoctusPoint(xoff + 250, yoff), 35, 25)

    yoff += 60
    g.noStroke()
    g.fill(color.DoctusColorYellow, 255)
    g.ellipse(DoctusPoint(xoff + 10, yoff), 5, 5)
    g.fill(color.DoctusColorRed, 150)
    g.ellipse(DoctusPoint(xoff + 30, yoff), 10, 10)
    g.fill(color.DoctusColorGreen, 150)
    g.ellipse(DoctusPoint(xoff + 60, yoff), 15, 15)
    g.fill(color.DoctusColorBlue, 150)
    g.ellipse(DoctusPoint(xoff + 100, yoff), 20, 20)
    g.fill(color.DoctusColorMangenta, 150)
    g.ellipse(DoctusPoint(xoff + 160, yoff), 5, 25)
    g.fill(color.DoctusColorTurquois, 150)
    g.ellipse(DoctusPoint(xoff + 160, yoff), 25, 5)
    g.fill(color.DoctusColorOrange, 150)
    g.ellipse(DoctusPoint(xoff + 250, yoff), 35, 25)

    yoff += 40
    g.stroke(DoctusColorBlue, 255)
    g.strokeWeight(10)
    g.fill(DoctusColorYellow, 255)
    (0 to 4).foreach(d => g.rect(25 + xoff + 20 * d, yoff, 20, 20))

    g.noStroke()
    g.fill(DoctusColorYellow, 255)
    (0 to 4).foreach(d => g.rect(150 + xoff + 20 * d, yoff, 20, 20))

    yoff = 30
    xoff = 300
    g.strokeWeight(1)
    g.stroke(color.DoctusColorBlack, 255)
    g.fill(color.DoctusColorBlack, 255)
    g.textSize(10)
    g.text(s"canvas size = ${canvas.width} ${canvas.height}", DoctusPoint(xoff + 10, yoff), 0)

    yoff += 20
    g.imageMode(ImageModeCORNER)
    g.image(logo.scale(0.1), DoctusPoint(xoff + 10, yoff))
    g.image(logo.scale(0.15), DoctusPoint(xoff + 30, yoff))
    g.image(logo.scale(0.18), DoctusPoint(xoff + 60, yoff))
    g.image(logo.scale(0.22), DoctusPoint(xoff + 100, yoff))
    drawImage(g, logo, xoff + 150, yoff, 0.25, true)
    g.imageMode(ImageModeCENTER)
    drawImage(g, logo, xoff + 270, yoff, 0.25, true)
    g.imageMode(ImageModeCORNER)

    yoff += 100
    g.strokeWeight(1)
    (0 to (250, 2)) foreach (i =>
      g.line(DoctusPoint(xoff + 10 + i, yoff), DoctusPoint(xoff + 10 + i, yoff + ampl(i))))

    yoff += 70
    g.strokeWeight(2)
    g.stroke(color.DoctusColorBlue, 255)
    g.noFill()
    g.poli(points(100, xoff + 10, xoff + 300, yoff, yoff + 100))

    yoff += 70
    g.strokeWeight(2)
    g.stroke(color.DoctusColorBlue, 255)
    g.fill(color.DoctusColorGreen, 255)
    g.poli(points(10, xoff + 10, xoff + 300, yoff, yoff + 100))

    yoff += 70
    g.noStroke()
    g.fill(color.DoctusColorBlue, 100)
    g.poli(points(10, xoff + 10, xoff + 300, yoff, yoff + 100))

    yoff += 100;
    hueShowcase(g, xoff, yoff, DoctusColorOrange)

    yoff += 12;
    hueShowcase(g, xoff, yoff, DoctusColorBlue)

    yoff += 12;
    hueShowcase(g, xoff, yoff, DoctusColorRgb(200, 100, 100))

    yoff += 15;
    saturationShowcase(g, xoff, yoff, DoctusColorRgb(200, 100, 100))
    
    yoff += 12;
    saturationShowcase(g, xoff, yoff, DoctusColorOrange)

    yoff += 12;
    saturationShowcase(g, xoff, yoff, DoctusColorBlue)

    yoff += 15;
    valueShowcase(g, xoff, yoff, DoctusColorRgb(200, 100, 100))
    
    yoff += 12;
    valueShowcase(g, xoff, yoff, DoctusColorOrange)

    yoff += 12;
    valueShowcase(g, xoff, yoff, DoctusColorBlue)
    
    yoff += 30
    val d = 30
    val w = 400
    g.stroke(DoctusColorBlack, 255)
    g.strokeWeight(1)
    g.line(xoff, yoff, xoff + w, yoff)
    g.line(xoff, yoff+ d, xoff + w, yoff + d)

    g.stroke(DoctusColorBlack, 255)
    g.strokeWeight(5)
    var x1off = 10
    g.line(xoff + x1off, yoff, xoff + x1off, yoff + d)
 
    g.strokeWeight(10)
    x1off += 30
    g.line(xoff + x1off, yoff, xoff + x1off, yoff + d)
 
    g.strokeWeight(40)
    x1off += 50
    g.line(xoff + x1off, yoff, xoff + x1off, yoff + d)
 
    g.strokeWeight(100)
    x1off += 130
    g.line(xoff + x1off, yoff, xoff + x1off, yoff + d)
 
  }

  private def drawImage(g: DoctusGraphics, img: DoctusImage, x: Int, y: Int, scale: Double, crossLine: Boolean): Unit = {
    g.image(img.scale(scale), x, y)
    if (crossLine) {
      val b = 100
      g.stroke(DoctusColorBlack, 255)
      g.strokeWeight(1)
      g.line(x - b, y, x + b, y)
      g.line(x, y - b, x, y + b)
    }
  }
  
  private def hueShowcase(g: DoctusGraphics, xoff: Int, yoff: Int, startColor: DoctusColor): Unit = {
    g.noStroke();
    val (sr, sg, sb) = startColor.rgb
    val (h, s, v) = DoctusColorUtil.rgb2hsv(sr, sg, sb)
    val hues = h - 20 to (h + 360 + 20, 10)
    hues.zipWithIndex.foreach {
      case (h1, i) =>
        val (dr, dg, db) = DoctusColorUtil.hsv2rgb(h1, s, v)
        g.fill(DoctusColorRgb(dr, dg, db), 255)
        g.rect(xoff + i * 10, yoff, 10, 10)
    }
  }

  private def saturationShowcase(g: DoctusGraphics, xoff: Int, yoff: Int, startColor: DoctusColor): Unit = {
    g.noStroke();
    val (sr, sg, sb) = startColor.rgb
    val (h, s, v) = DoctusColorUtil.rgb2hsv(sr, sg, sb)
    val saturs = -20 to (100 + 20, 5)
    saturs.zipWithIndex.foreach {
      case (s1, i) =>
        val (dr, dg, db) = DoctusColorUtil.hsv2rgb(h, s1, v)
        g.fill(DoctusColorRgb(dr, dg, db), 255)
        g.rect(xoff + i * 15, yoff, 15, 10)
    }
  }

  private def valueShowcase(g: DoctusGraphics, xoff: Int, yoff: Int, startColor: DoctusColor): Unit = {
    g.noStroke();
    g.strokeWeight(0)
    val (sr, sg, sb) = startColor.rgb
    val (h, s, v) = DoctusColorUtil.rgb2hsv(sr, sg, sb)
    val values = -20 to (100 + 20, 5)
    values.zipWithIndex.foreach {
      case (v1, i) =>
        val (dr, dg, db) = DoctusColorUtil.hsv2rgb(h, s, v1)
        g.fill(DoctusColorRgb(dr, dg, db), 255)
        g.rect(xoff + i * 15, yoff, 15, 10)
    }
  }

  private def ampl(i: Int) = (math.sin(i.toDouble / 50) * 50).toInt + 20

  private def points(n: Int, minx: Int, maxx: Int, miny: Int, maxy: Int): List[DoctusPoint] = {
    val ran = new java.util.Random(12345)
    (1 to n).map(i => {
      val x = (ran.nextDouble() * (maxx - minx)).toInt + minx
      val y = (ran.nextDouble() * (maxy - miny)).toInt + miny
      util.DoctusPoint(x, y)
    }).toList
  }

}

case class AnimatedCtrl(canvas: DoctusCanvas, scheduler: DoctusScheduler, logo: DoctusImage) {

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




sealed trait ActionTyp

case class Started(p: DoctusPoint, cnt: Int) extends ActionTyp
case class Stopped(p: DoctusPoint, cnt: Int) extends ActionTyp
case class Dragged(p: DoctusPoint, cnt: Int) extends ActionTyp

case class PointableCtrl(pointable: DoctusPointable, canvas: DoctusCanvas) {

  var actCount = 0
  var init = true
  var actions = List.empty[ActionTyp]

  pointable.onStart { started }
  pointable.onStop { stopped }
  canvas.onRepaint { paint }

  def paint(g: DoctusGraphics): Unit = {
    if (init) {
      g.noStroke()
      g.fill(DoctusColorYellow, 30)
      g.rect(0, 0, canvas.width, canvas.height)
      init = false
    }
    synchronized {
      actions.foreach {
        _ match {
          case a: Started =>
            g.fill(DoctusColorRed, 100)
            g.ellipse(DoctusPoint(a.p.x, a.p.y), 10, 10)
            drawNum(g, a.p, a.cnt)
          case a: Stopped =>
            g.fill(DoctusColorGreen, 100)
            g.ellipse(DoctusPoint(a.p.x, a.p.y), 5, 5)
            drawNum(g, a.p, a.cnt)
          case a: Dragged =>
            // Nothing to do
        }
      }
      actions = List.empty[ActionTyp]
    }
  }

  def drawNum(g: DoctusGraphics, p: DoctusPoint, num: Int): Unit = {
    val a = 40
    val h = 10
    g.stroke(DoctusColorBlack, 255)
    num % 8 match {
      case 0 =>
        val x1 = p.x + a
        val y1 = p.y
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1), 0)
      case 1 =>
        val x1 = p.x + a
        val y1 = p.y + a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1 + h), 0)
      case 2 =>
        val x1 = p.x
        val y1 = p.y + a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1 + h), 0)
      case 3 =>
        val x1 = p.x - a
        val y1 = p.y + a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1 + h), 0)
      case 4 =>
        val x1 = p.x - a
        val y1 = p.y
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1), 0)
      case 5 =>
        val x1 = p.x - a
        val y1 = p.y - a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1), 0)
      case 6 =>
        val x1 = p.x
        val y1 = p.y - a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1), 0)
      case 7 =>
        val x1 = p.x + a
        val y1 = p.y - a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1), 0)
    }
  }

  def started(p: DoctusPoint): Unit = {
    synchronized {
      actions ::= Started(p, actCount)
      actCount += 1
      canvas.repaint()
    }
  }
  def stopped(p: DoctusPoint): Unit = {
    synchronized {
      actions ::= Stopped(p, actCount)
      actCount += 1
      canvas.repaint()
    }
  }

}

case class DraggableCtrl(draggable: DoctusDraggable, canvas: DoctusCanvas) {

  var actCount = 0

  var actions = List.empty[ActionTyp]

  draggable.onStart { started }
  draggable.onStop { stopped }
  draggable.onDrag { dragged }
  
  canvas.onRepaint { paint }

  def paint(g: DoctusGraphics): Unit = {
    synchronized {
      actions.foreach {
        _ match {
          case a: Started =>
            g.fill(DoctusColorRed, 100)
            g.ellipse(DoctusPoint(a.p.x, a.p.y), 10, 10)
            drawNum(g, a.p, a.cnt)
          case a: Stopped =>
            g.fill(DoctusColorGreen, 100)
            g.ellipse(DoctusPoint(a.p.x, a.p.y), 5, 5)
            drawNum(g, a.p, a.cnt)
          case a: Dragged =>
            g.fill(DoctusColorYellow, 100)
            g.ellipse(DoctusPoint(a.p.x, a.p.y), 2, 2)
        }
      }
      actions = List.empty[ActionTyp]
    }
  }

  def drawNum(g: DoctusGraphics, p: DoctusPoint, num: Int): Unit = {
    val a = 40
    val h = 10
    g.fill(DoctusColorBlack, 255)
    num % 8 match {
      case 0 =>
        val x1 = p.x + a
        val y1 = p.y
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1), 0)
      case 1 =>
        val x1 = p.x + a
        val y1 = p.y + a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1 + h), 0)
      case 2 =>
        val x1 = p.x
        val y1 = p.y + a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1 + h), 0)
      case 3 =>
        val x1 = p.x - a
        val y1 = p.y + a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1 + h), 0)
      case 4 =>
        val x1 = p.x - a
        val y1 = p.y
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1), 0)
      case 5 =>
        val x1 = p.x - a
        val y1 = p.y - a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1), 0)
      case 6 =>
        val x1 = p.x
        val y1 = p.y - a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1), 0)
      case 7 =>
        val x1 = p.x + a
        val y1 = p.y - a
        g.line(DoctusPoint(p.x, p.y), DoctusPoint(x1, y1))
        g.text("" + num, DoctusPoint(x1, y1), 0)
    }
  }

  def started(p: DoctusPoint): Unit = {
    synchronized {
      actions ::= Started(p, actCount)
      actCount += 1
      canvas.repaint()
    }
  }
  def stopped(p: DoctusPoint): Unit = {
    synchronized {
      actions ::= Stopped(p, actCount)
      actCount += 1
      canvas.repaint()
    }
  }

  def dragged(p: DoctusPoint): Unit = {
    synchronized {
      actions ::= Dragged(p, actCount)
      actCount += 1
      canvas.repaint()
    }
  }

}

case class ComponentCtrl(
  pointable01: DoctusPointable,
  pointable02: DoctusPointable,
  upKey04: DoctusKey,
  activatable01: DoctusActivatable,
  select03: DoctusSelect[FullName],
  activatable03: DoctusActivatable,
  info: DoctusText) {

  var count = 0

  pointable01.onStart((p) => {
    info.text = s"$count - You started 'pointable 01' at $p"
    count += 1
  })

  pointable01.onStop((p) => {
    info.text = s"$count - You stopped 'stoppable 01' at $p"
    count += 1
  })

  pointable02.onStart((p) => {
    info.text = s"$count - You started 'pointable 02' at $p"
    count += 1
  })

  pointable02.onStop((p) => {
    info.text = s"$count - You stopped 'pointable 02' at $p"
    count += 1
  })

  upKey04.onActivated((key) => {
    info.text = s"$count - You pressed the $key key"
    count += 1
  })

  upKey04.onDeactivated((key) => {
    info.text = s"$count - You released the $key key"
    count += 1
  })

  activatable01.onDeactivated(() => {
    info.text = s"$count - You clicked 'clickable 01'"
    count += 1
  })

  select03.addItem(FullName("Kurt", "Wallander"))
  select03.addItem(FullName("Linda", "Wallander"))
  select03.addItem(FullName("Baipa", "Liepa"))

  activatable03.onDeactivated(() => {
    info.text = s"$count - select 03 = ${select03.selectedItem}"
    count += 1
  })
}

case class FullName(first: String, last: String)

/**
 * Demonstrates how a scheduler can be started and stopped
 */
case class SchedulerStopCtrl(sched: DoctusScheduler, start: DoctusActivatable, stop: DoctusActivatable, canv: DoctusCanvas) {

  var cnt = 0

  canv.onRepaint(g => {
    val r = 111
    val fs = 50
    val x = canv.width / 2.0
    val y = canv.height / 2.0
    if (stopper.isEmpty) {
      g.fill(DoctusColorOrange, 255)
    } else {
      g.fill(DoctusColorYellow, 255)
    }
    g.ellipse(DoctusPoint(x, y), r, r)
    g.fill(DoctusColorBlack, 255)
    g.textSize(fs)
    g.textFont(DoctusFontMonospace)
    g.text(cnt.toString, x - fs / 2.0, y + fs / 4.0, 0)
  })

  sched.start(canv.repaint, 10)

  var stopper: List[DoctusScheduler.Stopper] = List.empty[DoctusScheduler.Stopper]

  start.onDeactivated(() => {
    stopper ::= sched.start(() => {
      cnt += 1
    }, 500, 2000)
  })

  stop.onDeactivated(() => {
    stopper.foreach(_.stop())
    stopper = List.empty[DoctusScheduler.Stopper]
  })

}

