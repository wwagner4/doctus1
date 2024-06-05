package doctus.swing.impl

import doctus.core._
import doctus.core.util._

import java.awt.Component
import java.awt.Dimension
import java.awt.event._
import doctus.swing.DoctusSwingComponent

import javax.swing.{ComboBoxModel, ListModel}
import javax.swing.event.ListDataListener

private[swing] trait DoctusCanvasSwing1 extends DoctusCanvas {

  def comp: DoctusSwingComponent

  def onRepaint(f: DoctusGraphics => Unit): Unit = {
    comp.graphicsOpt = Some(f)
  }

  def repaint(): Unit = comp.repaint()

  def width: Int = {
    val size: Dimension = comp.getSize
    size.width
  }

  def height: Int = {
    val size: Dimension = comp.getSize
    size.height
  }
}

private[swing] trait DoctusPointableSwing1 extends DoctusPointable {

  def comp: Component

  val ml = new MouseListener {
    override def mouseExited(e: MouseEvent): Unit = ()

    override def mouseClicked(e: MouseEvent): Unit = ()

    override def mouseEntered(e: MouseEvent): Unit = ()

    override def mousePressed(e: MouseEvent): Unit = {
      val x = e.getPoint.getX.toInt
      val y = e.getPoint.getY.toInt
      val p = DoctusPoint(x, y)
      pressFunc.foreach(f => f(p))
    }

    override def mouseReleased(e: MouseEvent): Unit = {
      val x = e.getPoint.getX.toInt
      val y = e.getPoint.getY.toInt
      val p = DoctusPoint(x, y)
      releaseFunc.foreach(f => f(p))
    }

  }

  comp.addMouseListener(ml)

  private var pressFunc: Option[DoctusPoint => Unit] = None
  private var releaseFunc: Option[(DoctusPoint) => Unit] = None

  def onStart(f: DoctusPoint => Unit): Unit = pressFunc = Some(f)

  def onStop(f: DoctusPoint => Unit): Unit = releaseFunc = Some(f)
}

private[swing] trait DoctusDraggableSwing1
    extends DoctusPointableSwing1
    with DoctusDraggable {

  var onDragFunc: Option[DoctusPoint => Unit] = None

  def onDrag(f: DoctusPoint => Unit): Unit = onDragFunc = Some(f)

  val mml: MouseMotionListener = new MouseMotionListener {

    def mouseDragged(me: MouseEvent): Unit = {
      val x = me.getPoint.getX
      val y = me.getPoint.getY
      val p = DoctusPoint(x, y)
      onDragFunc.foreach(f => f(p))
    }

    def mouseMoved(me: MouseEvent): Unit = ()

  }
  comp.addMouseMotionListener(mml)

}

private[swing] trait DoctusKeySwing1 extends DoctusKey {

  def comp: Component

  private def mapKeyCode(code: Int): Option[DoctusKeyCode] = {
    code match {
      case KeyEvent.VK_DOWN  => Some(DKC_Down)
      case KeyEvent.VK_UP    => Some(DKC_Up)
      case KeyEvent.VK_LEFT  => Some(DKC_Left)
      case KeyEvent.VK_RIGHT => Some(DKC_Right)
      case KeyEvent.VK_SPACE => Some(DKC_Space)
      case KeyEvent.VK_ENTER => Some(DKC_Enter)
      case _                 => None
    }
  }

  comp.setFocusable(true)

  // Needed to avoid repeated key pressed events
  var active = false

  val kl = new KeyListener {

    override def keyTyped(e: KeyEvent): Unit = ()

    override def keyPressed(e: KeyEvent): Unit = {
      mapKeyCode(e.getKeyCode) match {
        case Some(key) =>
          if (!active) {
            active = true
            pressFunc.foreach(f => f(key))
          }
        case None => // Nothing to do
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {
      mapKeyCode(e.getKeyCode) match {
        case Some(_) =>
          active = false
        case None => // Nothing to do
      }
    }
  }

  comp.addKeyListener(kl)

  var pressFunc: Option[(DoctusKeyCode) => Unit] = None

  var releaseFunc: Option[(DoctusKeyCode) => Unit] = None

  def onKeyPressed(f: (DoctusKeyCode) => Unit): Unit = pressFunc = Some(f)

  def onKeyReleased(f: (DoctusKeyCode) => Unit): Unit = releaseFunc = Some(f)

}

private[swing] abstract class AbstractDoctusListModel[C]
    extends ListModel[String] {

  def values: Seq[C]

  def columnWidths: Seq[Int]

  def extractColumnString(item: C, columnIndex: Int): String

  override def getSize: Int = values.size

  override def getElementAt(index: Int): String = {
    val elem = values(index)
    columnWidths.zipWithIndex
      .map { case (c, i) =>
        val value = extractColumnString(elem, i)
        doctus.core.util.cropString(value, c)
      }
      .mkString("|")
  }

  override def addListDataListener(l: ListDataListener): Unit = {}

  override def removeListDataListener(l: ListDataListener): Unit = {}
}

abstract class AbstractDoctusComboBoxModel[C] extends ComboBoxModel[String] {

  def values: Seq[C]

  def columnWidths: Seq[Int]

  def extractColumnString(item: C, columnIndex: Int): String

  override def getSize: Int = values.size

  override def getElementAt(index: Int): String = {
    val elem = values(index)
    columnWidths.zipWithIndex
      .map { case (c, i) =>
        val value = extractColumnString(elem, i)
        doctus.core.util.cropString(value, c)
      }
      .mkString("|")
  }

  override def addListDataListener(l: ListDataListener): Unit = {}

  override def removeListDataListener(l: ListDataListener): Unit = {}
}
