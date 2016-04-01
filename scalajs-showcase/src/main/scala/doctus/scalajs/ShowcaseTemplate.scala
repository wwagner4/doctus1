package doctus.scalajs

import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement

import doctus.core.template.DoctusTemplateController
import doctus.showcase.DoctusTemplateStillImage

@JSExport("StillImage")
object StillImage {

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