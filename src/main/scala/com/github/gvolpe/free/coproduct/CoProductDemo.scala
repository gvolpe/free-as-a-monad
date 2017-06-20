package com.github.gvolpe.free.coproduct

import cats.data.Coproduct
import cats.effect.IO
import cats.free.Free
import cats.~>

import scala.language.higherKinds

object CoProductDemo extends App {

  type ActionOrDisk[A] = Coproduct[Action, Disk, A]

  import Actions._, Disks._

  // Mixing tho different algebras using Inject
  def mergeFiles(implicit A: Actions[ActionOrDisk], D: Disks[ActionOrDisk]): Free[ActionOrDisk, Unit] =
    for {
      foo <- D.read("data/foo.txt")
      bar <- D.read("data/bar.txt")
      enc <- A.uppercaseData(new String(foo ++ bar, "UTF-8"))
      _   <- D.write("data/output.txt", enc.getBytes("UTF-8"))
    } yield ()

  val interpreter: ActionOrDisk ~> IO = ActionIOInterpreter or DiskIOInterpreter

  mergeFiles.foldMap(interpreter).unsafeRunSync()

}
