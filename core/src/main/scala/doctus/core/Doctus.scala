package doctus.core

import doctus.core.DoctusScheduler.Stopper

import doctus.core.util._


/**
 * Representation of a font in doctus
 */
trait DoctusFont



/**
 * Representation of a color value in Doctus
 *
 */
trait DoctusColor {

  /**
   * Returns the red, green and blue values of the colors
   * The values range from 0 to 255
   */
  def rgb: (Int, Int, Int)

}

trait DoctusImage {

  /**
   * Returns a new instance of DoctusImage which
   * size is the size of the original image
   * multiplied by 'factor'
   */
  def scale(factor: Double): DoctusImage
  
  /**
   * Width of the unscaled image in pixel
   */
  def width: Int
  
  /**
   * Height of the unscaled image in pixel
   */
  def height: Int

}

sealed trait ImageMode

case object ImageModeCENTER extends ImageMode
case object ImageModeCORNER extends ImageMode


/**
 * Drawing functions
 */
trait DoctusGraphics {

  /**
   * Set the fill color for subsequent drawings of shapes (ellipse, rect, ...)
   *
   * Values for alpha range from 0 to 255.
   * 0   ... fully transparent
   * 255 ... fully opaque
   */
  def fill(c: DoctusColor, alpha: Double): Unit

  /**
   * Disables filling of shapes (ellipse, rect, ...)
   */
  def noFill(): Unit

  /**
   * Set the color for subsequent drawings of lines
   * and the border of shapes (ellipse, rect, ...)
   *
   * Values for alpha range from 0 to 255.
   * 0   ... fully transparent
   * 255 ... fully opaque
   */
  def stroke(c: DoctusColor, alpha: Double): Unit

  /**
   *  Sets the width in pixel of the stroke used
   *  for lines, points, and the border around shapes.
   */
  def strokeWeight(weight: Double): Unit

  /**
   * Disables drawing of borders for shapes (ellipse, rect, ...)
   * Lines are still drawn after the call to 'noStroke'
   */
  def noStroke(): Unit

  /**
   *  Sets the font used for subsequent calls to 'text(...)'
   */
  def textFont(font: DoctusFont): Unit

  /**
   *  Sets the text size used for subsequent calls to 'text(...)'
   */
  def textSize(textSize: Double): Unit

  /**
   * Draws a text using the current font.
   * 'originX' and 'originY' define the top left corner of the image.
   * 'rotation' is the rotation angle of the text in radians
   * 0 is a horizontal text from left to right
   * Pi/2 (90 deg) is a vertical text going upward. In that case
   *   'origin' is the bottom left corner of the box surrounding the text
   */
  def text(str: String, originX: Double, originY: Double, rotation: Double):Unit

  /**
   * Draws a text using the current font.
   * 'origin' is the top left corner of the image.
   * 'rotation' is the rotation angle of the text in radians
   * 0 is a horizontal text from left to right
   * Pi/2 (90 deg) is a vertical text going upward. In that case
   *   'origin' is the bottom left corner of the box surrounding the text
   *
   * The color and opacity of the text can be set using the 'fill' command.
   */
  def text(str: String, origin: DoctusPoint, rotation: Double): Unit = {
    text(str, origin.x, origin.y, rotation)    
  }

  /**
   * Draws a line from the point 'fromX' 'fromY' to the point 'toX' 'toY'
   */
  def line(fromX: Double, fromY: Double, toX: Double, toY: Double): Unit

  /**
   * Draws a line from 'from' to 'to'
   */
  def line(from: DoctusPoint, to: DoctusPoint): Unit = {
    line(from.x, from.y, to.x, to.y)
  }

  /**
   * Draws a rectangle
   * 'originX' and 'originY' define the top left corner of the rectangle.
   */
  def rect(originX: Double, originY: Double, width: Double, height: Double): Unit

  /**
   * Draws a rectangle
   * 'origin' is the top left corner of the rectangle.
   */
  def rect(origin: DoctusPoint, width: Double, height: Double): Unit = {
    rect(origin.x, origin.y, width, height)
  }

  /**
   * Draws an ellipse
   * 'centerX' and 'centerY' define the the center of the ellipse
   * 'a' and 'b' are the length of the semi major/minor of the ellipse
   */
  def ellipse(centerX: Double, centerY: Double, a: Double, b: Double): Unit

  /**
   * Draws an ellipse
   * 'center' is the the center of the ellipse
   * 'a' and 'b' are the length of the semi major/minor of the ellipse
   */
  def ellipse(center: DoctusPoint, a: Double, b: Double): Unit = {
    ellipse(center.x, center.y, a, b)
  }

  /**
   * Draws a polygon line defined by the points
   */
  def poli(poli: List[DoctusPoint]): Unit

  /**
   * Draws an image
   * 'originX' and 'originY' define the top left corner of the image.
   */
  def image(img: DoctusImage, originX: Double, originY: Double): Unit

  /**
   * Draws an image
   * 'origin' is the top left corner of the image.
   */
  def image(img: DoctusImage, origin: DoctusPoint): Unit = {
    image(img, origin.x, origin.y)
  }
  
  def imageMode(imageMode: ImageMode): Unit

}

/**
 * A component on which you can paint using DoctusGraphics
 */
trait DoctusCanvas {

  /**
   * Defines a paint function f that gets triggered whenever
   * repaint is called
   */
  def onRepaint(f: (DoctusGraphics) => Unit): Unit

  /**
   * Creates a DoctusGraphics from the underlying system and calls onRepaint.
   */
  def repaint(): Unit

  def width: Int
  def height: Int
}

/**
 * A component that can be activated and deactivated but does not provide
 * a position where it was activated
 * E.g. A keyboard button that is pressed and released, a keystroke ...
 */
trait DoctusActivatable {

  /**
   * Defines a function that gets called whenever the component is activated
   * E.g. a button was clicked
   */
  def onActivated(f: () => Unit): Unit

  /**
   * Defines a function that gets called whenever the component is deactivated
   * E.g. a button was released
   */
  def onDeactivated(f: () => Unit): Unit
}

sealed trait DoctusKeyCode
case object DKC_Space extends DoctusKeyCode
case object DKC_Up extends DoctusKeyCode
case object DKC_Down extends DoctusKeyCode
case object DKC_Right extends DoctusKeyCode
case object DKC_Left extends DoctusKeyCode
case object DKC_Enter extends DoctusKeyCode

/**
 * A component reaction to key events
 */
trait DoctusKey {
  
  /**
   * Defines a function that gets called whenever the key is pressed
   */
  def onKeyPressed(f: (DoctusKeyCode) => Unit): Unit

  /**
   * Defines a function that gets called whenever the key is released
   */
  def onKeyReleased(f: (DoctusKeyCode) => Unit): Unit

}

/**
 * A component that can be used for pointing
 * E.g. a mouse or your Finger on a touch device
 */
trait DoctusPointable {

  /**
   * Defines a function that gets called whenever the pointing starts
   * The point (DoctusPoints) indicates where the pointing was started.
   * E.g. where you started touching a touch device.
   * The position of the point relative to the left top corner of the component.
   */
  def onStart(f: (DoctusPoint) => Unit): Unit

  /**
   * Defines a function that gets called whenever the pointing stops.
   * The point (DoctusPoints) indicates where the pointing was stopped.
   * E.g. the position of your mouse when you release the left button.
   * The position of the point relative to the left top corner of the component.
   */
  def onStop(f: (DoctusPoint) => Unit): Unit
}


trait DoctusDraggable extends DoctusPointable {
  
  def onDrag(f: (DoctusPoint) => Unit): Unit

}


/**
 * Calls a function in regular intervals
 */
trait DoctusScheduler {

  /**
   * Calls the function f every 'duration' milliseconds
   * InitialDelay delays the first call of f for 'initialDelay' milliseconds   
   * Execution can be stopped by calling the stop method of the returned stopper
   */
  def start(f: () => Unit, duration: Int, initialDelay: Int = 0): Stopper
}

object DoctusScheduler {

  trait Stopper {

    // Stops the execution of a Scheduler
    def stop(): Unit
  }
}