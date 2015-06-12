package doctus.core.util

import utest._

object CoreSuite extends TestSuite {

  def tests = TestSuite {

    "vector equality" - {
      * - {
        val v1 = DoctusVector(0, 0)
        val v2 = DoctusVector(0, 0)
        assert(v1.equals(v2))
      }
      * - {
        val v1 = DoctusVector(0, 0)
        val v2 = DoctusVector(0, 0)
        assert(v1 == v2)
      }
      * - {
        val v1 = DoctusVector(1.0, 1.0)
        val v2 = DoctusVector(1.0, 1.0)
        assert(v1 == v2)
      }
      * - {
        val v1 = DoctusVector(1.0, 1.0)
        val v2 = DoctusVector(1.0, 1.1)
        assert(v1 != v2)
      }

    }

    "point equality" - {
      * - {
        val p1 = DoctusPoint(0, 0)
        val p2 = DoctusPoint(0, 0)
        assert(p1.equals(p2))
      }
      
      * - {
        val p1 = DoctusPoint(0, 0)
        val p2 = DoctusPoint(0, 0)
        assert(p1 == p2)
      }
      
      * - {
        val p1 = DoctusPoint(1.0, 1.0)
        val p2 = DoctusPoint(1.0, 1.0)
        assert(p1 == p2)
      }
      
      * - {
        val p1 = DoctusPoint(1.0, 1.0)
        val p2 = DoctusPoint(1.0, 1.1)
        assert(p1 != p2)
      }

    }

    'adding_two_vectors{
      val v1 = DoctusVector(10, 10)
      val v2 = DoctusVector(3, 4)
      val v3: DoctusVector = v1 + v2
      assert(v3.x == 13.0)
      assert(v3.y == 14.0)
    }
    'subtracting_two_vectors{
      val v1 = DoctusVector(10, 10)
      val v2 = DoctusVector(3, 4)
      val v3: DoctusVector = v1 - v2
      assert(v3.x == 7.0)
      assert(v3.y == 6.0)
    }
    'subtracting_two_vectors_the_other_way_around{
      val v1 = DoctusVector(10, 10)
      val v2 = DoctusVector(3, 4)
      val v3: DoctusVector = v2 - v1
      assert(v3.x == -7.0)
      assert(v3.y == -6.0)
    }
    'adding_a_vector_to_a_point{
      val v = DoctusVector(10, 10)
      val p = DoctusPoint(3, 4)
      val p1: DoctusPoint = p + v
      assert(p1.x == 13.0)
      assert(p1.y == 14.0)
    }
    'subtracting_a_vector_from_a_point{
      val v = DoctusVector(10, 10)
      val p = DoctusPoint(3, 4)
      val p1: DoctusPoint = p - v
      assert(p1.x == -7.0)
      assert(p1.y == -6.0)
    }
    'subtracting_a_point_from_a_point{
      val p1 = DoctusPoint(10, 10)
      val p2 = DoctusPoint(3, 4)
      val v: DoctusVector = p1 - p2
      assert(v.x == 7.0)
      assert(v.y == 6.0)
    }
    'subtracting_a_point_from_a_point_the_other_way_around{
      val p1 = DoctusPoint(10, 10)
      val p2 = DoctusPoint(3, 4)
      val v: DoctusVector = p2 - p1
      assert(v.x == -7.0)
      assert(v.y == -6.0)
    }
    'multiply_a_vector_with_a_pos_scalar{
      val v = DoctusVector(10, 11)
      val v1: DoctusVector = v * 2
      assert(v1.x == 20.0)
      assert(v1.y == 22.0)
    }
    'multiply_a_vector_with_a_neg_scalar{
      val v = DoctusVector(10, 11)
      val v1: DoctusVector = v * (-2)
      assert(v1.x == -20.0)
      assert(v1.y == -22.0)
    }
    'norm_of_a_vector_01{
      val v = DoctusVector(4, 3)
      assert(v.norm == 5.0)
    }
    'norm_of_a_vector_02{
      val v = DoctusVector(40, 0)
      assert(v.norm == 40.0)
    }
    'norm_of_a_vector_03{
      val v = DoctusVector(-40, 0)
      assert(v.norm == 40.0)
    }
    'norm_of_a_vector_03{
      val v = DoctusVector(0, -40)
      assert(v.norm == 40.0)
    }
    'to_unit_01{
      val v = DoctusVector(0, -40)
      val u = v.toUnit
      assert(u.x == 0.0)
      assert(u.y == -1.0)
    }
    'to_unit_02{
      val v = DoctusVector(0, 40)
      val u = v.toUnit
      assert(u.x == 0.0)
      assert(u.y == 1.0)
    }
    'to_unit_02{
      val v = DoctusVector(0.2, 0)
      val u = v.toUnit
      assert(u.x == 1.0)
      assert(u.y == 0.0)
    }
    'rot_01{
      val v = DoctusVector(10, 0)
      val u = v.rot(math.Pi / 2.0)
      val x = round(u.x)
      val y = round(u.y)
      assert(x == 0.0)
      assert(y == 10.0)
    }
    'rot_02{
      val v = DoctusVector(10, 0)
      val u = v.rot(-math.Pi / 2.0)
      val x = round(u.x)
      val y = round(u.y)
      assert(x == 0.0)
      assert(y == -10.0)
    }
    'rot_03{
      val v = DoctusVector(10, 0)
      val u = v.rot(math.Pi)
      val x = round(u.x)
      val y = round(u.y)
      assert(x == -10.0)
      assert(y == 0.0)
    }
    'multiply_scalar_01{
      val v = DoctusVector(10, 5)
      val u = v * 10
      val x = round(u.x)
      val y = round(u.y)
      assert(x == 100)
      assert(y == 50)
    }
    'multiply_scalar_02{
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


