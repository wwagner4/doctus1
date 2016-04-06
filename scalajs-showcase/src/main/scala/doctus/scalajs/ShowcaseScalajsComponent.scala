package doctus.scalajs

import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.jquery.jQuery
import doctus._

@JSExport("ComponentMap")
object ShowcaseScalajsComponent {

  @JSExport
  def main() {
    val infoElem: HTMLElement = dom.document.getElementById("info").asInstanceOf[HTMLElement]
    val activatable01Elem: HTMLElement = dom.document.getElementById("activatable_01").asInstanceOf[HTMLElement]
    val activatable02Elem: HTMLElement = dom.document.getElementById("activatable_02").asInstanceOf[HTMLElement]
    val button01Elem: HTMLElement = dom.document.getElementById("button_01").asInstanceOf[HTMLElement]
    val select01Elem: HTMLElement = dom.document.getElementById("select_01").asInstanceOf[HTMLElement]
    val selectButton01Elem: HTMLElement = dom.document.getElementById("button_select_01").asInstanceOf[HTMLElement]
    
    DoctusControllerComponent(
      DoctusPointableScalajs(activatable01Elem),
      DoctusPointableScalajs(activatable02Elem),
      DoctusKeyScalajs(dom.document.body),
      DoctusActivatableScalajs(button01Elem),
      DoctusSelectScalajs[FullName](jQuery(select01Elem), (fn) => s"${fn.first} -- ${fn.last}"),
      DoctusActivatableScalajs(selectButton01Elem),
      DoctusTextScalajs(infoElem))
  }

}

