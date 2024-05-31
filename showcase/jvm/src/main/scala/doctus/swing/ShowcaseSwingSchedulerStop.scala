package doctus.swing

import doctus.core._

import java.awt.{BorderLayout, Dimension, FlowLayout}
import javax.swing._

object ShowcaseSwingSchedulerStop extends App {

  // Create Components
  val panel = DoctusComponentFactory.component()
  val startButton = new JButton("start")
  val stopButton = new JButton("stop")

  // Create Layout
  val buttonsPanel = new JPanel()
  buttonsPanel.setLayout(new FlowLayout())
  buttonsPanel.add(startButton)
  buttonsPanel.add(stopButton)

  val contPanel = new JPanel()
  contPanel.setLayout(new BorderLayout())
  contPanel.add(panel, BorderLayout.CENTER)
  contPanel.add(buttonsPanel, BorderLayout.NORTH)

  // Wrap Components
  val sched = DoctusSchedulerSwing
  val start = DoctusActivatableSwing(startButton)
  val stop = DoctusActivatableSwing(stopButton)
  val canv = DoctusCanvasSwing(panel)

  // Start the controller
  DoctusControllerSchedulerStop(sched, start, stop, canv)

  // Open the application
  val top = new JFrame()
  top.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val url = getClass().getClassLoader().getResource("logo.png")
  if (url != null) top.setIconImage(new ImageIcon(url).getImage)
  top.setTitle("Scheduler Start Stop")
  top.setContentPane(contPanel)
  top.setSize(new Dimension(700, 500))
  top.setVisible(true)
}
