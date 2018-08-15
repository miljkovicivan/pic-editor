import javax.imageio.ImageIO
import javax.imageio.IIOException
import java.io.File
import java.awt.image.BufferedImage
import scala.math._

import constants._
import utils._


object image {

  def get_extension(name: String): String = name.split("\\.").reverse(0)

  def get_name(name: String): String = name.split("\\.")(0)

  def in_files_folder(name: String): String = "files/" + name

  def validate_name(name: String): Boolean = {
    val n = get_name(name)
    val ext = get_extension(name)

    val valid_ext = extensions contains ext
    val name_not_null = n.length > 0

    if (valid_ext && name_not_null) {
      true
    } else {
      println("Name not valid")
      false
    }
  }


  class Image(path: String, pic: BufferedImage) {

    val _path: String = path
    val _pic: BufferedImage = pic

    def save(name: String): Unit = {
      val _name = get_name(name)
      val _extension = get_extension(name)
      ImageIO.write(_pic, _extension, new File(in_files_folder(name)))
      println("saved")
    }

    def get_selected_layer(): BufferedImage = {
      // TODO: NOT IMPLEMENTED
      _pic
    }

    def is_selected(x: Int, y: Int): Boolean = {
      true
    }

    def do_operation(offset: Double, operation: (Double, Double) => Double) = {
      val layer = get_selected_layer()
      val w = _pic.getWidth
      val h = _pic.getHeight
      for (x <- 0 until w)
        for (y <- 0 until h)
          if (is_selected(x, y)) {
            val current_value = layer.getRGB(x,y)
            val new_value = calculate_new_value(current_value, offset, operation)
            //println(s"${current_value} - ${new_value}")
            layer.setRGB(x, y, new_value)
          }
    }

    override def toString(): String = _path;
  }


  object Image {

    var _instance: Image = null

    def load_file(): Unit = {
      try {
        if (_instance == null) {

          print("name: ")
          val path: String = scala.io.StdIn.readLine

          val name_valid = validate_name(path)

          if (name_valid) {
            val pic = ImageIO.read(new File(in_files_folder(path)))
            _instance = new Image(path=path, pic=pic)
          } else {
            println("File name is not valid")
          }

        }
        else {
          println("Image already loaded")
        }
      } catch {
        case e: IIOException => println("file not found")
      }
    }

    def file_loaded(): Boolean = {
      if (_instance == null) {
        false
      } else {
        true
      }
    }

    def save_file(): Unit = {
      print("name: ")
      val name: String = scala.io.StdIn.readLine

      val name_valid = validate_name(name)

      if (name_valid) {
        _instance.save(name=name)
      } else {
          println("File name is not valid")
      }
    }

    def add(): Unit = {
      print("X = ")
      val input: Double = read_double()
      _instance.do_operation(input, (x, y) => x + y)
    }

    def sub(): Unit = {
      print("X = ")
      val input: Double = read_double()
      _instance.do_operation(input, (x, y) => x - y)
    }

    def sub_inverse(): Unit = {
      print("X = ")
      val input: Double = read_double()
      _instance.do_operation(input, (x, y) => y - x)
    }

    def mul(): Unit = {
      print("X = ")
      val input: Double = read_double()
      _instance.do_operation(input, (x, y) => x * y)
    }

    def div(): Unit = {
      print("X = ")
      val input: Double = read_double()
      _instance.do_operation(input, (x, y) => x/y)
    }

    def div_inverse(): Unit = {
      print("X = ")
      val input: Double = read_double()
      _instance.do_operation(input, (x, y) => y/x)
    }

    def power(): Unit = {
      print("X = ")
      val input: Double = read_double()
      _instance.do_operation(input, pow)
    }

    def log_1p(): Unit = {
      def log_mask_arg(x: Double, y: Double): Double = {
        log1p(x)
      }
      val dummy: Double = 1 // first argumet is not used here. 1 is dummy
      _instance.do_operation(1, log_mask_arg)
    }

    def abs_f(): Unit = {
      def abs_mask_arg(x: Double, y: Double): Double = {
        abs(x)
      }
      val dummy: Double = 1 // first argumet is not used here. 1 is dummy
      _instance.do_operation(1, abs_mask_arg)
    }

    def min_f(): Unit = {
      print("X = ")
      val input: Double = read_double()
      _instance.do_operation(input, min)
    }

    def max_f(): Unit = {
      print("X = ")
      val input: Double = read_double()
      _instance.do_operation(input, max)
    }

  }
}

