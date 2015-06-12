package doctus.tryout

import java.util.concurrent.{TimeUnit, ScheduledExecutorService, Executors}

/**
 * Tryout Scheduler
 */
object SchedulerTryout extends App {

  println("Started Scheduler")
  val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

  val a = new Runnable {
    override def run(): Unit = println("-A-")
  }
  val b = new Runnable {
    override def run(): Unit = println("-B-")
  }
  scheduler.scheduleAtFixedRate(a, 0, 1000, TimeUnit.MILLISECONDS)
  scheduler.scheduleAtFixedRate(b, 0, 888, TimeUnit.MILLISECONDS)

}
