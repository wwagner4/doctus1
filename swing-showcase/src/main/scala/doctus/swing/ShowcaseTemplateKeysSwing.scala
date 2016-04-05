package doctus.swing

import javax.swing.JFrame
import java.awt.Dimension
import doctus.showcase.DoctusTemplateKeys
import doctus.core.template.DoctusTemplateController
import javax.swing.ImageIcon
import java.awt.BorderLayout

object ShowcaseTemplateKeysSwing extends App {
  
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