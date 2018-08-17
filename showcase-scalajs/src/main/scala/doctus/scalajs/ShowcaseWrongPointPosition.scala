package doctus.scalajs

import doctus.core.DoctusTemplateWrongPointPosition
import doctus.core.template.DoctusTemplateController
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("WrongPointPosition")
object ShowcaseWrongPointPosition {

  @JSExport
  def main() {

    // Get element from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]

    // Create doctus wrappers
    val templCanvas = DoctusTemplateCanvasScalajs(canvasElem)
    val sched = DoctusSchedulerScalajs

    // call the controller
    val templ = DoctusTemplateWrongPointPosition(templCanvas)
    DoctusTemplateController(templ, sched, templCanvas)
  }

}