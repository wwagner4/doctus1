package doctus.core.comp

/** A multiple select box
  */
trait DoctusSelect[T] {
  def addItem(item: T): Unit
  def selectedItem: T
}

case class SelectItemDescription[T](
    columnWidths: Seq[Int],
    extractColumnString: (T, Int) => String
)

/** A multiple select component
  */
trait DoctusSelect1[T] {
  def setItems(items: Seq[T], itemDescription: SelectItemDescription[T]): Unit
  def selectedItem: Option[T]
}

/** A component for displaying a text
  */
trait DoctusText {
  def text: String
  def text_=(txt: String): Unit
}
