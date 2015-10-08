package doctus.core.color

import utest._


object ColorUtilSuite extends TestSuite {

  def tests = TestSuite {

    "Color Util" - {
      "rgb to hsv" - {
        val r = 0
        val g = 0
        val b = 0
        val is = DoctusColorUtil.rgb2hsv(r, g, b)
        val should = (2, 3, 4)
        assert(is == should)
        assert(1 > 2)
      }
    }
  }
}