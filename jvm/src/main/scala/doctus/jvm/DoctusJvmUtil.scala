package doctus.jvm

import javafx.event.EventHandler
import javafx.event.Event

object DoctusJvmUtil {

  def handler[T <: Event](h: (T => Unit)): EventHandler[T] =
    new EventHandler[T] {
      override def handle(event: T): Unit = h(event)
    }

}