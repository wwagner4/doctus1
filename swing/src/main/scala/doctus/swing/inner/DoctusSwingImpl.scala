package doctus.swing.inner

import java.util.concurrent._
import doctus.core._
import doctus.core.comp._
import doctus.core.color
import doctus.core.util._
import javax.swing._
import javax.swing.text._
import java.awt.Stroke
import java.awt.image.BufferedImage
import java.awt.BasicStroke
import java.awt.Font
import java.awt.Graphics
import java.awt.RenderingHints
import java.awt.Graphics2D
import java.awt.Component
import java.awt.Canvas
import java.awt.Dimension
import java.awt.event._
import java.awt.geom.AffineTransform
import doctus.core.template.DoctusTemplateCanvas
import java.awt.Color
import doctus.swing.DoctusComponent

private[swing] trait DoctusCanvasSwing1 extends DoctusCanvas {

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

  private var pressFunc: Option[(DoctusPoint) => Unit] = None
  private var releaseFunc: Option[(DoctusPoint) => Unit] = None

  def onStart(f: (DoctusPoint) => Unit): Unit = pressFunc = Some(f)

  def onStop(f: (DoctusPoint) => Unit): Unit = releaseFunc = Some(f)
}

private[swing] trait DoctusDraggableSwing1 extends DoctusPointableSwing1 with DoctusDraggable {

  var onDragFunc: Option[(DoctusPoint) => Unit] = None

  def onDrag(f: (DoctusPoint) => Unit) = onDragFunc = Some(f)

  val mml = new MouseMotionListener {

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

trait DoctusKeySwing1 extends DoctusKey {

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
        case Some(key) => if (!active) {
          active = true
          pressFunc.foreach(f => f(key))
        }
        case None => // Nothing to do        
      }
    }

    override def keyReleased(e: KeyEvent): Unit = {
      mapKeyCode(e.getKeyCode) match {
        case Some(key) =>
          active = false
        case None => // Nothing to do        
      }
    }
  }

  comp.addKeyListener(kl)

  private var pressFunc: Option[(DoctusKeyCode) => Unit] = None
  private var releaseFunc: Option[(DoctusKeyCode) => Unit] = None

  def onKeyPressed(f: (DoctusKeyCode) => Unit): Unit = pressFunc = Some(f)

  def onKeyReleased(f: (DoctusKeyCode) => Unit): Unit = releaseFunc = Some(f)

}

