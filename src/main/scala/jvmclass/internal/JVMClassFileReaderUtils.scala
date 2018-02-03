package jvmclass.internal

import java.io.DataInputStream

// Java doesn't support unsigned bytes
object JVMClassFileReaderUtils {
  // Quick two's complement refresher:
  // Way of representing integers in binary.  For a 4 bit number:
  // 0000 = 0, 0001 = 1, 0010 = 2 etc.
  // 1111 = -1, 1110 = -2, 1101 = -3
  // Avoids using a sign bit, which also avoids pain of having two zeros - 0000 (+0) and 1000 (-0)
  // Makes the maths easier:
  // E.g. on a 4bit number,  2 (0010) and -2 (1110) = 10000, but the top bit is overflow, so it leaves 0 : -2+2=0
  // For an 8 bit number, twos complement is (2^8 - number)
  // Negating a number (reversing the sign) is performed by taking its twos complement


  def extendByteAsTwosComplement(in: Int): Int = {
    val topBitSet = (in & 0x80) != 0
    if (topBitSet) {
      val out = 0xffffff00 | in
      out
    }
    else in
  }

  def extendShortAsTwosComplement(in: Int): Int = {
    val topBitSet = (in & 0x8000) != 0
    if (topBitSet) {
      val out = 0xffff0000 | in
      out
    }
    else in
  }

  def extendIntAsTwosComplement(in: Int): Long = {
    // JVM does this for us - entirely possible that all code here is redundant!
    in.toLong
    //    val topBitSet = (in & 0x80000000) != 0
    //    if (topBitSet) {
    //      val compare = 0xffffffffffffffffL
    //      val out = compare | in
    //      out
    //    }
    //    else in
  }

  // Java and bytes:
  // Integer.parseInt("11110111", 2) == 247, not -9, e.g. it doesn't do the twos complement.  Use extendByteAsTwosComplement
  // Raw file is 0xffff -> in.read() is 255

  def readByteUnsigned(in: DataInputStream): Int = {
    // Returns 0-255, or -1 if end of stream reached
    val rawByte = in.read()
    rawByte
  }

  def readByteTwosComplement(in: DataInputStream): Int = {
    // Returns 0-255, or -1 if end of stream reached
    val rawByte = in.read()
    extendByteAsTwosComplement(rawByte)
  }

  def readShortTwosComplement(in: DataInputStream): Int = {
    extendShortAsTwosComplement(readByteTwosComplement(in) << 8) | readByteTwosComplement(in)
  }

  def readIntTwosComplement(in: DataInputStream): Int = {
    (readByteUnsigned(in) << 24) | (readByteUnsigned(in) << 16) | (readByteUnsigned(in) << 8) | readByteUnsigned(in)
  }

  def readFloatTwosComplement(in: DataInputStream): Float = {
    (readByteUnsigned(in) << 24) | (readByteUnsigned(in) << 16) | (readByteUnsigned(in) << 8) | readByteUnsigned(in)
  }

  def readDoubleTwosComplement(in: DataInputStream): Double = {
    (readByteUnsigned(in) << 56) | (readByteUnsigned(in) << 48) | (readByteUnsigned(in) << 40) | (readByteUnsigned(in) << 32) | (readByteUnsigned(in) << 24) | (readByteUnsigned(in) << 16) | (readByteUnsigned(in) << 8) | readByteUnsigned(in)
  }

  def readLongTwosComplements(in: DataInputStream): Long = {
    (readByteUnsigned(in) << 56) | (readByteUnsigned(in) << 48) | (readByteUnsigned(in) << 40) | (readByteUnsigned(in) << 32) | (readByteUnsigned(in) << 24) | (readByteUnsigned(in) << 16) | (readByteUnsigned(in) << 8) | readByteUnsigned(in)
  }


}
