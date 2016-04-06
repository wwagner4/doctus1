package doctus.swing

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing._
import javax.swing.border.EmptyBorder
import doctus._

object ShowcaseSwingComponent extends App {
  
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
  val upKey04 = DoctusKeySwing(cont)
  val clickable01 = DoctusActivatableSwing(button01)
  val select03 = DoctusSelectSwing[FullName](comboBox03, (fn) => "[%s - %s]" format(fn.first, fn.last))
  val clickable03 = DoctusActivatableSwing(button03)

  // Start the controller
  DoctusControllerComponent(pointable01, pointable02, upKey04, clickable01, select03, clickable03, infoText)

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

