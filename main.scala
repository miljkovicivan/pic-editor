import scala.util.control.Breaks._

import image.Image

object Main {

  def print_main_menu(): Unit = {
    println("===  Main menu  ===")
    println("1. Load file")
    println("2. Save file")
    println("3. Layers")
    println("4. Selections")
    println("5. Operations")
    println("0. Quit")
  }

  def ensure_file_is_loaded(func: => Unit): Unit = {
    if (Image.file_loaded()) {
      func
    } else {
      println("File must be loaded first")
    }
  }

  def print_selection_menu(): Unit = {
    println("===  Selections ===")
    println("1. Create selection")
    println("2. List selections")
    println("3. Activate selection")
    println("4. Deactivate selection")
    println("5. Fill selection with color")
    println("6. Delete selection")
    println("7. Fill color")
    println("0. Go to main menu")
    print(">> ")
    val choice: String = scala.io.StdIn.readLine
    choice match {
      case "1" => Image.create_selection()
      case "2" => Image.list_selections()
      case "3" => Image.activate_selection()
      case "4" => Image.deactivate_selection()
      case "5" => Image.fill_selection_with_color()
      case "6" => Image.delete_selection()
      case "7" => Image.fill_selection_with_color()
      case _ =>
    }
    print_main_menu()
  }

  def print_operations_menu(): Unit = {
    println("===  Operations  ===")
    println("1. current_value + X")
    println("2. current_value - X")
    println("3. X - current_value")
    println("4. current_value * X")
    println("5. current_value / X")
    println("6. X / current_value")
    println("7. current_value ^ X")
    println("8. log1p(current_value)")
    println("9. abs(current_value)")
    println("10. min(current_value, X)")
    println("11. max(current_value, X)")
    println("12. inverse")
    println("13. gray out")
    println("0. Go to main menu")
    print(">> ")
    val choice: String = scala.io.StdIn.readLine
    choice match {
      case "1" => Image.add()
      case "2" => Image.sub()
      case "3" => Image.sub_inverse()
      case "4" => Image.mul()
      case "5" => Image.div()
      case "6" => Image.div_inverse()
      case "7" => Image.power()
      case "8" => Image.log_1p()
      case "9" => Image.abs_f()
      case "10" => Image.min_f()
      case "11" => Image.max_f()
      case "12" => Image.inverse()
      case "13" => Image.gray_out()
      case _ =>
    }
    print_main_menu()
  }

  def load_file(): Unit = {
    Image.load_file()
  }

  def save_file(): Unit = {
    Image.save_file()
  }

  def main(args: Array[String]): Unit = {
    print_main_menu()

    breakable {
      while (true) {
        print(">> ")
        val choice: String = scala.io.StdIn.readLine
        choice match {
          case "1" => load_file()
          case "2" => ensure_file_is_loaded(save_file)
          case "3" => println(3)
          case "4" => ensure_file_is_loaded(print_selection_menu)
          case "5" => ensure_file_is_loaded(print_operations_menu)
          case "0" => break
          case _ => println("Not valid")
        }
      }
    }
  }
}
