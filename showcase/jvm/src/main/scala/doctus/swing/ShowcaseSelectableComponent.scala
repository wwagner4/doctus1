package doctus.swing

import doctus.Rocket
import doctus.core._
import doctus.core.comp.{DoctusSelect1, SelectItemDescription}

import java.awt.{BorderLayout, Dimension, FlowLayout}
import javax.swing._

object ShowcaseSelectableComponent extends App {

  val buttonA = new JButton("A")
  val buttonB = new JButton("B")
  val buttonOpen = new JButton("Open")

  def buttonsPanel(): JPanel = {
    val cont = new JPanel()
    cont.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0))
    cont.add(buttonA)
    cont.add(buttonB)
    cont.add(buttonOpen)
    cont
  }

  val list = new JList[String]()

  private val selectList: DoctusSelect1[Rocket] =
    new DoctusSelectSwingList[Rocket](list)

  val comboBox = new JComboBox[String]()

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

case class DoctusControllerSelectables(
    activatableA: DoctusActivatable,
    activatableB: DoctusActivatable,
    activatableOpen: DoctusActivatable,
    selectList: DoctusSelect1[Rocket],
    selectComboBox: DoctusSelect1[Rocket]
) {

  private val itemDescription = SelectItemDescription(
    columnWidths = List(20, 30, 20),
    extractColumnString = (rocket: Rocket, columnIndex: Int) => {
      columnIndex match {
        case 0 => rocket.name
        case 1 => rocket.height
        case 2 => rocket.launchDate
      }
    }
  )

  activatableA.onActivated { () =>
    selectList.setItems(rocketsA, itemDescription)
    selectComboBox.setItems(rocketsA, itemDescription)
  }
  activatableB.onActivated { () =>
    selectList.setItems(rocketsB, itemDescription)
    selectComboBox.setItems(rocketsB, itemDescription)
  }
  activatableOpen.onActivated { () =>
    println(
      s"-- selected row is list:${selectList.selectedItem} comboBox:${selectComboBox.selectedItem}"
    )
  }

  private val rocketsA = List(
    Rocket(
      name = "Delta Rocket Family",
      height = "Varies (e.g., Delta IV Heavy: 236 ft (72 m))",
      launchDate = "1960-present"
    ),
    Rocket(
      name = "Saturn Rocket Family",
      height = "Varies (e.g., Saturn V: 363 ft (110 m))",
      launchDate = "1960-1973"
    ),
    Rocket(
      name = "Space Shuttle",
      height = "184 ft (56 m)",
      launchDate = "1981-2011"
    )
  )

  private val rocketsB = List(
    Rocket(
      name = "Atlas Rocket Family",
      height = "Varies (e.g., Atlas V: 191 ft (58 m))",
      launchDate = "1957-present"
    ),
    Rocket(
      name = "Titan Rocket Family",
      height = "Varies (e.g., Titan IV: 153 ft (47 m))",
      launchDate = "1959-2005"
    ),
    Rocket(
      name = "Titan Rocket Family",
      height = "Varies (e.g., Titan IV: 153 ft (47 m))",
      launchDate = "1959-2005"
    ),
    Rocket(
      name = "Scout Rocket",
      height = "75 ft (23 m)",
      launchDate = "1961-1971"
    )
  )
}
