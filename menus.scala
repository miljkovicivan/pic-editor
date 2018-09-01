import image.Image
import composite_operations.CO


object menus {
  object Menus{

    def print_main_menu(): Unit = {
      println("===  Main menu  ===")
      println("1. Load file")
      println("2. Save file")
      println("3. Layers")
      println("4. Selections")
      println("5. Operations")
      println("0. Quit")
    }

    def print_selection_menu(): Unit = {
      println("===  Selections ===")
      println("1. Create selection")
      println("2. List selections")
      println("3. Activate selection")
      println("4. Deactivate selection")
      println("5. Fill selection with color")
      println("6. Delete selection")
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
      println("14. Composite operations")
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
        case "14" => _print_composite_operations_menu()
        case _ =>
      }
      print_main_menu()
    }

    def _print_composite_operations_menu(): Unit = {
      println("=== Composite operations ===")
      println("1. List composite operations")
      println("2. Create composite operation")
      println("3. Add operation")
      println("4. Apply composite operation")
      print(">> ")
      val choice: String = scala.io.StdIn.readLine
      choice match {
        case "1" => CO.list_composite_operations()
        case "2" => CO.create_composite_operations()
        case "3" => CO.add_operation()
        case "4" => CO.apply_composite_operaition()
        case _ =>
      }
    }

  }
}
