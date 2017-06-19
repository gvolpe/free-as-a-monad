package com.github.gvolpe.free

import java.nio.file.{Files, Paths}

import cats.effect.IO
import com.github.gvolpe.free.DiskIO.{Delete, Read, Write}

object IOInterpreter {
  def apply: DiskIO ~> IO = new (DiskIO ~> IO)  {

    def apply[A](fa: DiskIO[A]): IO[A] = fa match {
      case Read(filename)           => IO { Files.readAllBytes(Paths.get(filename)) }
      case Write(filename, content) => IO { Files.write(Paths.get(filename), content) }.map(_ => ())
      case Delete(filename)         => IO { Files.delete(Paths.get(filename)) }
    }

  }
}
