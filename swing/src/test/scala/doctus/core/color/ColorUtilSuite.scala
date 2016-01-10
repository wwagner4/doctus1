package doctus.core.color

import utest._


object ColorUtilSuite extends TestSuite {

  def tests = TestSuite {

    "Color Util" - {
      "rgb to hsv" - {
        * - {
          val r = 255
          val g = 0
          val b = 102
          val is = DoctusColorUtil.rgb2hsv(r, g, b)
          val should = (-24, 100, 100)
          assert(is == should)
        }
      }
      "hsv to rgb" - {
        * - {
          val r = -24
          val g = 100
          val b = 100
          val is = DoctusColorUtil.hsv2rgb(r, g, b)
          val should = (255, 0, 102)
          assert(is == should)
        }
      }
    }
  }
}