package doctus.scalajs

import utest._

object ComponentsTest extends TestSuite {

  case class RgbTest(r: Int, g: Int, b: Int, rgb: String)


  def tests = TestSuite {

    'rgb_test_01{
      val v = RgbTest(195, 198, 300, "#c3c6ff")
      assert(DoctusScalajsUtil.rgbString(v.r, v.g, v.b) == v.rgb)
    }

    'rgb_test_02{
      val v = RgbTest(24, 85, 100, "#185564")
      assert(DoctusScalajsUtil.rgbString(v.r, v.g, v.b) == v.rgb)
    }

    'rgb_test_03{
      val v = RgbTest(24, 85, 100, "#185564")
      assert(DoctusScalajsUtil.rgbString(v.r, v.g, v.b) == v.rgb)
    }

    'rgb_test_04{
      val v = RgbTest(0, 0, 0, "#000000")
      assert(DoctusScalajsUtil.rgbString(v.r, v.g, v.b) == v.rgb)
    }

    'rgb_test_05{
      val v = RgbTest(255, 256, -22, "#ffff00")
      assert(DoctusScalajsUtil.rgbString(v.r, v.g, v.b) == v.rgb)
    }

    'rgb_test_06{
      val v =  RgbTest(123, 189, 137, "#7bbd89")
      assert(DoctusScalajsUtil.rgbString(v.r, v.g, v.b) == v.rgb)
    }

    'rgb_test_07{
      val v = RgbTest(195, 198, 300, "#c3c6ff")
      assert(DoctusScalajsUtil.rgbString(v.r, v.g, v.b) == v.rgb)
    }

    'Terminal_empty{
      val t = new Terminal(5)
      val c = t.content
      assert(c == Seq("&nbsp;", "&nbsp;", "&nbsp;", "&nbsp;", "&nbsp;"))
    }
    'Terminal_one_line{
      val t = new Terminal(5)
      t.addLine("hallo")
      assert(t.content == Seq("&nbsp;", "&nbsp;", "&nbsp;", "&nbsp;", "hallo"))
    }
    'Terminal_2_lines{
      val t = new Terminal(5)
      t.addLine("hallo")
      t.addLine("was")
      assert(t.content == Seq("&nbsp;", "&nbsp;", "&nbsp;", "hallo", "was"))
    }
    'Terminal_6_lines{
      val t = new Terminal(5)
      t.addLine("hallo")
      t.addLine("was")
      t.addLine("geht")
      t.addLine("ab")
      t.addLine("oida")
      t.addLine("....")
      assert(t.content == Seq("was", "geht", "ab", "oida", "...."))
    }
    'Terminal_5_lines{
      val t = new Terminal(5)
      t.addLine("hallo")
      t.addLine("was")
      t.addLine("geht")
      t.addLine("ab")
      t.addLine("oida")
      assert(t.content == Seq("hallo", "was", "geht", "ab", "oida"))
    }
  }
}