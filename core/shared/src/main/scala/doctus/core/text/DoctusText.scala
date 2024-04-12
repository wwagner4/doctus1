package doctus.core.text

import doctus.core._

/** Monospaced font
  */
case object DoctusFontMonospace extends DoctusFont

/** Font with serifs
  */
case object DoctusFontSerif extends DoctusFont

/** Font without serifs
  */
case object DoctusFontSansSerif extends DoctusFont

/** Make sure the font exists on the platform on which you are using doctus
  */
case class DoctusFontNamed(name: String) extends DoctusFont
