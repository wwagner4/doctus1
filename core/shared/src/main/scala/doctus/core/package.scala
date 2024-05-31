package doctus.core

/** Implementations and Utilities around DoctusColor
  */
package object color {}

/** Utility functionality that is not bound to specific interface classes
  */
package object util {

  def cropString(str: String, len: Int): String = {
    if (len <= 0) ""
    else if (str.length > len && len >= 10) {
      val v = str.take(len - 3)
      s"$v..."
    } else {
      val fmt = f"%%${len}s"
      fmt.format(str.take(len))
    }
  }

}

/** Support for GUI-components
  *
  * Currently the main focus of doctus is not to develop a complete set of
  * components for different platforms.
  *
  * Interfaces hosted in this package can be seen as a proof of concept.
  */
package object comp {}

/** Interfaces needed for text handling
  */
package object text {}
