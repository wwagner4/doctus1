package doctus.tryout

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.event.KeyEvent
import doctus.core.DoctusActivatable
import doctus.swing.DoctusActivatableSwingKey
import doctus.swing.DoctusComponentFactory
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import doctus.swing.DoctusActivatableSwing

object SwingKeyActivatableTryout extends App {

  val greenPanel = DoctusComponentFactory.component()
  greenPanel.setBackground(Color.GREEN)

  val button = new JButton("click me")

  val contentPanel = new JPanel()
  contentPanel.setLayout(new BorderLayout())
  contentPanel.add(greenPanel, BorderLayout.CENTER)
  contentPanel.add(button, BorderLayout.SOUTH)

  val frame = new JFrame()
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  frame.setTitle("DoctusActivatableSwingKey")
  frame.setSize(new Dimension(200, 200))
  frame.setContentPane(contentPanel)
  frame.setVisible(true)

  val activatable = DoctusActivatableSwingKey(greenPanel, KeyEvent.VK_UP)
  val clickable = DoctusActivatableSwing(button)
  SwingKeyActivatableTryoutController(clickable, activatable)

}

case class SwingKeyActivatableTryoutController(c: DoctusActivatable, a: DoctusActivatable) {

  var cnt = 0

  a.onActivated { () => println("Pressed UP key 1 " + cnt); cnt += 1}
  a.onDeactivated { () => println("Released UP key 1 " + cnt); cnt += 1}

  c.onDeactivated(() => {
    println("Clicked")
    cnt = 0
    a.onActivated { () => println("Pressed UP key 2 " + cnt); cnt += 1}
    a.onDeactivated { () => println("Released UP key 2 " + cnt); cnt += 1}
  })

}

