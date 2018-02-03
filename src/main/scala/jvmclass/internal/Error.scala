package jvmclass.internal

package object Error {
  def err(message: String): Unit = {
    println("Error: " + message)
    throw new InternalError(message)
  }
}
