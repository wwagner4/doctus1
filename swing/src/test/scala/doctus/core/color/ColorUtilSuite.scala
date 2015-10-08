package doctus.core.color

import utest._


object ColorUtilSuite extends TestSuite {

  def tests = TestSuite {

    /*
v:[100]
v:[DoctusColorRgb(255,0,102)]
set base color:DoctusColorRgb(255,0,102)
color holder 100 DoctusColorRgb(255,0,102) -> DoctusColorRgb(255,-101,0)
v:[DoctusColorRgb(255,0,170)]
set base color:DoctusColorRgb(255,0,170)
color holder 100 DoctusColorRgb(255,0,170) -> DoctusColorRgb(255,-169,0)
v:[DoctusColorRgb(255,0,238)]
set base color:DoctusColorRgb(255,0,238)
color holder 100 DoctusColorRgb(255,0,238) -> DoctusColorRgb(255,-238,0)
v:[DoctusColorRgb(203,0,255)]
set base color:DoctusColorRgb(203,0,255)
color holder 100 DoctusColorRgb(203,0,255) -> DoctusColorRgb(199,0,255)


     */

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