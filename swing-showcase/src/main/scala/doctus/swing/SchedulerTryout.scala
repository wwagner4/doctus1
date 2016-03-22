package doctus.swing

object SchedulerTryout extends App {

  val sched = DoctusSchedulerSwing
  
  def hallo(): Unit = print(".")
  def schoene(): Unit = println("schöne")
  def frau(): Unit = println("frau")
  
  sched.start(hallo, 10)
  sched.start(schoene, 400)
  sched.start(frau, 150)
  
}