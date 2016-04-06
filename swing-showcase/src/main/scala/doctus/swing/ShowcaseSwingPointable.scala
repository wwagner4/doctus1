package doctus.swing

import java.awt.BorderLayout
import java.awt.Dimension

import doctus.DoctusControllerPointable
import javax.swing.JFrame

object PointableApp extends App {

  val p = DoctusComponentFactory.component()

  DoctusControllerPointable(DoctusPointableSwing(p), DoctusCanvasSwing(p))

  val top = new JFrame()
  top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  top.setTitle("Pointable Showcase")
  
  top.getContentPane.add(p, BorderLayout.CENTER)
  top.setSize(new Dimension(900, 600))
  top.setVisible(true)
}

