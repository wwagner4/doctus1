package doctus.scalajs

import doctus.core.util._

/**
 * Utility functions for scalajs components
 */
object DoctusScalajsUtil {

  def rgbString(r: Int, g: Int, b: Int): String = {
    def minmax(v: Int): Int = {
      if (v < 0) 0
      else if (v > 255) 255
      else v
    }
    val hr = "%02x" format minmax(r)
    val hg = "%02x" format minmax(g)
    val hb = "%02x" format minmax(b)
    s"#$hr$hg$hb"
  }

}

case class DoctusIdPoint(id: Int, point: DoctusPoint)

sealed trait DoctusEvent
case class TouchStart(point: List[DoctusIdPoint]) extends DoctusEvent
case class TouchEnd(point: List[DoctusIdPoint]) extends DoctusEvent
case class TouchMove(point: List[DoctusIdPoint]) extends DoctusEvent
case class MouseDown(point: DoctusPoint) extends DoctusEvent
case class MouseUp(point: DoctusPoint) extends DoctusEvent
case class MouseMove(point: DoctusPoint) extends DoctusEvent

/**
 * Translates touch-/and mousevents to calls of onStart-/onStop methods
 */
class DoctusEventManager {

  var latestPos: Option[DoctusPoint] = None
  var down = false

  var optStart: Option[DoctusPoint => Unit] = None
  var optStop: Option[DoctusPoint => Unit] = None
  var optDrag: Option[DoctusPoint => Unit] = None

  def onStart(f: (DoctusPoint) => Unit): Unit = optStart = Some(f)
  def onStop(f: (DoctusPoint) => Unit): Unit = optStop = Some(f)
  def onDrag(f: (DoctusPoint) => Unit): Unit = optDrag = Some(f)

  def addEvent(event: DoctusEvent) = {
    event match {
      case MouseDown(p) => {
        optStart.foreach(f => f(p))
        down = true
      }
      case MouseUp(p) => {
        optStop.foreach(f => f(p))
        down = false
      }
      case MouseMove(p) => {
        if (down) optDrag.foreach(f => f(p))
      }
      case TouchStart(piList) =>
        if (piList.size == 1) {
          val p = piList(0).point
          latestPos = Some(p)
          optStart.foreach(f => f(p))
          down = true
        }
      case TouchEnd(piList) =>
        val pi = piList.filter(_.id == 0)
        if (pi.size == 0 && latestPos.isDefined) {
          optStop.foreach(f => f(latestPos.get))
          down = false
          latestPos = None
        }
      case TouchMove(piList) =>
        val pi = piList.filter(_.id == 0)
        if (pi.nonEmpty) {
          val p = pi(0).point
          latestPos = Some(p)
          if (down) optDrag.foreach(f => f(p))
        }
    }

  }

}

/**
 * A kind of terminal that manages a list of strings
 * lines: Number of managed lines
 */
class Terminal(lines: Int) {

  private var cont = Vector.fill(lines)("&nbsp;")

  def addLine(line: String) = cont = cont.drop(1) :+ line

  def content: Seq[String] = cont
}


