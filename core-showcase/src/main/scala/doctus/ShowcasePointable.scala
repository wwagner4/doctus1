package doctus

import doctus.core._
import doctus.core.comp._
import doctus.core.color._
import doctus.core.util._
import doctus.core.text._

case class DoctusControllerPointable(pointable: DoctusPointable, canvas: DoctusCanvas) {

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

