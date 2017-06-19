package com.github.gvolpe.free

object Demo extends App {

  def mergeFiles: Free[DiskIO, Unit] = for {
    foo <- Free.liftM(DiskIO.Read("data/foo.txt"))
    bar <- Free.liftM(DiskIO.Read("data/bar.txt"))
    _   <- Free.liftM(DiskIO.Write("data/output.txt", foo ++ bar))
  } yield ()

  mergeFiles.foldMap(IOInterpreter.apply).unsafeRunSync()

}