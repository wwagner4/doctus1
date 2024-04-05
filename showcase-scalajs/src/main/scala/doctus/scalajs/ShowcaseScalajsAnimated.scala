package doctus.scalajs

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement
import doctus.core.DoctusControllerAnimated

@JSExportTopLevel("AnimatedMap")
object ShowcaseScalajsAnimated {

  @JSExport
  def main(): Unit = {
    // Get element from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    // Create doctus wrappers
    val sched = DoctusSchedulerScalajs
    val canvas = DoctusCanvasScalajs(canvasElem)
    val logo = DoctusImageScalajs("src/main/resources/logo.png")
    // call the controller
    DoctusControllerAnimated(canvas, sched, logo)
  }
}

