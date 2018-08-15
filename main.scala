import scala.util.control.Breaks._

import file_reader.ispisi
import image.Image

object Main {

  def print_menu(): Unit = {
    println("1. Load file")
    println("2. Save file")
    println("3. Layers")
    println("4. Selections")
    println("5. Operations")
    println("6. Quit")
  }

  def ensure_file_is_loaded(func: => Unit): Unit = {
    if (Image.file_loaded()) {
      func
    } else {
      println("File must be loaded first")
    }
  }

  def print_operations_menu(): Unit = {
    println("===  Operations  ===")
    println("1. current_value + X")
    println("2. Go to main menu")
    print(">> ")
    val choice: String = scala.io.StdIn.readLine
    choice match {
      case "1" => Image.add()
      case _ =>
    }
  }

  def load_file(): Unit = {
    Image.load_file()
  }

  def save_file(): Unit = {
    Image.save_file()
  }

  def main(args: Array[String]): Unit = {
    print_menu()

    breakable {
      while (true) {
        print(">> ")
        val choice: String = scala.io.StdIn.readLine
        choice match {
          case "1" => load_file()
          case "2" => ensure_file_is_loaded(save_file)
          case "3" => println(3)
          case "4" => println(4)
          case "5" => ensure_file_is_loaded(print_operations_menu)
          case "6" => break
          case _ => println("Not valid")
        }
      }
    }
  }
}
