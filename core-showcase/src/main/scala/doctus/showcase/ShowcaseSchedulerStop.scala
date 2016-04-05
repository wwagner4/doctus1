package doctus.showcase

import doctus.core._
import doctus.core.comp._
import doctus.core.color._
import doctus.core.util._
import doctus.core.text._

/**
 * Demonstrates how a scheduler can be started and stopped
 */
case class DoctusControllerSchedulerStop(sched: DoctusScheduler, start: DoctusActivatable, stop: DoctusActivatable, canv: DoctusCanvas) {

  var cnt = 0

  canv.onRepaint(g => {
    val r = 111
    val fs = 50
    val x = canv.width / 2.0
    val y = canv.height / 2.0
    if (stopper.isEmpty) {
      g.fill(DoctusColorOrange, 255)
    } else {
      g.fill(DoctusColorYellow, 255)
    }
    g.ellipse(DoctusPoint(x, y), r, r)
    g.fill(DoctusColorBlack, 255)
    g.textSize(fs)
    g.textFont(DoctusFontMonospace)
    g.text(cnt.toString, x - fs / 2.0, y + fs / 4.0, 0)
  })

  sched.start(canv.repaint, 10)

  var stopper: List[DoctusScheduler.Stopper] = List.empty[DoctusScheduler.Stopper]

  start.onDeactivated(() => {
    stopper ::= sched.start(() => {
      cnt += 1
    }, 500, 2000)
  })

  stop.onDeactivated(() => {
    stopper.foreach(_.stop())
    stopper = List.empty[DoctusScheduler.Stopper]
  })

}
