package doctus.core.util

/** Represents a two dimensional vector
  */
trait DoctusVector {

  def +(v: DoctusVector): DoctusVector
  def -(v: DoctusVector): DoctusVector
  def *(s: Double): DoctusVector
  def norm: Double
  def toUnit: DoctusVector
  def rot(rad: Double): DoctusVector
  def x: Double
  def y: Double
  override def equals(other: Any): Boolean = other match {
    case otherPoint: DoctusVector => otherPoint.x == x && otherPoint.y == y
    case _                        => false
  }
  override def hashCode(): Int = 1
  override def toString = "v[%.2f, %.2f]" format (this.x, this.y)

}

/** Represents a two dimensional point
  */
object DoctusVector {

  import impl.DoctusVectorImpl._

  def apply(x: Double, y: Double): DoctusVector = vector(x, y)

}

trait DoctusPoint {

  def x: Double
  def y: Double
  override def equals(other: Any): Boolean = other match {
    case _other: DoctusPoint => _other.x == x && _other.y == y
    case _                   => false
  }
  override def hashCode(): Int = 41 * (41 + x.toInt) + y.toInt
  override def toString = "p[%.2f, %.2f]" format (this.x, this.y)

  def +(v: DoctusVector): DoctusPoint
  def -(v: DoctusVector): DoctusPoint
  def -(v: DoctusPoint): DoctusVector

}

object DoctusPoint {

  import impl.DoctusVectorImpl._

  def apply(x: Double, y: Double): DoctusPoint = point(x, y)

}
