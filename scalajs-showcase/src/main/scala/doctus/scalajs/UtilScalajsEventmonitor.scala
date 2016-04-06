package doctus.scalajs

import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement

@JSExport("Eventmonitor")
object UtilScalajsEventmonitor {

  @JSExport
  def main() {
    // Get elements from html page
    val infoElem = dom.document.getElementById("term").asInstanceOf[HTMLCanvasElement]
    val bodyElem =  dom.document.body
    // Call the controller
    Eventmonitor1Ctrl(bodyElem, infoElem)
  }
  
}
