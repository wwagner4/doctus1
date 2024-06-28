package doctus.core.comp

/** A multiple select box
  */
trait DoctusSelect[T] {
  def setItems(
      items: Seq[T],
      itemDescription: SelectItemDescription[T] =
        DoctusSelect.defaultSelectItemDescription()
  ): Unit
  def selectedItem: Option[T]
}

case class SelectItemDescription[T](
    columnWidths: Seq[Int],
    extractColumnString: (T, Int) => String
)

object DoctusSelect {
  def defaultSelectItemDescription[T](columnWidth: Int = 100) =
    SelectItemDescription(Seq(columnWidth), (elem: T, _) => elem.toString)
}

/** A component for displaying a text
  */
trait DoctusText {
  def text: String
  def text_=(txt: String): Unit
}

/** A container containing multiple panels where only one is visible.
  */
trait DoctusCard {
  def next(): Unit
  def previous(): Unit
  def show(name: String): Unit
}
