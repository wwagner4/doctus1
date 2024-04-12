package doctus.jvm

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import doctus.core.DoctusScheduler

case object DoctusSchedulerJvm extends DoctusScheduler {

  def start(
      f: () => Unit,
      duration: Int,
      initialDelay: Int = 0
  ): DoctusScheduler.Stopper = {
    require(duration > 0, "Duration must be greater than zero. " + duration)
    require(
      initialDelay >= 0,
      "Initial delay must be greater equal to zero. " + duration
    )

    val scheduler: ScheduledExecutorService =
      Executors.newScheduledThreadPool(1)
    val runnable = new Runnable {
      override def run(): Unit = f()
    }
    val future = scheduler.scheduleAtFixedRate(
      runnable,
      initialDelay,
      duration,
      TimeUnit.MILLISECONDS
    )

    new DoctusScheduler.Stopper {
      // Stops the execution of a Scheduler
      override def stop(): Unit = future.cancel(true)
    }
  }

}
