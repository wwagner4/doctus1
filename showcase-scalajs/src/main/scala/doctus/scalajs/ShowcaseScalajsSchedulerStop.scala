package doctus.scalajs

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement
import org.scalajs.dom.raw.HTMLElement
import doctus.core.DoctusControllerSchedulerStop

@JSExportTopLevel("SchedulerStopMap")
object ShowcaseScalajsSchedulerStop {

  @JSExport
  def main() {

    // Get elements from html
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    val startElem: HTMLElement = dom.document.getElementById("start").asInstanceOf[HTMLElement]
    val stopElem: HTMLElement = dom.document.getElementById("stop").asInstanceOf[HTMLElement]

    // Apply doctus wrappers
    val sched = DoctusSchedulerScalajs
    val start = DoctusActivatableScalajs(startElem)
    val stop = DoctusActivatableScalajs(stopElem)
    val canv = DoctusCanvasScalajs(canvasElem)

    // Call the controller
    DoctusControllerSchedulerStop(sched, start, stop, canv)
  }
}
