import scala.util.control.Breaks._
import java.lang.NumberFormatException

object utils {

  def read_double(): Double = {
    var input: Double = 0
    breakable {
      while (true) {
        try {
          input = scala.io.StdIn.readLine.toDouble
          break
        } catch {
          case e: NumberFormatException => println("Format is wrong. Try again.")
        }
      }
    }
    input
  }

  def read_int(): Int = {
    var input: Int = 0
    breakable {
      while (true) {
        try {
          input = scala.io.StdIn.readLine.toInt
          break
        } catch {
          case e: NumberFormatException => println("Format is wrong. Try again.")
        }
      }
    }
    input
  }

  class PixelValueException extends Exception {}

  def scale_to_int(value: Double): Int = {
    if (value < 0 || value > 1) {
      throw new PixelValueException()
    }
    val tmp: Double = value * 255
    tmp.toInt
  }

  def scale_to_double(value: Int): Double = {
    if (value < 0 || value > 255) {
      throw new PixelValueException()
    }
    val tmp: Double = value.toDouble / 255
    tmp
  }

  def guarded(x: Double): Double = {
    if (x < 0) {
      0
    } else {
      if (x > 1) {
        1
      } else {
        x
      }
    }
  }

  def get_transparency(value: Int): Int = {
    (value & 0xff000000) / 16777216
  }

  def get_red(value: Int): Int = {
    (value & 0xff0000) / 65536
  }

  def get_green(value: Int): Int = {
    (value & 0xff00) / 256
  }

  def get_blue(value: Int): Int = {
    (value & 0xff)
  }

  def calculate_new_value(current_value: Int, offset: Double, operation: (Double, Double) => Double): Int = {
    val t: Int = get_transparency(current_value)
    val r: Int = get_red(current_value)
    val g: Int = get_green(current_value)
    val b: Int = get_blue(current_value)

    def guard_operate_and_scale(x: Int) = {
      scale_to_int(guarded(operation(scale_to_double(x), offset)))
    }

    // maybe use partial here. that way you don't have to send offset all the way down here
    val new_r: Int = guard_operate_and_scale(r)
    val new_g: Int = guard_operate_and_scale(g)
    val new_b: Int = guard_operate_and_scale(b)

    val new_value = t * 16777216 + new_r * 65536 + new_g * 256 + new_b
    new_value
  }

}
