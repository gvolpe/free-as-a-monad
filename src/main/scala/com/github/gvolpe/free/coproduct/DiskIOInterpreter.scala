package com.github.gvolpe.free.coproduct

import java.nio.file.{Files, Paths}

import cats.effect.IO
import cats.~>

object DiskIOInterpreter extends (Disk ~> IO){

  def apply[A](fa: Disk[A]): IO[A] = fa match {
    case Read(filename)           => IO { Files.readAllBytes(Paths.get(filename)) }
    case Write(filename, content) => IO { Files.write(Paths.get(filename), content); () }
  }

}