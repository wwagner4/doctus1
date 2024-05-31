package doctus.jvm

import doctus.core.{DoctusScheduler, DoctusSchedulerStopper}

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

case object DoctusSchedulerJvm extends DoctusScheduler {

  def start(
      f: () => Unit,
      duration: Int,
      initialDelay: Int = 0
  ): DoctusSchedulerStopper = {
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

    new DoctusSchedulerStopper {
      // Stops the execution of a Scheduler
      override def stop(): Unit = future.cancel(true)
    }
  }

}
