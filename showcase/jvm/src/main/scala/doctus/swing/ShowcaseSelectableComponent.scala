package doctus.swing

import doctus.core._
import doctus.core.comp.DoctusSelect1

import java.awt.{BorderLayout, Dimension, FlowLayout}
import javax.swing._

object ShowcaseSelectableComponent extends App {

  private val buttonA = new JButton("A")
  private val buttonB = new JButton("B")
  private val buttonOpen = new JButton("Open")

  def buttonsPanel(): JPanel = {
    val cont = new JPanel()
    cont.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0))
    cont.add(buttonA)
    cont.add(buttonB)
    cont.add(buttonOpen)
    cont
  }

  private val list = new JList[String]()

  private val selectList: DoctusSelect1[Rocket] =
    new DoctusSelectSwingList[Rocket](list)

  private val comboBox = new JComboBox[String]()

  private val selectComboBox: DoctusSelect1[Rocket] =
    new DoctusSelectSwingComboBox[Rocket](comboBox)

  private val activatableA: DoctusActivatable = DoctusActivatableSwing(buttonA)
  private val activatableB: DoctusActivatable = DoctusActivatableSwing(buttonB)
  private val activatableOpen: DoctusActivatable = DoctusActivatableSwing(
    buttonOpen
  )

  DoctusControllerSelectables(
    activatableA,
    activatableB,
    activatableOpen,
    selectList,
    selectComboBox
  )

  private val listScrollPane = new JScrollPane(list)

  private val cont = new JPanel()
  cont.setLayout(new BorderLayout())
  cont.add(listScrollPane, BorderLayout.CENTER)
  cont.add(buttonsPanel(), BorderLayout.NORTH)
  cont.add(comboBox, BorderLayout.SOUTH)

  private val top = new JFrame()
  top.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  top.setTitle("Doctus Component Showcase")
  private val url = getClass.getClassLoader.getResource("logo.png")
  if (url != null) top.setIconImage(new ImageIcon(url).getImage)
  top.setContentPane(cont)
  // Calling makes only sense after the component was added to the JFrame
  top.setSize(new Dimension(600, 500))
  top.setVisible(true)

}
