package doctus.scalajs

import doctus._
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.raw._
import org.scalajs.jquery.jQuery
import scala.scalajs.js.annotation.JSExport
import doctus.core.DoctusFont
import doctus.DoctusControllerCanvas

@JSExport("CanvasMap")
object ShowcaseScalajsCanvas {

  @JSExport
  def main() {
    // Get elements from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    // Wrap elements with doctus implementations
    val canvas = DoctusCanvasScalajs(canvasElem)
    val logo = DoctusImageScalajs("src/main/resources/logo.png")
    // Call the controller
    DoctusControllerCanvas(canvas, logo)
  }
}

