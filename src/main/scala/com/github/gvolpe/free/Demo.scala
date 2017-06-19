package com.github.gvolpe.free

import java.nio.file.{Files, Paths}

import cats.effect.IO
import cats.~>
import com.github.gvolpe.free.DiskIO.{Delete, Read, Write}

object Demo extends App {

  def mergeFiles: Free[DiskIO, Unit] = for {
    foo <- Free.liftM(Read("data/foo.txt"))
    bar <- Free.liftM(Read("data/bar.txt"))
    _   <- Free.liftM(Write("data/output.txt", foo ++ bar))
  } yield ()

  val interpreter: DiskIO ~> IO = new (DiskIO ~> IO)  {

    def apply[A](fa: DiskIO[A]): IO[A] = fa match {
      case Read(filename)           => IO { Files.readAllBytes(Paths.get(filename)) }
      case Write(filename, content) => IO { Files.write(Paths.get(filename), content) }.map(_ => ())
      case Delete(filename)         => IO { Files.delete(Paths.get(filename)) }
    }

  }

  def run: IO[Unit] = mergeFiles.foldMap(interpreter)

  run.unsafeRunSync()

}