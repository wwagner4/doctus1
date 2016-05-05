package doctus.jvm

import java.io.File
import javafx.application.Application
import javafx.embed.swing.SwingFXUtils
import javafx.scene._
import javafx.scene.canvas.Canvas
import javafx.stage.Stage
import javax.imageio.ImageIO

import doctus.core._
import doctus.core.template.DoctusTemplateController

object ShowcaseTemplateJvmFileStillImage extends App {

  Application.launch(classOf[FxApp], args: _*)

  class FxApp extends Application {

    val width = 3000
    val height = 2000

    val homeDir = new File(System.getProperty("user.home"))
    val pngFile = new File(homeDir, "ShowcaseTemplateJvmFileStillImage_%d_%d.png" format(width, height))

    override def start(stage1: Stage) {

      val canvas = new Canvas(width, height)

      val doctCanvas = DoctusTemplateCanvasFx(canvas)
      val doctSched = DoctusSchedulerJvm

      val grp = new Group()
      grp.getChildren.add(canvas)

      // Start the controller
      val templ = DoctusTemplateStillImage(doctCanvas)
      DoctusTemplateController(templ, doctSched, doctCanvas)
      doctCanvas.repaint()

      val wi = grp.snapshot(new SnapshotParameters(), null)
      require(wi != null)
      val bi = SwingFXUtils.fromFXImage(wi, null)
      require(bi != null)
      ImageIO.write(bi, "png", pngFile)

      println("wrote image to '%s'" format pngFile)
      System.exit(0)

    }
  }

}

