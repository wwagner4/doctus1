package doctus.jvm

import doctus.core._

object ShowcaseJvmAnimated extends App {

  val fxCanvas = ???
  
  val sched = DoctusSchedulerJvm
  val canvas = DoctusCanvasFx(fxCanvas)
  val img = DoctusImageFx("logo.png")

  // Start the controller
  DoctusControllerAnimated(canvas, sched, img)

}

