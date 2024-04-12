package doctus.scalajs

import utest._
import doctus.core.util._

object EventManagerSuite extends TestSuite {

  class Collector {
    private var result = List.empty[Char]
    def a(@annotation.nowarn p: DoctusPoint): Unit = result ::= 'A'
    def b(@annotation.nowarn p: DoctusPoint): Unit = result ::= 'B'
    def c(@annotation.nowarn p: DoctusPoint): Unit = result ::= 'C'
    def resultString: String = result.reverse.mkString("")
  }

  def tests = TestSuite {

    "pointing one finger" - {

      val col = new Collector

      val m = new DoctusEventManager

      m.onStart(col.a)
      m.onStop(col.b)

      m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
      m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
      m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
      m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
      m.addEvent(TouchEnd(List.empty[DoctusIdPoint]))

      assert("AB" == col.resultString)
    }

    "dragging one finger" - {
      * - {
        val m = new DoctusEventManager

        val col = new Collector

        m.onStart(col.a)
        m.onDrag(col.b)
        m.onStop(col.c)

        m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchEnd(List.empty[DoctusIdPoint]))

        assert("ABBBC" == col.resultString)
      }

      * - {
        val m = new DoctusEventManager

        val col = new Collector

        m.onStart(col.a)
        m.onDrag(col.b)
        m.onStop(col.c)

        m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 10)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 20)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 20)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 20)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 20)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 30)))))
        m.addEvent(TouchEnd(List.empty[DoctusIdPoint]))

        assert("ABBBBBBC" == col.resultString)
      }

    }
    "intertouch with one finger" - {
      * - {
        val m = new DoctusEventManager

        val col = new Collector

        m.onStart(col.a)
        m.onDrag(col.b)
        m.onStop(col.c)

        m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))

        m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))

        m.addEvent(TouchEnd(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))

        m.addEvent(TouchEnd(List.empty[DoctusIdPoint]))

        val re = col.resultString
        assert("ABBBBBBBBBBC" == re)
      }
      * - {
        val m = new DoctusEventManager

        val col = new Collector

        m.onStart(col.a)
        m.onDrag(col.b)
        m.onStop(col.c)

        m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))

        m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))

        m.addEvent(TouchEnd(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))

        m.addEvent(TouchEnd(List.empty[DoctusIdPoint]))

        val re = col.resultString
        assert("ABBBC" == re)
      }
      * - {
        val m = new DoctusEventManager

        val col = new Collector

        m.onStart(col.a)
        m.onDrag(col.b)
        m.onStop(col.c)

        m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))

        m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))

        m.addEvent(TouchEnd(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))

        m.addEvent(TouchEnd(List.empty[DoctusIdPoint]))

        val re = col.resultString
        assert("ABC" == re)
      }
    }
    "altertouch with one finger" - {
      * - {
        val m = new DoctusEventManager

        val col = new Collector

        m.onStart(col.a)
        m.onDrag(col.b)
        m.onStop(col.c)

        m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)))))

        m.addEvent(TouchStart(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(0, DoctusPoint(0, 0)), DoctusIdPoint(1, DoctusPoint(0, 0)))))

        m.addEvent(TouchEnd(List(DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(1, DoctusPoint(0, 0)))))
        m.addEvent(TouchMove(List(DoctusIdPoint(1, DoctusPoint(0, 0)))))

        m.addEvent(TouchEnd(List.empty[DoctusIdPoint]))

        val re = col.resultString
        assert("ABBBBBBBC" == re)
      }
    }
  }
}