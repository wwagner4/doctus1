package doctus.core.util

import utest._

object CoreSuite extends TestSuite {

  def tests: Tests = Tests {

    test("crop string") - {
      test - {
        val expected = "12345678"
        val in = "123456789012"
        val len = 8
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "123456789"
        val in = "123456789012"
        val len = 9
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "1234567..."
        val in = "123456789012"
        val len = 10
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "12345678..."
        val in = "123456789012"
        val len = 11
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "123456789012"
        val in = "123456789012"
        val len = 12
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = " 123456789012"
        val in = "123456789012"
        val len = 13
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "  123456789012"
        val in = "123456789012"
        val len = 14
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "   123456789012"
        val in = "123456789012"
        val len = 15
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "    123456789012"
        val in = "123456789012"
        val len = 16
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "     "
        val in = ""
        val len = 5
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "     "
        val in = " "
        val len = 5
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "     "
        val in = "  "
        val len = 5
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "     "
        val in = "   "
        val len = 5
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "     "
        val in = "            "
        val len = 5
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "  abc"
        val in = "abc"
        val len = 5
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = " abc"
        val in = "abc"
        val len = 4
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "abc"
        val in = "abc"
        val len = 3
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "ab"
        val in = "abc"
        val len = 2
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = "a"
        val in = "abc"
        val len = 1
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
      test - {
        val expected = ""
        val in = "abc"
        val len = 0
        val out = cropString(in, len)
        assert(expected.equals(out))
      }
    }

    test("vector equality") {
      test - {
        val v1 = DoctusVector(0, 0)
        val v2 = DoctusVector(0, 0)
        assert(v1.equals(v2))
      }
      test - {
        val v1 = DoctusVector(0, 0)
        val v2 = DoctusVector(0, 0)
        assert(v1 == v2)
      }
      test - {
        val v1 = DoctusVector(1.0, 1.0)
        val v2 = DoctusVector(1.0, 1.0)
        assert(v1 == v2)
      }
      test - {
        val v1 = DoctusVector(1.0, 1.0)
        val v2 = DoctusVector(1.0, 1.1)
        assert(v1 != v2)
      }

    }

    test("point equality") {
      test - {
        val p1 = DoctusPoint(0, 0)
        val p2 = DoctusPoint(0, 0)
        assert(p1.equals(p2))
      }
      
      test - {
        val p1 = DoctusPoint(0, 0)
        val p2 = DoctusPoint(0, 0)
        assert(p1 == p2)
      }
      
      test - {
        val p1 = DoctusPoint(1.0, 1.0)
        val p2 = DoctusPoint(1.0, 1.0)
        assert(p1 == p2)
      }
      
      test - {
        val p1 = DoctusPoint(1.0, 1.0)
        val p2 = DoctusPoint(1.0, 1.1)
        assert(p1 != p2)
      }

    }

    Symbol("adding_two_vectors"){
      val v1 = DoctusVector(10, 10)
      val v2 = DoctusVector(3, 4)
      val v3: DoctusVector = v1 + v2
      assert(v3.x == 13.0)
      assert(v3.y == 14.0)
    }
    Symbol("subtracting_two_vectors"){
      val v1 = DoctusVector(10, 10)
      val v2 = DoctusVector(3, 4)
      val v3: DoctusVector = v1 - v2
      assert(v3.x == 7.0)
      assert(v3.y == 6.0)
    }
    Symbol("subtracting_two_vectors_the_other_way_around"){
      val v1 = DoctusVector(10, 10)
      val v2 = DoctusVector(3, 4)
      val v3: DoctusVector = v2 - v1
      assert(v3.x == -7.0)
      assert(v3.y == -6.0)
    }
    Symbol("adding_a_vector_to_a_point"){
      val v = DoctusVector(10, 10)
      val p = DoctusPoint(3, 4)
      val p1: DoctusPoint = p + v
      assert(p1.x == 13.0)
      assert(p1.y == 14.0)
    }
    Symbol("subtracting_a_vector_from_a_point"){
      val v = DoctusVector(10, 10)
      val p = DoctusPoint(3, 4)
      val p1: DoctusPoint = p - v
      assert(p1.x == -7.0)
      assert(p1.y == -6.0)
    }
    Symbol("subtracting_a_point_from_a_point"){
      val p1 = DoctusPoint(10, 10)
      val p2 = DoctusPoint(3, 4)
      val v: DoctusVector = p1 - p2
      assert(v.x == 7.0)
      assert(v.y == 6.0)
    }
    Symbol("subtracting_a_point_from_a_point_the_other_way_around"){
      val p1 = DoctusPoint(10, 10)
      val p2 = DoctusPoint(3, 4)
      val v: DoctusVector = p2 - p1
      assert(v.x == -7.0)
      assert(v.y == -6.0)
    }
    Symbol("multiply_a_vector_with_a_pos_scalar"){
      val v = DoctusVector(10, 11)
      val v1: DoctusVector = v * 2
      assert(v1.x == 20.0)
      assert(v1.y == 22.0)
    }
    Symbol("multiply_a_vector_with_a_neg_scalar"){
      val v = DoctusVector(10, 11)
      val v1: DoctusVector = v * (-2)
      assert(v1.x == -20.0)
      assert(v1.y == -22.0)
    }
    Symbol("norm_of_a_vector_01"){
      val v = DoctusVector(4, 3)
      assert(v.norm == 5.0)
    }
    Symbol("norm_of_a_vector_02"){
      val v = DoctusVector(40, 0)
      assert(v.norm == 40.0)
    }
    Symbol("norm_of_a_vector_03"){
      val v = DoctusVector(-40, 0)
      assert(v.norm == 40.0)
    }
    Symbol("norm_of_a_vector_03"){
      val v = DoctusVector(0, -40)
      assert(v.norm == 40.0)
    }
    Symbol("to_unit_01"){
      val v = DoctusVector(0, -40)
      val u = v.toUnit
      assert(u.x == 0.0)
      assert(u.y == -1.0)
    }
    Symbol("to_unit_02"){
      val v = DoctusVector(0, 40)
      val u = v.toUnit
      assert(u.x == 0.0)
      assert(u.y == 1.0)
    }
    Symbol("to_unit_02"){
      val v = DoctusVector(0.2, 0)
      val u = v.toUnit
      assert(u.x == 1.0)
      assert(u.y == 0.0)
    }
    Symbol("rot_01"){
      val v = DoctusVector(10, 0)
      val u = v.rot(math.Pi / 2.0)
      val x = round(u.x)
      val y = round(u.y)
      assert(x == 0.0)
      assert(y == 10.0)
    }
    Symbol("rot_02"){
      val v = DoctusVector(10, 0)
      val u = v.rot(-math.Pi / 2.0)
      val x = round(u.x)
      val y = round(u.y)
      assert(x == 0.0)
      assert(y == -10.0)
    }
    Symbol("rot_03"){
      val v = DoctusVector(10, 0)
      val u = v.rot(math.Pi)
      val x = round(u.x)
      val y = round(u.y)
      assert(x == -10.0)
      assert(y == 0.0)
    }
    Symbol("multiply_scalar_01"){
      val v = DoctusVector(10, 5)
      val u = v * 10
      val x = round(u.x)
      val y = round(u.y)
      assert(x == 100)
      assert(y == 50)
    }
    Symbol("multiply_scalar_02"){
      val v = DoctusVector(10, 5)
      val u = v * -10
      val x = round(u.x)
      val y = round(u.y)
      assert(x == -100)
      assert(y == -50)
    }
  }

  def round(v: Double): Double = {
    val fac = 10000
    val x1 = v * fac
    val x2 = math.round(x1)
    x2.toDouble / fac
  }

}


