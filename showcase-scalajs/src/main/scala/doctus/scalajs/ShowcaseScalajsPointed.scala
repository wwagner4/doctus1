package doctus.scalajs

import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement

import doctus.core.DoctusControllerPointable

@JSExport("PointedMap")
object ShowcaseScalajsPointed {

  @JSExport
  def main() {
    // Get elements from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    // Wrap elements with doctus implementations
    val canvas = DoctusCanvasScalajs(canvasElem)
    val point = DoctusPointableScalajs(canvasElem)
    // Call the controller
    DoctusControllerPointable(point, canvas)
  }
}

