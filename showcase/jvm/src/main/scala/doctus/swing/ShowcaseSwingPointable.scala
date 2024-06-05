package doctus.swing

import doctus.core.DoctusControllerPointable

import java.awt.{BorderLayout, Dimension}
import javax.swing.{JFrame, WindowConstants}

object ShowcaseSwingPointable extends App {

  val p = DoctusSwingComponentFactory.component

  DoctusControllerPointable(DoctusPointableSwing(p), DoctusCanvasSwing(p))

  val top = new JFrame()
  top.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  top.setTitle("Pointable Showcase")

  top.getContentPane.add(p, BorderLayout.CENTER)
  top.setSize(new Dimension(900, 600))
  top.setVisible(true)
}
