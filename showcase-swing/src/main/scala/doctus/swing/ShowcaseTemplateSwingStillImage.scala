package doctus.swing

import doctus.core.DoctusTemplateStillImage
import javax.swing.JFrame
import javax.swing.ImageIcon
import java.awt.BorderLayout
import java.awt.Dimension
import doctus.core.template._

object ShowcaseTemplateSwingStillImage extends App {

  val p = DoctusComponentFactory.component()
  val canvas = DoctusTemplateCanvasSwing(p)
  val sched = DoctusSchedulerSwing
  val logo = DoctusImageSwing("logo.png")

  val top = new JFrame()
  top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  top.setTitle("Canvas Showcase")
  val url = getClass().getClassLoader().getResource("logo.png")
  if (url != null) top.setIconImage(new ImageIcon(url).getImage)

  top.getContentPane.add(p, BorderLayout.CENTER)
  top.setSize(new Dimension(900, 700))
  top.setVisible(true)

  val templ = DoctusTemplateStillImage(canvas)
  DoctusTemplateController(templ, sched, canvas)


}