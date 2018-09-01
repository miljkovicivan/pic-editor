import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.math._

import utils._
import image.Image


object composite_operations {

  class CompositeFunction {
  }

  object CO {
    var _functions: HashMap[String, ListBuffer[(Double, Double) => Double]] = HashMap()
    var _params: HashMap[String, ListBuffer[Double]] = HashMap()

    def list_composite_operations(): Unit = {
      _functions.keys.foreach{println}
    }

    def get_operation_name(): String = {
      print("Name: ")
      val name: String = scala.io.StdIn.readLine
      // TODO check if name exists
      name
    }

    def create_composite_operations(): Unit = {
      val name = get_operation_name()

      _functions(name) = new ListBuffer[(Double, Double) => Double]()
      _params(name) = new ListBuffer[Double]()
    }
    
    def add(name: String): Unit = {
      print("X = ")
      val input: Double = read_double()
      val f = (x: Double, y: Double) => x + y
      _params(name) += input
      _functions(name) += f
    }

    def sub(name: String): Unit = {
      print("X = ")
      val input: Double = read_double()
      val f = (x: Double, y: Double) => x - y
      _params(name) += input
      _functions(name) += f
    }

    def sub_inverse(name: String): Unit = {
      print("X = ")
      val input: Double = read_double()
      val f = (x: Double, y: Double) => y - x
      _params(name) += input
      _functions(name) += f
    }

    def mul(name: String): Unit = {
      print("X = ")
      val input: Double = read_double()
      val f = (x: Double, y: Double) => x * y
      _params(name) += input
      _functions(name) += f
    }

    def div(name: String): Unit = {
      print("X = ")
      val input: Double = read_double()
      val f = (x: Double, y: Double) => x / y
      _params(name) += input
      _functions(name) += f
    }

    def div_inverse(name: String): Unit = {
      print("X = ")
      val input: Double = read_double()
      val f = (x: Double, y: Double) => y / x
      _params(name) += input
      _functions(name) += f
    }

    def power(name: String): Unit = {
      print("X = ")
      val input: Double = read_double()
      _params(name) += input
      _functions(name) += pow
    }

    def log_1p(name: String): Unit = {
      def log_mask_arg(x: Double, y: Double): Double = {
        log1p(x)
      }
      _params(name) += 1 // dummy
      _functions(name) += log_mask_arg
    }

    def abs_f(name: String): Unit = {
      def abs_mask_arg(x: Double, y: Double): Double = {
        abs(x)
      }
      _params(name) += 1 // dummy
      _functions(name) += abs_mask_arg
    }

    def min_f(name: String): Unit = {
      print("X = ")
      val input: Double = read_double()
      _params(name) += input
      _functions(name) += min
    }

    def max_f(name: String): Unit = {
      print("X = ")
      val input: Double = read_double()
      _params(name) += input
      _functions(name) += max
    }

    def inverse(name: String): Unit = {
      val f = (x: Double, y: Double) => y - x
      _params(name) += 1
      _functions(name) += f
    }

    def gray_out(name: String): Unit = {
      println("Not implemented")
    }


    def add_operation(): Unit = {
      val name = get_operation_name()

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
        case "1" => add(name)
        case "2" => sub(name)
        case "3" => sub_inverse(name)
        case "4" => mul(name)
        case "5" => div(name)
        case "6" => div_inverse(name)
        case "7" => power(name)
        case "8" => log_1p(name)
        case "9" => abs_f(name)
        case "10" => min_f(name)
        case "11" => max_f(name)
        case "12" => inverse(name)
        case "13" => gray_out(name)
        case _ =>
      }

    }

    def apply_composite_operaition(): Unit = {
      val name = get_operation_name()
      val fs = _functions(name)
      val ps = _params(name)

      for (x <- 0 to fs.length - 1) {
        val f = fs(x)
        val p = ps(x)
        Image._instance.do_operation(p, f)
      }
    }
  }

}
