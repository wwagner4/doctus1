package doctus.scalajs

import doctus.core.comp.DoctusSelect
import org.scalajs.jquery._

/** Uses a HTML select box in combination with jQuery The default mapping is the
  * toString mapping
  */
case class DoctusSelectScalajs[T](
    selectBox: JQuery,
    render: T => String = (a: T) => a.toString
) extends DoctusSelect[T] {

  private var index = 0

  protected class ShadowSelect {

    private var items = Vector.empty[T]

    def addItem(item: T): Unit = items = items :+ item

    def selectedItem(index: Int): T = items(index)

  }

  private val shadow = new ShadowSelect

  def addItem(item: T): Unit = {
    val value = index
    val label = {
      val posi = "%5d." format (index + 1)
      s"$posi ${render(item)}"
    }
    val v1 = jQuery("<option/>")
    val optionElem = v1.attr("value", value).html(label)
    selectBox.append(optionElem)
    shadow.addItem(item)
    index += 1
  }

  def selectedItem: T = {
    val indexStr = selectBox.value().asInstanceOf[String]
    shadow.selectedItem(indexStr.toInt)
  }
}
