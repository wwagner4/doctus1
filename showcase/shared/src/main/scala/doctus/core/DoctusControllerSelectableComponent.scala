package doctus.core

import doctus.core.comp.{DoctusSelect, SelectItemDescription}

case class Rocket(
    name: String,
    height: String,
    launchDate: String
)

case class DoctusControllerSelectables(
    activatableA: DoctusActivatable,
    activatableB: DoctusActivatable,
    activatableOpen: DoctusActivatable,
    selectList: DoctusSelect[Rocket],
    selectComboBox: DoctusSelect[Rocket]
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
