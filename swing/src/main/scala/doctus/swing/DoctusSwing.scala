package doctus.swing

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

case class DoctusGraphicsSwing(graphics: Graphics2D) extends DoctusGraphics {

  var fill = new java.awt.Color(100, 100, 100, 255)
  var strokeColor = new java.awt.Color(0, 0, 0, 255)
  var isStroke: Boolean = true
  var isFill: Boolean = true

  def ellipse(centerX: Double, centerY: Double, a: Double, b: Double): Unit = {
    if (isFill) {
      graphics.setColor(fill)
      val w = a * 2
      val h = b * 2
      graphics.fillOval((centerX - a).toInt, (centerY - b).toInt, w.toInt, h.toInt)
    }
    if (isStroke) {
      graphics.setColor(strokeColor)
      val w = a * 2
      val h = b * 2
      graphics.drawOval((centerX - a).toInt, (centerY - b).toInt, w.toInt, h.toInt)
    }

  }
  def fill(c: doctus.core.DoctusColor, alpha: Double): Unit = {
    fill = toAwtColor(c, alpha)
    isFill = true
  }

  def noFill(): Unit = isFill = false

  protected var imageMode: ImageMode = ImageModeCORNER

  def imageMode(imageMode: ImageMode): Unit = {
    this.imageMode = imageMode
  }

  def image(img: DoctusImage, originX: Double, originY: Double): Unit = {
    img match {
      case i: DoctusImageSwing =>
        val trans = this.imageMode match {
          case ImageModeCORNER => AffineTransform.getTranslateInstance(originX, originY)
          case ImageModeCENTER =>
            val x = originX - img.width * i.scaleFactor / 2.0
            val y = originY - img.height * i.scaleFactor / 2.0
            AffineTransform.getTranslateInstance(x, y)
        }
        trans.concatenate(AffineTransform.getScaleInstance(i.scaleFactor, i.scaleFactor))
        graphics.drawImage(i.icon.getImage, trans, null)
      case _ => throw new IllegalStateException("Unknown implementation for 'DoctusImage'. %s" format img.getClass)
    }
  }

  def line(fromX: Double, fromY: Double, toX: Double, toY: Double): Unit = {
    if (isStroke) {
      graphics.setColor(strokeColor)
      graphics.drawLine(fromX.toInt, fromY.toInt, toX.toInt, toY.toInt)
    }
  }
  def poli(poli: List[DoctusPoint]): Unit = {
    if (isFill) {
      graphics.setColor(fill)
      val xValues = poli.map(_.x.toInt).toArray
      val yValues = poli.map(_.y.toInt).toArray
      graphics.fillPolygon(xValues, yValues, poli.size)
    }
    if (isStroke) {
      graphics.setColor(strokeColor)
      val xValues = poli.map(_.x.toInt).toArray
      val yValues = poli.map(_.y.toInt).toArray
      graphics.drawPolygon(xValues, yValues, poli.size)
    }

  }
  def rect(originX: Double, originY: Double, width: Double, height: Double): Unit = {
    if (isFill) {
      graphics.setColor(fill)
      graphics.fillRect(originX.toInt, originY.toInt, width.toInt, height.toInt)
    }
    if (isStroke) {
      graphics.setColor(strokeColor)
      graphics.drawRect(originX.toInt, originY.toInt, width.toInt, height.toInt)
    }
  }

  def stroke(c: doctus.core.DoctusColor, alpha: Double): Unit = {
    strokeColor = toAwtColor(c, alpha)
    isStroke = true
  }
  def noStroke(): Unit = isStroke = false
  def strokeWeight(weight: Double): Unit = {
    graphics.setStroke(new BasicStroke(weight.toFloat, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, null, 0.0f))
  }

  var textFontSize: Float = 12.0f

  def textFont(font: DoctusFont): Unit = {
    import doctus.core.text._
    val f = font match {
      case DoctusFontMonospace   => new Font("Monospaced", Font.PLAIN, textFontSize.toInt)
      case DoctusFontSerif       => new Font("Serif", Font.PLAIN, textFontSize.toInt)
      case DoctusFontSansSerif   => new Font("SansSerif", Font.PLAIN, textFontSize.toInt)
      case DoctusFontNamed(name) => new Font(name, Font.PLAIN, textFontSize.toInt)
    }
    graphics.setFont(f)
  }
  def textSize(textSize: Double): Unit = {
    textFontSize = textSize.toFloat
    val font = graphics.getFont
    graphics.setFont(font.deriveFont(textSize.toFloat))

  }

  def text(str: String, originX: Double, originY: Double, rotation: Double): Unit = {
    graphics.setColor(fill)
    val t = graphics.getTransform
    val at = new AffineTransform();
    at.translate(originX, originY)
    at.rotate(-rotation);
    graphics.transform(at)
    graphics.drawString(str, 0, 0)
    graphics.setTransform(t)
  }

  private def toAwtColor(c: DoctusColor, alpha: Double): java.awt.Color = {
    def minmax(value: Int): Int = math.max(0, math.min(255, value))
    c.rgb match {
      case (r, g, b) =>
        val red = minmax(r)
        val green = minmax(g)
        val blue = minmax(b)
        val a = minmax(alpha.toInt)
        new java.awt.Color(red, green, blue, a)
    }
  }

}

/**
 * Extended scala.swing.Component with some extra functionality
 * needed for SwingCanvas
 */
trait DoctusComponent extends Component {
  def paintOpt_=(paintOpt: Option[DoctusGraphics => Unit])
  def paintOpt: Option[DoctusGraphics => Unit]
}

/**
 * A normal scala.swing.Component with some extra functionality
 * needed for SwingCanvas
 */
object DoctusComponentFactory {

  /**
   * Parameters are only used for Mac.
   */
  def component(bufferWidth: Int = 3000, bufferHeight: Int = 3000,
                textAntialiasing: Boolean = true,
                doubleBuffering: Boolean = true): DoctusComponent = {
    import doctus.swing.DoctusSwingUtil._

    case class Buffer(image: BufferedImage, graphics: DoctusGraphics)

    def createBuffer: Buffer = {
      val bi = new BufferedImage(3000, 3000, BufferedImage.TYPE_INT_RGB)
      val bg = bi.createGraphics()
      bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      if (textAntialiasing) {
        bg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
      } else {
        bg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF)
      }
      val bdg = DoctusGraphicsSwing(bg.asInstanceOf[Graphics2D])
      Buffer(bi, bdg)
    }

    lazy val buffer = createBuffer

    def createCanvas = new Canvas with DoctusComponent {

      var paintOpt: Option[(DoctusGraphics) => Unit] = None

      override def paint(g: Graphics): Unit = {
        if (doubleBuffering) {
          val doctusGraphics = DoctusGraphicsSwing(g.asInstanceOf[Graphics2D])
          paintOpt.foreach(f => f(buffer.graphics))
          g.drawImage(buffer.image, 0, 0, null)
        } else {
          val doctusGraphics = DoctusGraphicsSwing(g.asInstanceOf[Graphics2D])
          paintOpt.foreach(f => f(doctusGraphics))
        }
      }

      override def update(g: Graphics): Unit = paint(g)

    }

    def createJPanel = new JPanel with DoctusComponent {

      var paintOpt: Option[(DoctusGraphics) => Unit] = None

      override def paint(g: Graphics): Unit = {
        val doctusGraphics = DoctusGraphicsSwing(g.asInstanceOf[Graphics2D])
        paintOpt.foreach(f => f(doctusGraphics))
      }

    }

    if (osName == Mac) {
      createCanvas
    } else createJPanel
  }

}

import inner._

case class DoctusCanvasSwing(comp: DoctusComponent) extends DoctusCanvasSwing1

case class DoctusTemplateCanvasSwing(comp: DoctusComponent) extends DoctusTemplateCanvas with DoctusCanvasSwing1 with DoctusDraggableSwing1

case class DoctusSelectSwing[T](comboBox: JComboBox[T], f: (T) => String = (t: T) => t.toString()) extends DoctusSelect[T] {

  import javax.swing._

  val model = new DefaultComboBoxModel[T]()
  comboBox.setModel(model)

  object TaskCellRenderer extends ListCellRenderer[T] {
    val peerRenderer: ListCellRenderer[T] = (new DefaultListCellRenderer).asInstanceOf[ListCellRenderer[T]]
    override def getListCellRendererComponent(
      list: JList[_ <: T], item: T, index: Int,
      isSelected: Boolean, cellHasFocus: Boolean): Component = {
      val component = peerRenderer.getListCellRendererComponent(
        list, item, index, isSelected, cellHasFocus).asInstanceOf[JLabel]
      component.setText(f(item))
      component
    }
  }
  comboBox.setRenderer(TaskCellRenderer)

  def addItem(item: T): Unit = model.addElement(item)

  def selectedItem: T = comboBox.getSelectedItem.asInstanceOf[T]
}

case class DoctusPointableSwing(comp: Component) extends DoctusPointableSwing1

case class DoctusDraggableSwing(comp: Component) extends DoctusDraggableSwing1 with DoctusDraggable

case class DoctusActivatableSwing(comp: Component) extends DoctusActivatable {

  val p = DoctusPointableSwing(comp)

  def onActivated(f: () => Unit): Unit = p.onStart { _ => f() }
  def onDeactivated(f: () => Unit): Unit = p.onStop { _ => f() }

}

case object DoctusSchedulerSwing extends DoctusScheduler {

  def start(f: () => Unit, duration: Int, initialDelay: Int = 0): DoctusScheduler.Stopper = {
    require(duration > 0, "Duration must be greater than zero. " + duration)
    require(initialDelay >= 0, "Initial delay must be greater equal to zero. " + duration)

    val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(1)
    val b = new Runnable {
      override def run(): Unit = f()
    }
    val future = scheduler.scheduleAtFixedRate(b, initialDelay, duration, TimeUnit.MILLISECONDS)

    new DoctusScheduler.Stopper {
      // Stops the execution of a Scheduler
      override def stop(): Unit = future.cancel(true)
    }
  }

}

case class DoctusTextSwing(textComp: JTextComponent) extends DoctusText {

  def text: String = {
    textComp.getText
  }

  def text_=(txt: String) = {
    textComp.setText(txt)
  }
}

object DoctusActivatableSwingKey {

  implicit class Implicit(c: Component) {

    /**
     * Convenience function for gaining the focus of a component.
     * Consider making the focus handling explicit as needed for you use case
     *
     * - The component is made focusable
     * - The component gains the focus when the front window gains the focus,
     *   or when the user clicked the component
     */
    def requestFocusForDoctusActivatableSwingKey(): Unit = {

      c.setFocusable(true)

      val win = SwingUtilities.getRoot(c)
      win match {
        case null => () // The component is not yet bound to a window (e.g. JFrame)
        case w: java.awt.Window => {
          val wfl = new WindowFocusListener {
            def windowGainedFocus(x$1: java.awt.event.WindowEvent): Unit = {
              c.requestFocusInWindow()
            }
            def windowLostFocus(x$1: java.awt.event.WindowEvent): Unit = ()
          }
          w.addWindowFocusListener(wfl)
        }
      }

      val ml = new MouseListener {
        def mouseClicked(e: java.awt.event.MouseEvent): Unit = {
          c.requestFocusInWindow()
        }
        def mouseEntered(e: java.awt.event.MouseEvent): Unit = ()
        def mouseExited(e: java.awt.event.MouseEvent): Unit = ()
        def mousePressed(e: java.awt.event.MouseEvent): Unit = ()
        def mouseReleased(e: java.awt.event.MouseEvent): Unit = ()
      }
      c.addMouseListener(ml)

    }
  }
}

package inner {

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

}

/**
 * Listens to the keyboard.
 *
 * Consider that listening only works if the component 'comp' has currently the focus.
 * You may use 'requestFocusForDoctusActivatableSwingKey' as for convenience.
 */
case class DoctusKeySwing(comp: Component) extends DoctusKey {

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
          pressFunc.foreach(f => f(key))
        case None => // Nothing to do        
      }
    }
  }

  comp.addKeyListener(kl)

  private var pressFunc: Option[(DoctusKeyCode) => Unit] = None
  private var releaseFunc: Option[(DoctusKeyCode) => Unit] = None

  def onActivated(f: (DoctusKeyCode) => Unit): Unit = pressFunc = Some(f)

  def onDeactivated(f: (DoctusKeyCode) => Unit): Unit = releaseFunc = Some(f)

}

case class DoctusImageSwing(resource: String, scaleFactor: Double = 1.0) extends DoctusImage {

  val icon: ImageIcon = {
    val imgPath = resource
    val imgr = getClass.getClassLoader.getResource(imgPath)
    assert(imgr != null, s"Found no resource for $imgPath")
    new ImageIcon(imgr)
  }

  def scale(factor: Double): doctus.core.DoctusImage = DoctusImageSwing(resource, scaleFactor * factor)

  def width = icon.getIconWidth()

  def height = icon.getIconHeight()

}

