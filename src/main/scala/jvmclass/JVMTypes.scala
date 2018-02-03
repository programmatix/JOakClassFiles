package jvmclass

object JVMTypes {
  trait JVMType
  sealed trait JVMTypePrimitive extends JVMType

  case class JVMTypeVoid() extends JVMTypePrimitive

  case class JVMTypeBoolean() extends JVMTypePrimitive

  // 32-bit signed two's-complement integers. -2147483648 to 2147483647
  case class JVMTypeInt() extends JVMTypePrimitive

  // 16-bit signed two's-complement integers. -32768 to 32767
  case class JVMTypeShort() extends JVMTypePrimitive

  // 16-bit unsigned integers representing Unicode characters. 0 to 65535
  case class JVMTypeChar() extends JVMTypePrimitive

  // 8-bit signed two's-complement integers. -128 to 127
  case class JVMTypeByte() extends JVMTypePrimitive

  case class JVMTypeFloat() extends JVMTypePrimitive

  case class JVMTypeDouble() extends JVMTypePrimitive

  // 64-bit signed two's-complement integers. -9223372036854775808 to 9223372036854775807
  case class JVMTypeLong() extends JVMTypePrimitive

  // String is not a primitive type but needed to create a valid main
  // Why don't we use char* -> String?  Because it makes the assumption the c code won't do fancy array/pointer stuff with it
  case class JVMTypeString() extends JVMType

  case class JVMTypeArray(typ: JVMType) extends JVMType

  def jvmTypeToClass(typ: JVMType): Class[_] = {
    typ match {
      case _: JVMTypeVoid => classOf[Void]
      case _: JVMTypeBoolean => classOf[Boolean]
      case _: JVMTypeInt => classOf[Int]
      case _: JVMTypeShort => classOf[Short]
      case _: JVMTypeChar => classOf[Char]
      case _: JVMTypeByte => classOf[Byte]
      case _: JVMTypeFloat => classOf[Float]
      case _: JVMTypeDouble => classOf[Double]
      case _: JVMTypeLong => classOf[Long]
      case _: JVMTypeString => classOf[String]
      case v: JVMTypeArray => classOf[Array[_]]
    }
  }
}
