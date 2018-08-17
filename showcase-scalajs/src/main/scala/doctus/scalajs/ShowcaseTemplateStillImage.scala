package doctus.scalajs

import doctus.core.DoctusTemplateStillImage
import doctus.core.template.DoctusTemplateController
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("StillImage")
object ShowcaseTemplateStillImage {

  @JSExport
  def main() {

    // Get element from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]

    // Create doctus wrappers
    val templCanvas = DoctusTemplateCanvasScalajs(canvasElem)
    val sched = DoctusSchedulerScalajs

    // call the controller
    val templ = DoctusTemplateStillImage(templCanvas)
    DoctusTemplateController(templ, sched, templCanvas)
  }

}