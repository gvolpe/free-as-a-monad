package com.github.gvolpe.free

object Demo extends App {

  def mergeFiles: Free[DiskIO, Unit] = for {
    foo <- DiskIO.read("data/foo.txt")
    bar <- DiskIO.read("data/bar.txt")
    _   <- DiskIO.write("data/output.txt", foo ++ bar)
  } yield ()

  mergeFiles.foldMap(IOInterpreter.apply).unsafeRunSync()

}