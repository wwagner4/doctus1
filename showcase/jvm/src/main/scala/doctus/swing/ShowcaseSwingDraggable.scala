package doctus.swing

import java.awt.BorderLayout
import java.awt.Dimension
import doctus.core.DoctusControllerDraggable

import javax.swing.{JFrame, WindowConstants}

object DraggableApp extends App {

  val p = DoctusComponentFactory.component()

  DoctusControllerDraggable(DoctusDraggableSwing(p), DoctusCanvasSwing(p))

  val top = new JFrame()
  top.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  top.setTitle("Draggable Showcase")

  top.getContentPane.add(p, BorderLayout.CENTER)
  top.setSize(new Dimension(900, 600))
  top.setVisible(true)
}
