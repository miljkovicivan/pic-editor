import scala.util.control.Breaks._

import file_reader.ispisi
import image.Image

object Main {

  def print_menu(): Unit = {
    println("1. Load file")
    println("2. Save file")
    println("3. Save & quit")
    println("4. Quit")
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
          case "2" => save_file()
          case "3" => println(3)
          case "4" => break
          case _ => println("Not valid")
        }
      }
    }
  }
}
