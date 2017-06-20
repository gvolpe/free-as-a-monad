package com.github.gvolpe.free.coproduct

import cats.effect.IO
import cats.~>

object ActionIOInterpreter extends (Action ~> IO){

  def apply[A](fa: Action[A]): IO[A] = fa match {
    case UppercaseData(key) => IO { key.toUpperCase }
    case LowercaseData(key) => IO { key.toLowerCase }
  }

}