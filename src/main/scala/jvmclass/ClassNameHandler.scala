package jvmclass

object ClassNameHandler {
  /**
    * Returns a Java name like "com.example.MyClass" and splits it up into ("MyClass", Some("com.example"))
    * Also will turn "com/example/MyClass.class" into ("MyClass", Some("com.example"))
    */
  def split(nameRaw: String): (String, Option[String]) = {
    val name = nameRaw.stripSuffix(".class")

    val (clsName: String, packageName: Option[String]) = if (name.contains(".")) {
      val splits = name.split("\\.")
      (splits.last, Some(splits.take(splits.length - 1).mkString(".")))
    }
    else if (name.contains("/")) {
      val splits = name.split("/")
      (splits.last, Some(splits.take(splits.length - 1).mkString(".")))
    }
    else {
      (name, None)
    }

    (clsName, packageName)
  }
}
