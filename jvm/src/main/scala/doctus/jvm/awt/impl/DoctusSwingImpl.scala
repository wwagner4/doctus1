package doctus.jvm.awt.impl

import java.awt.Dimension

import doctus.core._
import doctus.jvm.awt.DoctusComponent

private[awt] trait DoctusCanvasSwing1 extends DoctusCanvas {

  def comp: DoctusComponent

  def onRepaint(f: (DoctusGraphics) => Unit): Unit = {
    comp.paintOpt = Some(f)
  }

  def repaint() = comp.repaint()

  def width = {
    val size: Dimension = comp.getSize
    size.width
  }

  def height = {
    val size: Dimension = comp.getSize
    size.height
  }
}

