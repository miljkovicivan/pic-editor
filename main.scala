import scala.util.control.Breaks._
import scala.collection.mutable.HashMap

import image.Image
import composite_operations.CO
import menus.Menus

object Main {

  def ensure_file_is_loaded(func: => Unit): Unit = {
    if (Image.file_loaded()) {
      func
    } else {
      println("File must be loaded first")
    }
  }

  def main(args: Array[String]): Unit = {
    Menus.print_main_menu()

    breakable {
      while (true) {
        print(">> ")
        val choice: String = scala.io.StdIn.readLine
        choice match {
          case "1" => Image.load_file()
          case "2" => ensure_file_is_loaded(Image.save_file)
          case "3" => println(3)
          case "4" => ensure_file_is_loaded(Menus.print_selection_menu)
          case "5" => ensure_file_is_loaded(Menus.print_operations_menu)
          case "0" => break
          case _ => println("Not valid")
        }
      }
    }
  }
}
