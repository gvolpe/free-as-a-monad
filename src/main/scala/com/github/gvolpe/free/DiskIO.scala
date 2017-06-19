package com.github.gvolpe.free

sealed trait DiskIO[A]

object DiskIO {
  final case class Read(filename: String) extends DiskIO[Array[Byte]]
  final case class Write(filename: String, content: Array[Byte]) extends DiskIO[Unit]
  final case class Delete(filename: String) extends DiskIO[Unit]
}


