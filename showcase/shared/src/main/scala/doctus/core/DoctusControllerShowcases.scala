package doctus.core

import doctus.core.comp.DoctusSelect

case class Showcase(name: String, desc: String)

case class DoctusControllerShowcases(canvas: DoctusSelect[Showcase]) {

  canvas.addItem(Showcase("Animated", "Animation of the doctus logo"))
  canvas.addItem(
    Showcase(
      "Stillimage",
      "An image that is repainted if the size of the window changes"
    )
  )

}
