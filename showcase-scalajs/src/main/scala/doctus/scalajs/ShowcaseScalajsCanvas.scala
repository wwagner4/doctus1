package doctus.scalajs

import doctus.core.DoctusControllerCanvas
import org.scalajs.dom
import org.scalajs.dom.raw._

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("CanvasMap")
object ShowcaseScalajsCanvas {

  @JSExport
  def main(): Unit = {
    // Get elements from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    // Wrap elements with doctus implementations
    val canvas = DoctusCanvasScalajs(canvasElem)
    val logo = DoctusImageScalajs("src/main/resources/logo.png")
    // Call the controller
    DoctusControllerCanvas(canvas, logo)
  }
}

