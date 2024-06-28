package doctus.core

import doctus.core.comp._

case class FullName(first: String, last: String)

case class DoctusControllerComponent(
    pointable01: DoctusPointable,
    pointable02: DoctusPointable,
    upKey04: DoctusKey,
    activatable01: DoctusActivatable,
    select03: DoctusSelect[FullName],
    activatable03: DoctusActivatable,
    info: DoctusText
) {

  var count = 0

  pointable01.onStart(p => {
    info.text = s"$count - You started 'pointable 01' at $p"
    count += 1
  })

  pointable01.onStop(p => {
    info.text = s"$count - You stopped 'stoppable 01' at $p"
    count += 1
  })

  pointable02.onStart(p => {
    info.text = s"$count - You started 'pointable 02' at $p"
    count += 1
  })

  pointable02.onStop(p => {
    info.text = s"$count - You stopped 'pointable 02' at $p"
    count += 1
  })

  upKey04.onKeyPressed(key => {
    info.text = s"$count - You pressed the $key key"
    count += 1
  })

  upKey04.onKeyReleased(key => {
    info.text = s"$count - You released the $key key"
    count += 1
  })

  activatable01.onDeactivated(() => {
    info.text = s"$count - You clicked 'clickable 01'"
    count += 1
  })

  select03.setItems(
    List(
      FullName("Kurt", "Wallander"),
      FullName("Linda", "Wallander"),
      FullName("Baipa", "Liepa")
    )
  )

  activatable03.onDeactivated(() => {
    info.text = s"$count - select 03 = ${select03.selectedItem}"
    count += 1
  })
}
