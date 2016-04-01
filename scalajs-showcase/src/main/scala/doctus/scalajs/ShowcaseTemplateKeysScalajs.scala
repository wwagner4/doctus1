package doctus.scalajs

import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement
import doctus.showcase.ShowcaseTemplateKeys
import doctus.core.template.DoctusTemplateController


@JSExport("ShowcaseTemplateKeys")
object ShowcaseTemplateKeysScalajs {
  
  @JSExport
  def main() {

    // Get element from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]

    // Create doctus wrappers
    val templCanvas = DoctusTemplateCanvasScalajs(canvasElem)
    val sched = DoctusSchedulerScalajs

    // call the controller
    val templ = ShowcaseTemplateKeys(templCanvas)
    DoctusTemplateController(templ, sched, templCanvas)
  }
}

