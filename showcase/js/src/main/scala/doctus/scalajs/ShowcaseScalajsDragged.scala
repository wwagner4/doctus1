package doctus.scalajs

import doctus.core.DoctusControllerDraggable
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("DraggedMap")
object ShowcaseScalajsDragged {

  @JSExport
  def main(): Unit = {
    // Get elements from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    // Wrap elements with doctus implementations
    val canvas = DoctusCanvasScalajs(canvasElem)
    val drag = DoctusDraggableScalajs(canvasElem)
    // Call the controller
    DoctusControllerDraggable(drag, canvas)
  }
}

