package com.github.gvolpe.free.coproduct

import cats.free.{Free, Inject}

import scala.language.higherKinds

sealed trait Disk[A]
final case class Read(filename: String) extends Disk[Array[Byte]]
final case class Write(filename: String, content: Array[Byte]) extends Disk[Unit]

class Disks[F[_]](implicit I: Inject[Disk, F]) {
  def read(filename: String): Free[F, Array[Byte]] = Free.inject[Disk, F](Read(filename))
  def write(filename: String, content: Array[Byte]): Free[F, Unit] = Free.inject[Disk, F](Write(filename, content))
}

object Disks {
  implicit def disks[F[_]](implicit I: Inject[Disk, F]): Disks[F] = new Disks[F]
}