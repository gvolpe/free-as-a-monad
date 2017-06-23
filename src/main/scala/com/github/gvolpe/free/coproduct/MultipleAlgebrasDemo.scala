package com.github.gvolpe.free.coproduct

import cats.data.Coproduct
import cats.effect.IO
import cats.free.Free
import cats.~>

import scala.language.higherKinds

object MultipleAlgebrasDemo extends App {

  type ActionDisk[A]  = Coproduct[Action, Disk, A]
  type Program[A]     = Coproduct[Log, ActionDisk, A]

  import Actions._, Disks._, Logs._

  // Mixing three different algebras using Inject
  def mergeFiles(implicit A: Actions[Program], D: Disks[Program], L: Logs[Program]): Free[Program, Unit] =
    for {
      _   <- L.info("Reading files: data/foo.txt and data/bar.txt")
      foo <- D.read("data/foo.txt")
      bar <- D.read("data/bar.txt")
      _   <- L.warning("Uppercasing file contents")
      enc <- A.uppercaseData(new String(foo ++ bar, "UTF-8"))
      _   <- L.info("Writing result to data/output.txt")
      _   <- D.write("data/output.txt", enc.getBytes("UTF-8"))
    } yield ()

  val actionDiskInterpreter: ActionDisk ~> IO = ActionIOInterpreter or DiskIOInterpreter
  val interpreter: Program ~> IO = LogIOInterpreter or actionDiskInterpreter

  mergeFiles.foldMap(interpreter).unsafeRunSync()

}
