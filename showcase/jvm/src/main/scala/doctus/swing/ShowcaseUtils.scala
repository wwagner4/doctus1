package doctus.swing

import javax.swing._

object ShowcaseUtil {

  def setupSwing(): Unit = {
    System.setProperty("sun.java2d.opengl", "true")
    // setupGtkLookAndFeel()
  }

  def setupGtkLookAndFeel(): Unit = {
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")
    } catch {
      case _: Exception =>
        val cross = UIManager.getCrossPlatformLookAndFeelClassName
        UIManager.setLookAndFeel(cross)
    }
  }
}
