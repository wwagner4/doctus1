package doctus.swing

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing._
import javax.swing.border.EmptyBorder
import doctus._

object ShowcaseSwingAnimated extends App {

  // Create the component
  val panel = DoctusComponentFactory.component(doubleBuffering = false)

  // Wrap the components
  val sched = DoctusSchedulerSwing
  val canvas = DoctusCanvasSwing(panel)
  val img = DoctusImageSwing("logo.png")

  // Start the controller
  DoctusControllerAnimated(canvas, sched, img)

  // Open the main frame
  val top = new JFrame()
  top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  val url = getClass().getClassLoader().getResource("logo.png")
  if (url != null) top.setIconImage(new ImageIcon(url).getImage)
  top.setTitle("Animated Showcase")
  top.getContentPane().add(panel, BorderLayout.CENTER)
  top.setSize(new Dimension(700, 500))
  top.setVisible(true)
}

