package doctus.swing

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing._
import javax.swing.border.EmptyBorder
import doctus.showcase._

object CanvasApp extends App {

  val p = DoctusComponentFactory.component()
  val canvas = DoctusCanvasSwing(p)
  val logo = DoctusImageSwing("logo.png")
  
  DoctusControllerCanvas(canvas, logo)

  val top = new JFrame()
  top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  top.setTitle("Canvas Showcase")
  val url = getClass().getClassLoader().getResource("logo.png")
  if (url != null) top.setIconImage(new ImageIcon(url).getImage)
  
  top.getContentPane.add(p, BorderLayout.CENTER)
  top.setSize(new Dimension(900, 700))
  top.setVisible(true)
}

