package doctus.core.comp

/**
 * A multiple select box
 */
trait DoctusSelect[T] {
  def addItem(item: T): Unit
  def selectedItem: T
}

/**
 * A component for displaying a text
 */
trait DoctusText {
  def text: String
  def text_=(txt: String)
}
