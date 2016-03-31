package doctus.scalajs

import doctus.showcase._
import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.raw._
import org.scalajs.jquery.jQuery
import scala.scalajs.js.annotation.JSExport
import doctus.core.DoctusFont

@JSExport("ComponentMap")
object ComponentMap {

  @JSExport
  def main() {
    val infoElem: HTMLElement = dom.document.getElementById("info").asInstanceOf[HTMLElement]
    val activatable01Elem: HTMLElement = dom.document.getElementById("activatable_01").asInstanceOf[HTMLElement]
    val activatable02Elem: HTMLElement = dom.document.getElementById("activatable_02").asInstanceOf[HTMLElement]
    val button01Elem: HTMLElement = dom.document.getElementById("button_01").asInstanceOf[HTMLElement]
    val select01Elem: HTMLElement = dom.document.getElementById("select_01").asInstanceOf[HTMLElement]
    val selectButton01Elem: HTMLElement = dom.document.getElementById("button_select_01").asInstanceOf[HTMLElement]
    
    ComponentCtrl(
      DoctusPointableScalajs(activatable01Elem),
      DoctusPointableScalajs(activatable02Elem),
      DoctusKeyScalajs(dom.document.body),
      DoctusActivatableScalajs(button01Elem),
      DoctusSelectScalajs[FullName](jQuery(select01Elem), (fn) => s"${fn.first} -- ${fn.last}"),
      DoctusActivatableScalajs(selectButton01Elem),
      DoctusTextScalajs(infoElem))
  }

}

@JSExport("CanvasMap")
object CanvasMap {

  @JSExport
  def main() {
    // Get elements from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    // Wrap elements with doctus implementations
    val canvas = DoctusCanvasScalajs(canvasElem)
    val logo = DoctusImageScalajs("src/main/resources/logo.png")
    // Call the controller
    CanvasCtrl(canvas, logo)
  }
}

@JSExport("PointedMap")
object PointedMap {

  @JSExport
  def main() {
    // Get elements from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    // Wrap elements with doctus implementations
    val canvas = DoctusCanvasScalajs(canvasElem)
    val point = DoctusPointableScalajs(canvasElem)
    // Call the controller
    PointableCtrl(point, canvas)
  }
}

@JSExport("DraggedMap")
object DraggedMap {

  @JSExport
  def main() {
    // Get elements from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    // Wrap elements with doctus implementations
    val canvas = DoctusCanvasScalajs(canvasElem)
    val drag = DoctusDraggableScalajs(canvasElem)
    // Call the controller
    DraggableCtrl(drag, canvas)
  }
}

@JSExport("Eventmonitor1")
object Eventmonitor1 {

  @JSExport
  def main() {
    // Get elements from html page
    val infoElem = dom.document.getElementById("term").asInstanceOf[HTMLCanvasElement]
    val bodyElem =  dom.document.body
    // Call the controller
    Eventmonitor1Ctrl(bodyElem, infoElem)
  }
}

@JSExport("AnimatedMap")
object AnimatedMap {

  @JSExport
  def main() {
    // Get element from html page
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    // Create doctus wrappers
    val sched = DoctusSchedulerScalajs
    val canvas = DoctusCanvasScalajs(canvasElem)
    val logo = DoctusImageScalajs("src/main/resources/logo.png")
    // call the controller
    AnimatedCtrl(canvas, sched, logo)
  }
}

@JSExport("SchedulerStopMap")
object SchedulerStopMap {

  @JSExport
  def main() {

    // Get elements from html
    val canvasElem: HTMLCanvasElement = dom.document.getElementById("canvas").asInstanceOf[HTMLCanvasElement]
    val startElem: HTMLElement = dom.document.getElementById("start").asInstanceOf[HTMLElement]
    val stopElem: HTMLElement = dom.document.getElementById("stop").asInstanceOf[HTMLElement]

    // Apply doctus wrappers
    val sched = DoctusSchedulerScalajs
    val start = DoctusActivatableScalajs(startElem)
    val stop = DoctusActivatableScalajs(stopElem)
    val canv = DoctusCanvasScalajs(canvasElem)

    // Call the controller
    SchedulerStopCtrl(sched, start, stop, canv)
  }
}

