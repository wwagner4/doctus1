package doctus.scalajs

import org.scalajs.dom
import org.scalajs.dom.raw._

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Eventmonitor")
object UtilScalajsEventmonitor {

  @JSExport
  def main(): Unit = {
    // Get elements from html page
    val infoElem = dom.document.getElementById("term").asInstanceOf[HTMLCanvasElement]
    val bodyElem = dom.document.body
    // Call the controller
    UtilEventmonitor(bodyElem, infoElem)
  }

}

case class UtilEventmonitor(elem: HTMLElement, info: HTMLElement) {

  case class EventDesc1(eventType: String, f: Event => String)

  def fillChildren1(cont: Seq[String]): Unit = {
    val cns = info.children
    val cnt = cns.length
    val diff = {
      val d = cont.size - cnt
      if (d < 0) 0 else d
    }
    for (i <- 0 until math.min(cnt, cont.size)) {
      val cn = cns(i)
      cn.innerHTML = term.content(i + diff)
    }
  }

  val events = List(
    EventDesc1("touchstart", formatTouchEvent),
    EventDesc1("touchend", formatTouchEvent),
    EventDesc1("touchcancel", formatTouchEvent),
    EventDesc1("touchleave", formatTouchEvent),
    EventDesc1("touchmove", formatTouchEvent),
    EventDesc1("mousedown", formatMouseEvent),
    EventDesc1("mouseenter", formatMouseEvent),
    EventDesc1("mouseleave", formatMouseEvent),
    EventDesc1("mousemove", formatMouseEvent),
    EventDesc1("mouseout", formatMouseEvent),
    EventDesc1("mouseover", formatMouseEvent),
    EventDesc1("mouseup", formatMouseEvent))

  val term = new Terminal(100)

  events.foreach { ed =>
    elem.addEventListener(ed.eventType, (e: Event) => {
      e.preventDefault()
      term.addLine("%s -> %s" format (ed.eventType, ed.f(e)))
      fillChildren1(term.content)
    })
  }

  def formatTouchEvent(e: Event): String = {
    def format(tl: TouchList): String = {
      (for (i <- 0 until tl.length.intValue) yield {
        val t: Touch = tl(i)
        "%.2f - %d %d" format (t.identifier, t.clientX.intValue(), t.clientY.intValue())
      }).mkString("[", ",", "]")
    }
    val te = e.asInstanceOf[TouchEvent]
    "TE %s" format format(te.targetTouches)
  }

  def formatMouseEvent(e: Event): String = {
    val me = e.asInstanceOf[MouseEvent]
    "ME %.2f %.2f" format (me.clientX, me.clientY)
  }
}
