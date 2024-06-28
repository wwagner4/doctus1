package doctus.swing

import java.awt.{
  BasicStroke,
  Canvas,
  CardLayout,
  Component,
  Container,
  Font,
  Graphics,
  Graphics2D
}
import java.awt.event.MouseListener
import java.awt.event.WindowFocusListener
import java.awt.geom.AffineTransform
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import doctus.core.{
  DoctusActivatable,
  DoctusColor,
  DoctusDraggable,
  DoctusFont,
  DoctusGraphics,
  DoctusImage,
  DoctusScheduler,
  DoctusSchedulerStopper,
  ImageMode,
  ImageModeCENTER,
  ImageModeCORNER
}
import doctus.core.comp.{
  DoctusCard,
  DoctusSelect,
  DoctusText,
  SelectItemDescription
}
import doctus.core.template.DoctusTemplateCanvas
import doctus.core.util.DoctusPoint
import impl.{
  AbstractDoctusComboBoxModel,
  AbstractDoctusListModel,
  DoctusCanvasSwing1,
  DoctusDraggableSwing1,
  DoctusKeySwing1,
  DoctusPointableSwing1
}

import javax.swing.{
  DefaultListSelectionModel,
  ImageIcon,
  JComboBox,
  JList,
  JPanel,
  ListSelectionModel
}
import javax.swing.text.JTextComponent

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
      graphics.fillOval(
        (centerX - a).toInt,
        (centerY - b).toInt,
        w.toInt,
        h.toInt
      )
    }
    if (isStroke) {
      graphics.setColor(strokeColor)
      val w = a * 2
      val h = b * 2
      graphics.drawOval(
        (centerX - a).toInt,
        (centerY - b).toInt,
        w.toInt,
        h.toInt
      )
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
          case ImageModeCORNER =>
            AffineTransform.getTranslateInstance(originX, originY)
          case ImageModeCENTER =>
            val x = originX - img.width * i.scaleFactor / 2.0
            val y = originY - img.height * i.scaleFactor / 2.0
            AffineTransform.getTranslateInstance(x, y)
        }
        trans.concatenate(
          AffineTransform.getScaleInstance(i.scaleFactor, i.scaleFactor)
        )
        graphics.drawImage(i.icon.getImage, trans, null)
      case _ =>
        throw new IllegalStateException(
          "Unknown implementation for 'DoctusImage'. %s" format img.getClass
        )
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
  def rect(
      originX: Double,
      originY: Double,
      width: Double,
      height: Double
  ): Unit = {
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
    graphics.setStroke(
      new BasicStroke(
        weight.toFloat,
        BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_MITER,
        10.0f,
        null,
        0.0f
      )
    )
  }

  var textFontSize: Float = 12.0f

  def textFont(font: DoctusFont): Unit = {
    import doctus.core.text._
    val f = font match {
      case DoctusFontMonospace =>
        new Font("Monospaced", Font.PLAIN, textFontSize.toInt)
      case DoctusFontSerif => new Font("Serif", Font.PLAIN, textFontSize.toInt)
      case DoctusFontSansSerif =>
        new Font("SansSerif", Font.PLAIN, textFontSize.toInt)
      case DoctusFontNamed(name) =>
        new Font(name, Font.PLAIN, textFontSize.toInt)
    }
    graphics.setFont(f)
  }
  def textSize(textSize: Double): Unit = {
    textFontSize = textSize.toFloat
    val font = graphics.getFont
    graphics.setFont(font.deriveFont(textSize.toFloat))

  }

  def text(
      str: String,
      originX: Double,
      originY: Double,
      rotation: Double
  ): Unit = {
    graphics.setColor(fill)
    val t = graphics.getTransform
    val at = new AffineTransform()
    at.translate(originX, originY)
    at.rotate(-rotation)
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

/** Extended scala.swing.Component with a graphics member for drawing
  */
trait DoctusSwingComponent extends Component {
  def graphicsOpt_=(paintOpt: Option[DoctusGraphics => Unit]): Unit
  def graphicsOpt: Option[DoctusGraphics => Unit]
}

/** A normal scala.swing.Component with some extra functionality needed for
  * SwingCanvas
  */
object DoctusSwingComponentFactory {

  /** Parameters are only used for Mac.
    */
  def component: DoctusSwingComponent = {

    System.setProperty("sun.java2d.opengl", "true")

    import doctus.swing.DoctusSwingUtil._

    // noinspection VarCouldBeVal
    def createCanvas = new Canvas with DoctusSwingComponent {

      var graphicsOpt: Option[DoctusGraphics => Unit] = None

      override def paint(g: Graphics): Unit = {
        val doctusGraphics = DoctusGraphicsSwing(g.asInstanceOf[Graphics2D])
        graphicsOpt.foreach(f => f(doctusGraphics))
      }

      override def update(g: Graphics): Unit = paint(g)

    }

    // noinspection VarCouldBeVal
    def createJPanel = new JPanel with DoctusSwingComponent {

      var graphicsOpt: Option[DoctusGraphics => Unit] = None

      override def paint(g: Graphics): Unit = {
        val doctusGraphics = DoctusGraphicsSwing(g.asInstanceOf[Graphics2D])
        graphicsOpt.foreach(f => f(doctusGraphics))
      }

    }

    if (osName == Mac) createCanvas
    else createJPanel
  }

}

case class DoctusCanvasSwing(comp: DoctusSwingComponent)
    extends DoctusCanvasSwing1

//noinspection ScalaUnusedSymbol
case class DoctusTemplateCanvasSwing(comp: DoctusSwingComponent)
    extends DoctusTemplateCanvas
    with DoctusCanvasSwing1
    with DoctusDraggableSwing1
    with DoctusKeySwing1

case class DoctusPointableSwing(comp: Component) extends DoctusPointableSwing1

//noinspection ScalaUnusedSymbol
case class DoctusDraggableSwing(comp: Component)
    extends DoctusDraggableSwing1
    with DoctusDraggable

case class DoctusActivatableSwing(comp: Component) extends DoctusActivatable {

  private val p = DoctusPointableSwing(comp)

  def onActivated(f: () => Unit): Unit = p.onStart { _ => f() }
  def onDeactivated(f: () => Unit): Unit = p.onStop { _ => f() }

}

case object DoctusSchedulerSwing extends DoctusScheduler {

  def start(
      f: () => Unit,
      duration: Int,
      initialDelay: Int = 0
  ): DoctusSchedulerStopper = {
    require(duration > 0, "Duration must be greater than zero. " + duration)
    require(
      initialDelay >= 0,
      "Initial delay must be greater equal to zero. " + duration
    )

    val scheduler: ScheduledExecutorService =
      Executors.newScheduledThreadPool(1)
    val b = new Runnable {
      override def run(): Unit = f()
    }
    val future = scheduler.scheduleAtFixedRate(
      b,
      initialDelay,
      duration,
      TimeUnit.MILLISECONDS
    )

    () => future.cancel(true)
  }

}

//noinspection ScalaUnusedSymbol
case class DoctusTextSwing(textComp: JTextComponent) extends DoctusText {

  def text: String = {
    textComp.getText
  }

  def text_=(txt: String): Unit = {
    textComp.setText(txt)
  }
}

//noinspection ScalaUnusedSymbol
object DoctusActivatableSwingKey {

  // noinspection ScalaUnusedSymbol
  implicit class Implicit(c: Component) {

    /** Convenience function for gaining the focus of a component. Consider
      * making the focus handling explicit as needed for you use case
      *
      *   - The component is made focusable
      *   - The component gains the focus when the front window gains the focus,
      *     or when the user clicked the component
      */
    def requestFocusForDoctusActivatableSwingKey(): Unit = {

      c.setFocusable(true)

      val win = javax.swing.SwingUtilities.getRoot(c)
      win match {
        case null =>
          () // The component is not yet bound to a window (e.g. JFrame)
        case w: java.awt.Window =>
          val wfl = new WindowFocusListener {
            def windowGainedFocus(x$1: java.awt.event.WindowEvent): Unit = {
              c.requestFocusInWindow()
            }
            def windowLostFocus(x$1: java.awt.event.WindowEvent): Unit = ()
          }
          w.addWindowFocusListener(wfl)
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

/** Listens to the keyboard.
  *
  * Consider that listening only works if the component 'comp' has currently the
  * focus. You may use 'requestFocusForDoctusActivatableSwingKey' as for
  * convenience.
  */
//noinspection ScalaUnusedSymbol
case class DoctusKeySwing(comp: Component) extends DoctusKeySwing1

case class DoctusImageSwing(resource: String, scaleFactor: Double = 1.0)
    extends DoctusImage {

  val icon: ImageIcon = {
    val imgPath = resource
    val img = getClass.getClassLoader.getResource(imgPath)
    assert(img != null, s"Found no resource for $imgPath")
    new ImageIcon(img)
  }

  def scale(factor: Double): doctus.core.DoctusImage =
    DoctusImageSwing(resource, scaleFactor * factor)

  def width: Int = icon.getIconWidth

  def height: Int = icon.getIconHeight

}

//noinspection ScalaUnusedSymbol
class DoctusSelectSwingList[C](
    val list: JList[String]
) extends DoctusSelect[C] {

  // Adapt JList
  private val sel = new DefaultListSelectionModel()
  sel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
  list.setSelectionModel(sel)

  private var listModelOpt = Option.empty[AbstractDoctusListModel[C]]

  override def setItems(
      items: Seq[C],
      itemDescription: SelectItemDescription[C]
  ): Unit = {
    val model = createListModel(items, itemDescription)
    listModelOpt = Some(model)
    list.setModel(model)
  }

  override def selectedItem: Option[C] = {
    val index = list.getSelectedIndex
    if (index < 0) None
    else listModelOpt.map { lm => lm.values(index) }
  }

  private def createListModel(
      items: Seq[C],
      itemDescription: SelectItemDescription[C]
  ) = {
    new AbstractDoctusListModel[C] {

      override def values: Seq[C] = items

      override def columnWidths: Seq[Int] = itemDescription.columnWidths

      override def extractColumnString(item: C, columnIndex: Int): String = {
        itemDescription.extractColumnString(item, columnIndex)
      }
    }
  }

}

//noinspection ScalaUnusedSymbol
class DoctusSelectSwingComboBox[C](
    val comboBox: JComboBox[String]
) extends DoctusSelect[C] {

  private var comboBoxModelOpt = Option.empty[AbstractDoctusComboBoxModel[C]]

  override def setItems(
      items: Seq[C],
      itemDescription: SelectItemDescription[C]
  ): Unit = {
    val model: AbstractDoctusComboBoxModel[C] =
      createComboBoxModel(items, itemDescription)
    comboBoxModelOpt = Some(model)
    comboBox.setModel(model)
  }

  override def selectedItem: Option[C] = {
    val index = comboBox.getSelectedIndex
    if (index < 0) None
    else comboBoxModelOpt.map { model => model.values(index) }
  }

  def createComboBoxModel(
      items: Seq[C],
      itemDescription: SelectItemDescription[C]
  ): AbstractDoctusComboBoxModel[C] = {
    new AbstractDoctusComboBoxModel[C] {

      override def values: Seq[C] = items

      override def columnWidths: Seq[Int] = itemDescription.columnWidths

      override def extractColumnString(item: C, columnIndex: Int): String = {
        itemDescription.extractColumnString(item, columnIndex)
      }

      var selectedItem: AnyRef = {
        if (items.isEmpty) ""
        else getElementAt(0)
      }

      override def setSelectedItem(anItem: AnyRef): Unit = {
        selectedItem = anItem
      }

      override def getSelectedItem: AnyRef = {
        selectedItem
      }
    }
  }

}

case class DoctusSwingCard(container: Container, cardLayout: CardLayout)
    extends DoctusCard {

  override def next(): Unit = cardLayout.next(container)

  override def previous(): Unit = cardLayout.previous(container)

  override def show(name: String): Unit = cardLayout.show(container, name)
}
