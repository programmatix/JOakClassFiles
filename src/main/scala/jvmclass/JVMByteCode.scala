package jvmclass

import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

import JVMClassFileTypes.JVMClassFileBuilderUtils
;

object JVMByteCode {

  case class GenParams()

  def make(oc: JVMOpCode) = JVMOpCodeWithArgs(oc, Array())


  def makeFloat(oc: JVMOpCode, args: Float*) = JVMOpCodeWithArgs(oc, args.map(v => JVMVarFloat(v)).toArray)

  def makeInt(oc: JVMOpCode, args: Int*) = JVMOpCodeWithArgs(oc, args.map(v => JVMVarInt(v)).toArray)

  def makeDouble(oc: JVMOpCode, args: Double*) = JVMOpCodeWithArgs(oc, args.map(v => JVMVarDouble(v)).toArray)



  case class JVMOpCodeWithArgs(oc: JVMOpCode, args: Array[JVMVar] = Array())  {
    assert(oc.args.length == args.length, s"For opcode ${oc.name} expected args ${oc.args.mkString(",")} but got ${args.mkString(",")}")

    override def toString(): String = gen(GenParams()).trim()

    def gen(implicit params: GenParams): String = {
      oc.name + " " + (
        args.map(arg => {
          //          val argDef = oc.args(idx)
          //          val argActual = args(idx)
          arg.toString
        }).mkString(" ")
        )
    }

    def write(out: ByteArrayOutputStream, charset: Charset, genParams: GenParams): Int = {
      JVMClassFileBuilderUtils.writeByte(out, oc.hexcode)
      for (idx <- args.indices) {
        val argDef = oc.args(idx)
        val argActual = args(idx)
        val v = argActual.asInstanceOf[JVMVarInteger].asInt
        argDef.lengthBytes match {
          case 1 => JVMClassFileBuilderUtils.writeByte(out, v)
          case 2 => JVMClassFileBuilderUtils.writeShort(out, v)
          case 4 => JVMClassFileBuilderUtils.writeInt(out, v)
        }
      }
      oc.lengthInBytes
    }
  }







  trait JVMVar
  sealed trait JVMVarInteger {
    def asInt: Int
  }

  case class JVMVarBoolean(v: Boolean) extends JVMVar

  case class JVMVarInt(v: Int) extends JVMVar with JVMVarInteger {
    def asInt: Int = v
  }

  @Deprecated // currently using JVMVarInt to avoid weirdness
  case class JVMVarShort(v: Short) extends JVMVar with JVMVarInteger {
    def asInt: Int = v
  }

  case class JVMVarChar(v: Char) extends JVMVar

  @Deprecated // currently using JVMVarInt to avoid weirdness
  case class JVMVarByte(v: Byte) extends JVMVar with JVMVarInteger {
    def asInt: Int = v
  }

  case class JVMVarFloat(v: Float) extends JVMVar

  case class JVMVarDouble(v: Double) extends JVMVar

  case class JVMVarLong(v: Long) extends JVMVar

  case class JVMVarString(v: String) extends JVMVar


  //  case class DefineFunction(name: Identifier, types: Types, passedVariables: Seq[DeclareVariable]) extends Command
//  case class DeclareVariable(name: Identifier, typ: JVMType) extends Command

//  case class DeclareVariable(name: String, typ: JVMType)

  //  case class StoreExpressionInCurrentVar() extends Command

//  case class JVMGenUnsupportedCurrently(err: String) extends RuntimeException(s"Operation is not currently supported: ${err}")

//  case class JVMInterimBadState(err: String) extends RuntimeException(err)

}
