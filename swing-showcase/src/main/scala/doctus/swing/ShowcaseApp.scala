package doctus.swing

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout

import doctus.showcase.AnimatedCtrl
import doctus.showcase.CanvasCtrl
import doctus.showcase.ComponentCtrl
import doctus.showcase.DraggableCtrl
import doctus.showcase.FullName
import doctus.showcase.PointableCtrl
import doctus.showcase.SchedulerStopCtrl
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField
import javax.swing.border.EmptyBorder

object ComponentApp extends App {
  
  // Must be imported in order to be able to use 'requestFocusForDoctusActivatableSwingKey'
  import DoctusActivatableSwingKey.Implicit

  // Create the components
  val textField = new JTextField()
  textField.setEditable(false)

  val label01 = new JLabel()
  label01.setText("Activatable 01")
  label01.setBorder(new EmptyBorder(10, 10, 10, 10))

  val panel02 = new JPanel()
  panel02.setBackground(java.awt.Color.YELLOW)
  panel02.setPreferredSize(new Dimension(100, 100))

  val button01 = new JButton("button 01")

  val comboBox03 = new JComboBox[FullName]()
  val button03 = new JButton("check selection")

  // Layout the components
  val main = new JPanel()
  main.setLayout(new FlowLayout())
  main.add(label01)
  main.add(panel02)
  main.add(button01)
  main.add(comboBox03)
  main.add(button03)
  main.add(new JTextArea("Press one of the arrow keys for testing the key activatable\n" + 
  "Click on the background panel if another component has currently the focus.") {
    override def isEditable() = false;
  })

  val cont = new JPanel()
  //cont.setFocusable(true);
  cont.setLayout(new BorderLayout())
  cont.add(main, BorderLayout.CENTER)
  cont.add(textField, BorderLayout.NORTH)

  // Wrap the components
  val infoText: DoctusTextSwing = DoctusTextSwing(textField)
  val pointable01 = DoctusPointableSwing(label01)
  val pointable02 = DoctusPointableSwing(panel02)
  val upKey04 = DoctusActivatableKeySwing(cont)
  val clickable01 = DoctusActivatableSwing(button01)
  val select03 = DoctusSelectSwing[FullName](comboBox03, (fn) => "[%s - %s]" format(fn.first, fn.last))
  val clickable03 = DoctusActivatableSwing(button03)

  // Start the controller
  ComponentCtrl(pointable01, pointable02, upKey04, clickable01, select03, clickable03, infoText)

  // Open the main frame
  val top = new JFrame()
  top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  top.setTitle("Doctus Component Showcase")
  val url = getClass().getClassLoader().getResource("logo.png")
  if (url != null) top.setIconImage(new ImageIcon(url).getImage)
  top.setContentPane(cont)
  // Calling makes only sense after the component was added to the JFrame
  cont.requestFocusForDoctusActivatableSwingKey()
  top.setSize(new Dimension(600, 500))
  top.setVisible(true)

}

object CanvasApp extends App {

  val p = DoctusComponentFactory.component()
  val canvas = DoctusCanvasSwing(p)
  val logo = DoctusImageSwing("logo.png")
  
  CanvasCtrl(canvas, logo)

  val top = new JFrame()
  top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  top.setTitle("Canvas Showcase")
  val url = getClass().getClassLoader().getResource("logo.png")
  if (url != null) top.setIconImage(new ImageIcon(url).getImage)
  
  top.getContentPane.add(p, BorderLayout.CENTER)
  top.setSize(new Dimension(900, 700))
  top.setVisible(true)
}

object PointableApp extends App {

  val p = DoctusComponentFactory.component()

  PointableCtrl(DoctusPointableSwing(p), DoctusCanvasSwing(p))

  val top = new JFrame()
  top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  top.setTitle("Activatable Showcase")
  
  top.getContentPane.add(p, BorderLayout.CENTER)
  top.setSize(new Dimension(900, 600))
  top.setVisible(true)
}

object DraggableApp extends App {

  val p = DoctusComponentFactory.component()

  DraggableCtrl(DoctusDraggableSwing(p), DoctusCanvasSwing(p))

  val top = new JFrame()
  top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  top.setTitle("Activatable Showcase")
  
  top.getContentPane.add(p, BorderLayout.CENTER)
  top.setSize(new Dimension(900, 600))
  top.setVisible(true)
}

object AnimatedApp extends App {

  // Create the component
  val panel = DoctusComponentFactory.component()

  // Wrap the components
  val sched = DoctusSchedulerSwing
  val canvas = DoctusCanvasSwing(panel)
  val img = DoctusImageSwing("logo.png")

  // Start the controller
  AnimatedCtrl(canvas, sched, img)

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

object SchedulerStopApp extends App {

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
  SchedulerStopCtrl(sched, start, stop, canv)

  // Open the application
  val top = new JFrame()
  top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  val url = getClass().getClassLoader().getResource("logo.png")
  if (url != null) top.setIconImage(new ImageIcon(url).getImage)
  top.setTitle("Scheduler Start Stop")
  top.setContentPane(contPanel)
  top.setSize(new Dimension(700, 500))
  top.setVisible(true)
}

