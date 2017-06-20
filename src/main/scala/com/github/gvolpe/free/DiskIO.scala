package com.github.gvolpe.free

sealed trait DiskIO[A]

final case class Read(filename: String) extends DiskIO[Array[Byte]]
final case class Write(filename: String, content: Array[Byte]) extends DiskIO[Unit]
final case class Delete(filename: String) extends DiskIO[Unit]

object DiskIO {
  def read(filename: String): Free[DiskIO, Array[Byte]] = Free.liftF(Read(filename))
  def write(filename: String, content: Array[Byte]): Free[DiskIO, Unit] = Free.liftF(Write(filename, content))
  def delete(filename: String): Free[DiskIO, Unit] = Free.liftF(Delete(filename))
}


