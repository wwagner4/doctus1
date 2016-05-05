package doctus.jvm

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import doctus.core._
import doctus.core.template.DoctusTemplateController
import doctus.jvm.awt.{DoctusBufferedImage, DoctusCanvasSwingBufferedImage}

object ShowcaseTemplateJvmAwtFileStillImage extends App {

  val width = 10000
  val height = 6000

  val homeDir = new File(System.getProperty("user.home"))
  val pngFile = new File(homeDir, "ShowcaseTemplateJvmAwtFileStillImage_%d_%d.png" format (width, height))

  val bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

  val dbi = DoctusBufferedImage(bi)

  val doctCanvas = DoctusCanvasSwingBufferedImage(dbi)
  val doctSched = DoctusSchedulerJvm

  // Start the controller
  val templ = DoctusTemplateStillImage(doctCanvas)
  DoctusTemplateController(templ, doctSched, doctCanvas)
  doctCanvas.repaint()

  ImageIO.write(bi, "png", pngFile)

  println("wrote image to '%s'" format pngFile)
  System.exit(0)


}

