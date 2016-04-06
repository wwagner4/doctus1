package doctus.swing

import java.awt.BorderLayout
import java.awt.Dimension

import doctus.core.DoctusTemplateKeys
import doctus.core.template.DoctusTemplateController
import javax.swing.ImageIcon
import javax.swing.JFrame

object ShowcaseTemplateSwingKeys extends App {
  
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

  val templ = DoctusTemplateKeys(canvas)
  DoctusTemplateController(templ, sched, canvas)


  
}