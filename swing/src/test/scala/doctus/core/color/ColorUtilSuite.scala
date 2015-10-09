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