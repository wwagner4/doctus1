package doctus.core.util.impl

import doctus.core.util.{DoctusPoint, DoctusVector}

private[util] object DoctusVectorImpl {

  trait AbstractDoctusVector extends DoctusVector {
    def +(v: DoctusVector) = add(this, v)
    def -(v: DoctusVector) = sub(this, v)
    def *(s: Double) = mulScalar(this, s)
    def norm = norm1(this)
    def toUnit = toUnit1(this)
    def rot(rad: Double) = rot1(this, rad)
  }

  trait AbstractDoctusPoint extends DoctusPoint {
    def +(v: DoctusVector): DoctusPoint = add(this, v)
    def -(v: DoctusVector): DoctusPoint = sub(this, v)
    def -(p: DoctusPoint): DoctusVector = sub(this, p)
  }

  def add(v1: DoctusVector, v2: DoctusVector): DoctusVector = {
    vector(v1.x + v2.x, v1.y + v2.y)
  }
  def add(p: DoctusPoint, v: DoctusVector): DoctusPoint = {
    point(p.x + v.x, p.y + v.y)
  }
  def sub(v1: DoctusVector, v2: DoctusVector): DoctusVector = {
    vector(v1.x - v2.x, v1.y - v2.y)
  }
  def mulScalar(v: DoctusVector, s: Double): DoctusVector = {
    vector(v.x * s, v.y * s)
  }
  def sub(p: DoctusPoint, v: DoctusVector): DoctusPoint = {
    point(p.x - v.x, p.y - v.y)
  }
  def sub(p1: DoctusPoint, p2: DoctusPoint): DoctusVector = {
    vector(p1.x - p2.x, p1.y - p2.y)
  }
  def vector(_x: Double, _y: Double): DoctusVector = {
    val __x = _x
    val __y = _y
    new AbstractDoctusVector {
      def x = __x
      def y = __y
    }
  }
  def point(_x: Double, _y: Double): DoctusPoint = {
    val __x = _x
    val __y = _y
    new AbstractDoctusPoint {
      def x = __x
      def y = __y
    }
  }
  def norm1(v: DoctusVector): Double = {
    math.sqrt((v.x * v.x) + (v.y * v.y))
  }
  def toUnit1(v: DoctusVector): DoctusVector = {
    val n = v.norm
    vector(v.x / n, v.y / n)
  }

  def rot1(v: DoctusVector, rad: Double): DoctusVector = {
    val x = v.x * math.cos(rad) - v.y * math.sin(rad)
    val y = v.x * math.sin(rad) + v.y * math.cos(rad)
    vector(x, y)
  }

}
