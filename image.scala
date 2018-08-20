import javax.imageio.ImageIO
import javax.imageio.IIOException
import java.io.File
import java.awt.image.BufferedImage
import java.awt.Color
import java.util.NoSuchElementException
import scala.math._
import scala.collection.mutable.HashMap

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

  class Selection(name: String, x1: Int, y1: Int, x2: Int, y2: Int) {
    val _x1 = x1
    val _x2 = x2
    val _y1 = y1
    val _y2 = y2
    val _name = name
    var _active: Boolean = true

    def is_selected(x: Int, y: Int): Boolean = {
      val x_ok = x >= _x1 && x <= x2
      val y_ok = y >= _y1 && y <= y2
      if (x_ok && y_ok) {
        true
      } else {
        false
      }
    }

    def is_active(): Boolean = {
      _active
    }

    def activate() = {
      _active = true
    }

    def deactivate() = {
      _active = false
    }

    override def toString(): String = {
      var active = ""
      if (_active) {
        active = "active"
      } else {
        active = "not active"
      }
      s"${_name} (${active})"
    }

  }


  class Image(path: String, pic: BufferedImage) {

    val _path: String = path
    val _pic: BufferedImage = pic

    val _selections: HashMap[String, Selection] = HashMap()

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

    def fill_selection(name: String, color: Color) = {
      val s = _selections(name)
      val g = _pic.createGraphics()
      if (s.is_active()) {
        g.setColor(color)
        g.fillRect(s._x1, s._y1, s._x2 - s._x1, s._y2 - s._y1)
        g.dispose()
      } else {
        println("Selection is not active")
      }
    }

    def is_selected(x: Int, y: Int): Boolean = {
      var selected: Boolean = false
      if (_selections.count((_) => true) == 0) {
        true
      } else {
        _selections.values.foreach((selection) =>
          if (selection.is_selected(x,y) && selection.is_active()) {
            selected = true
        })
        selected
      }
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

    // File management

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

    // Operations

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

    def inverse(): Unit = {
      _instance.do_operation(1, (x, y) => y - x)
    }

    def gray_out(): Unit = {
      println("Not implemented")
    }

    // Selections

    def _validate_selection(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
      var ret = true
      if (x1 < 0 || y1 < 0 || x2 > _instance._pic.getWidth || y2 > _instance._pic.getHeight) {
        ret = false
      }
      ret
    }

    def create_selection() = {
      print("Name: ")
      val name = scala.io.StdIn.readLine
      print("Upper left X: ")
      val x1 = read_int()
      print("Upper left Y: ")
      val y1 = read_int()
      print("Down right X: ")
      val x2 = read_int()
      print("Down right Y: ")
      val y2 = read_int()

      val validate_s = _validate_selection(x1, y1, x2, y2)
      if (validate_s) {
        val selection = new Selection(name=name, x1=x1, y1=y1, x2=x2, y2=y2)
        _instance._selections(name) = selection
      } else {
        println(s"Your selection is out of image. Image size: ${_instance._pic.getWidth}x${_instance._pic.getHeight}")
      }
    }

    def list_selections() {
      _instance._selections.values.foreach(println)
    }

    def activate_selection() {
      // TODO make getting selection safe NoSuchElementException
      print("Name: ")
      val name = scala.io.StdIn.readLine
      _instance._selections(name).activate()
    }

    def deactivate_selection() {
      // TODO make getting selection safe NoSuchElementException
      print("Name: ")
      val name = scala.io.StdIn.readLine
      _instance._selections(name).deactivate()
    }

    def _choose_color(): Color = {
      println("=== Choose color ===")
      println("1.  Black")
      println("2.  Blue")
      println("3.  Cyan")
      println("4.  Dark Gray")
      println("5.  Gray")
      println("6.  Green")
      println("7.  Light Gray")
      println("8.  Magenta")
      println("9.  Orange")
      println("10. Pink")
      println("11. Red")
      println("12. White")
      println("13. Yellow")
      println("14. Define your color")
      val choice: String = scala.io.StdIn.readLine
      choice match {
        case "1" => Color.BLACK
        case "2" => Color.BLUE
        case "3" => Color.CYAN
        case "4" => Color.DARK_GRAY
        case "5" => Color.GRAY
        case "6" => Color.GREEN
        case "7" => Color.LIGHT_GRAY
        case "8" => Color.MAGENTA
        case "9" => Color.ORANGE
        case "10" => Color.PINK
        case "11" => Color.RED
        case "12" => Color.WHITE
        case "13" => Color.YELLOW
        case "14" => _custom_color()
        case _ => Color.BLACK
      }
    }

    def _custom_color(): Color = {
      print("Enter red component: ")
      val red = scale_to_int(guarded(read_double()))
      print("Enter green component: ")
      val green = scale_to_int(guarded(read_double()))
      print("Enter blue component: ")
      val blue = scale_to_int(guarded(read_double()))
      println(red + " " + green + " " + blue)
      new Color(red, green, blue)
    }

    def fill_selection_with_color() {
      print("Name: ")
      val name = scala.io.StdIn.readLine
      val color = _choose_color()
      _instance.fill_selection(name, color)
    }

    def delete_selection() {
      // TODO make getting selection safe NoSuchElementException
      print("Name: ")
      val name = scala.io.StdIn.readLine
      _instance._selections.remove(name)
    }

  }
}

