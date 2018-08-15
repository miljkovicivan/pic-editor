import javax.imageio.ImageIO
import javax.imageio.IIOException
import java.io.File
import java.awt.image.BufferedImage

import constants.extensions


object image {

  def get_extension(name: String): String = name.split("\\.").reverse(0)
  def get_name(name: String): String = name.split("\\.")(0)
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
  def in_files_folder(name: String): String = "files/" + name

  class Image(path: String, pic: BufferedImage) {
    val _path: String = path
    val _pic: BufferedImage = pic

    def save(name: String): Unit = {
      val _name = get_name(name)
      val _extension = get_extension(name)
      ImageIO.write(_pic, _extension, new File(in_files_folder(name)))
      println("saved")
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

    def save_file(): Unit = {
      if (_instance != null) {

        print("name: ")
        val name: String = scala.io.StdIn.readLine

        val name_valid = validate_name(name)

        if (name_valid) {
          _instance.save(name=name)
        } else {
            println("File name is not valid")
        }
      } else {
        println("File must be loaded first")
      }
    }
  }
}

