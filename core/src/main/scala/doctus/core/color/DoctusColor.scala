package doctus.core.color

import doctus.core.DoctusColor

/**
 * The color black
 */
case object DoctusColorBlack extends DoctusColor {
  private val colors = (0, 0, 0)
  def apply = DoctusColorRgb(colors._1, colors._2, colors._3)
  def rgb = colors
}

/**
 * The color white
 */
case object DoctusColorWhite extends DoctusColor {
  private val colors = (255, 255, 255)
  def apply = DoctusColorRgb(colors._1, colors._2, colors._3)
  def rgb = colors
}

/**
 * The color red
 */
case object DoctusColorRed extends DoctusColor {
  private val colors = (255, 0, 0)
  def apply = DoctusColorRgb(colors._1, colors._2, colors._3)
  def rgb = colors
}

/**
 * The color green
 */
case object DoctusColorGreen extends DoctusColor {
  private val colors = (0, 255, 0)
  def apply = DoctusColorRgb(colors._1, colors._2, colors._3)
  def rgb = colors
}

/**
 * The color blue
 */
case object DoctusColorBlue extends DoctusColor {
  private val colors = (0, 0, 255)
  def apply = DoctusColorRgb(colors._1, colors._2, colors._3)
  def rgb = colors
}

/**
 * The color yellow
 */
case object DoctusColorYellow extends DoctusColor {
  private val colors = (255, 255, 0)
  def apply = DoctusColorRgb(colors._1, colors._2, colors._3)
  def rgb = colors
}
/**
 * The color magenta
 */
case object DoctusColorMangenta extends DoctusColor {
  private val colors = (255, 0, 255)
  def apply = DoctusColorRgb(colors._1, colors._2, colors._3)
  def rgb = colors
}

/**
 * The color turquoise
 */
case object DoctusColorTurquois extends DoctusColor {
  private val colors = (0, 255, 255)
  def apply = DoctusColorRgb(colors._1, colors._2, colors._3)
  def rgb = colors
}

/**
 * The color orange
 */
case object DoctusColorOrange extends DoctusColor {
  private val colors = (255, 168, 0)
  def apply = DoctusColorRgb(colors._1, colors._2, colors._3)
  def rgb = colors
}
/**
 * Values of red, green and blue range from 0 to 255
 */
case class DoctusColorRgb(red: Int, green: Int, blue: Int) extends DoctusColor {
  def rgb = (red, green, blue)
}

/**
 * Utility functions for colors
 */
object DoctusColorUtil {
  
    /**
   * Converts HSV (Hue, Saturation, Brightness) into RGB (Red, Green, Blue)
   * Ranges:
   * hue:       [0, 360]
   * saturation: [0, 99]
   * value:     [0, 99]
   *
   * red:       [0, 255]
   * green:     [0, 255]
   * blue:      [0, 255]
   *
   * Originally from: http://henkelmann.eu/
   */
  def hsv2rgb(hue: Int, saturation: Int, value: Int): (Int, Int, Int) = {
    val h = if (hue < 0) ((hue % 360) + 360).toDouble else (hue % 360).toDouble
    val s = adj(saturation, 0, 99) / 99.0
    val v = adj(value, 0, 99) / 99.0
    val c = s * v
    val h1 = h / 60.0
    val x = c * (1.0 - ((h1 % 2) - 1.0).abs)
    val (r, g, b) = if (h1 < 1.0) (c, x, 0.0)
    else if (h1 < 2.0) (x, c, 0.0)
    else if (h1 < 3.0) (0.0, c, x)
    else if (h1 < 4.0) (0.0, x, c)
    else if (h1 < 5.0) (x, 0.0, c)
    else (c, 0.0, x)
    val m = v - c
    (((r + m) * 255).toInt, ((g + m) * 255).toInt, ((b + m) * 255).toInt)
  }

  /**
   * Converts RGB (Red, Green, Blue) into HSV (Hue, Saturation, Brightness)
   * Ranges:
   * hue:        [0, 360]
   * saturation: [0, 100]
   * value:      [0, 100]
   *
   * red:        [0, 255]
   * green:      [0, 255]
   * blue:       [0, 255]
   *
   * Originally from: http://henkelmann.eu/
   */
  def rgb2hsv(red: Int, green: Int, blue: Int): (Int, Int, Int) = {
    val r = adj(red, 0, 255) / 255.0
    val g = adj(green, 0, 255) / 255.0
    val b = adj(blue, 0, 255) / 255.0
    val max = (r max g) max b
    val min = (r min g) min b
    val c = max - min
    val h1 =
      if (c == 0.0) 0.0 //we have some grey tone
      else if (max == r) ((g - b) / c) % 6
      else if (max == g) ((b - r) / c) + 2.0
      else ((r - g) / c) + 4.0
    val h = h1 * 60.0
    val v = max
    val s =
      if (c == 0.0 || v == 0.0) 0.0
      else c / v
    (h.toInt, (s * 100).toInt, (v * 100).toInt)
  }

  private def adj(value: Int, min: Int, max: Int): Int = {
    require(min <= max)
    if (value < min) min
    else if (value > max) max
    else value
  }
  
}

