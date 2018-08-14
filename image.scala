

object image {
  class Image(path: String) {
    val _path = path

    def save(name: String): Unit = {
      if (name != null) {
        // save by its original name
        println(name)
      } else {
        // save it by new name
        println(_path)
      }
    }

    override def toString(): String = _path;
  }


  object Image {

    var _instance: Image = null

    def load_file(path: String): Unit = {
      if (_instance == null) {
        _instance = new Image(path)
      }
    }

    def save_file(name: String): Unit = {
      if (_instance != null) {
        _instance.save(name=name)
      } else {
        println("File must be loaded first")
      }
    }
  }
}

