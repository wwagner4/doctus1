package doctus.jvm

import javafx.scene.canvas.{Canvas, GraphicsContext}
import javafx.scene.control.{ComboBox, TextInputControl}
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.scene.shape.StrokeLineCap
import javafx.scene.text.Font
import javafx.scene.{Node, Parent}

import doctus.core._
import doctus.core.comp._
import doctus.core.template.DoctusTemplateCanvas
import doctus.core.text.{
  DoctusFontMonospace,
  DoctusFontNamed,
  DoctusFontSansSerif,
  DoctusFontSerif
}
import doctus.core.util.DoctusPoint
import doctus.jvm.impl._

case class DoctusGraphicsFx(gc: GraphicsContext) extends DoctusGraphics {

  gc.setLineCap(StrokeLineCap.BUTT)

  def ellipse(centerX: Double, centerY: Double, a: Double, b: Double): Unit = {
    if (doFill) gc.fillOval(centerX - a, centerY - b, a * 2, b * 2)
    if (doStroke) gc.strokeOval(centerX - a, centerY - b, a * 2, b * 2)
  }

  var doFill = true
  var doStroke = true

  def fill(c: DoctusColor, alpha: Double): Unit = {
    val (r, g, b) = c.rgb
    gc.setFill(Color.rgb(r, g, b, alpha / 255))
    doFill = true
  }

  def noFill(): Unit = {
    doFill = false
  }

  var _imageMode: ImageMode = ImageModeCORNER

  def imageMode(imageMode: ImageMode): Unit = _imageMode = imageMode

  def image(img: DoctusImage, originX: Double, originY: Double): Unit = {
    img match {
      case img: DoctusImageFx =>
        val w = img.width * img.scaleFactor
        val h = img.height * img.scaleFactor
        _imageMode match {
          case ImageModeCENTER =>
            gc.drawImage(img.image, originX - w * 0.5, originY - h * 0.5, w, h)
          case ImageModeCORNER =>
            gc.drawImage(img.image, originX, originY, w, h)
        }
      case _ =>
        throw new IllegalStateException(
          "Image class not DoctusImageFx. " + img.getClass
        )
    }
  }

  def line(fromX: Double, fromY: Double, toX: Double, toY: Double): Unit = {
    gc.strokeLine(fromX, fromY, toX, toY)
  }

  def poli(poli: List[DoctusPoint]): Unit = {
    poli match {
      case Nil      => // Empty list -> Nothing to do
      case _ :: Nil => // List with one element -> Nothing to do
      case first :: rest =>
        gc.beginPath()
        gc.moveTo(first.x, first.y)
        rest.foreach { p => gc.lineTo(p.x, p.y) }
        gc.closePath()
        if (doFill) gc.fill()
        if (doStroke) gc.stroke()
    }
  }

  def rect(
      originX: Double,
      originY: Double,
      width: Double,
      height: Double
  ): Unit = {
    if (doFill) gc.fillRect(originX, originY, width, height)
    if (doStroke) gc.strokeRect(originX, originY, width, height)
  }

  def stroke(c: DoctusColor, alpha: Double): Unit = {
    val a1 = math.min(255, alpha)
    val a2 = math.max(0, a1)
    val (r, g, b) = c.rgb
    gc.setStroke(
      new Color(r.toDouble / 255, g.toDouble / 255, b.toDouble / 255, a2 / 255)
    )
    doStroke = true
  }

  def noStroke(): Unit = {
    doStroke = false
  }

  def strokeWeight(weight: Double): Unit = {
    gc.setLineWidth(weight)
    doStroke = true
  }

  def textFont(font: DoctusFont): Unit = {
    val size = gc.getFont.getSize
    // TODO implement more advanced name finding
    font match {
      case DoctusFontSansSerif   => gc.setFont(Font.font("Arial", size))
      case DoctusFontSerif       => gc.setFont(Font.font("Times", size))
      case DoctusFontMonospace   => gc.setFont(Font.font("Courier", size))
      case DoctusFontNamed(name) => gc.setFont(Font.font(name, size))
    }
  }

  def textSize(textSize: Double): Unit = {
    val family = gc.getFont.getFamily
    val size = textSize
    gc.setFont(Font.font(family, size))
  }

  def text(
      str: String,
      originX: Double,
      originY: Double,
      rotation: Double
  ): Unit = {
    val current = gc.getTransform
    val rot = current.clone()
    rot.appendTranslation(originX, originY)
    rot.appendRotation(-rotation * 180 / math.Pi)
    gc.setTransform(rot)
    gc.fillText(str, 0, 0)
    gc.setTransform(current)
  }

}

case class DoctusCanvasFx(comp: Canvas) extends DoctusCanvasFxImpl

case class DoctusTemplateCanvasFx(comp: Canvas)
    extends DoctusTemplateCanvas
    with DoctusCanvasFxImpl
    with DoctusDraggableFxImpl
    with DoctusKeyFxImp {}

// TODO Move parameter f to DoctusSelect
case class DoctusSelectFx[T](
    comboBox: ComboBox[T],
    f: (T) => String = (t: T) => t.toString
) extends DoctusSelect[T] {

  def addItem(item: T): Unit = comboBox.getItems.add(item)

  def selectedItem: T = {
    comboBox.getSelectionModel.getSelectedItem
  }

}

case class DoctusPointableFx(comp: Node) extends DoctusPointableFxImpl

case class DoctusDraggableFx(comp: Node) extends DoctusDraggableFxImpl

case class DoctusActivatableFx(comp: Parent) extends DoctusActivatable {

  def onActivated(f: () => Unit): Unit = _onStart = Some(f)
  def onDeactivated(f: () => Unit): Unit = _onStop = Some(f)

  var _onStart = Option.empty[() => Unit]
  var _onStop = Option.empty[() => Unit]

  comp.setOnMousePressed(DoctusJvmUtil.handler { e =>
    e.getScreenX
    e.getScreenY
    _onStart.foreach(f => f())
  })

  comp.setOnMouseReleased(DoctusJvmUtil.handler { e =>
    e.getScreenX
    e.getScreenY
    _onStop.foreach(f => f())
  })

}

case class DoctusTextFx(textComp: TextInputControl) extends DoctusText {

  def text: String = textComp.getText

  def text_=(txt: String) = textComp.setText(txt)
}

/** Listens to the keyboard.
  */
case class DoctusKeyFx(comp: Parent) extends DoctusKeyFxImp

case class DoctusImageFx(resource: String, scaleFactor: Double = 1.0)
    extends DoctusImage {

  def scale(factor: Double): doctus.core.DoctusImage =
    DoctusImageFx(resource, scaleFactor * factor)

  lazy val image = new Image(resource)

  def width = image.getWidth.toInt

  def height = image.getHeight.toInt

}
