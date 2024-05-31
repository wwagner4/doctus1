package doctus.swing

trait OsName
case object Win extends OsName
case object Mac extends OsName
case object Linux extends OsName
case object Unknown extends OsName

object DoctusSwingUtil {
  def osName: OsName = {
    val osn = System.getProperty("os.name").toLowerCase()
    if (osn.startsWith("win")) Win
    else if (osn.startsWith("mac")) Mac
    else if (osn.startsWith("linux")) Linux
    else Unknown
  }

}
