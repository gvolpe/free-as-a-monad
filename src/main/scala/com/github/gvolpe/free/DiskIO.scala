package com.github.gvolpe.free

sealed trait DiskIO[A]

final case class Read(filename: String) extends DiskIO[Array[Byte]]
final case class Write(filename: String, content: Array[Byte]) extends DiskIO[Unit]
final case class Delete(filename: String) extends DiskIO[Unit]

object DiskIO {
  def read(filename: String): FreeM[DiskIO, Array[Byte]] = FreeM.liftF(Read(filename))
  def write(filename: String, content: Array[Byte]): FreeM[DiskIO, Unit] = FreeM.liftF(Write(filename, content))
  def delete(filename: String): FreeM[DiskIO, Unit] = FreeM.liftF(Delete(filename))
}


