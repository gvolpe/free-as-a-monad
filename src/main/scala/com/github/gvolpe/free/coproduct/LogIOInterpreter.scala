package com.github.gvolpe.free.coproduct

import cats.effect.IO
import cats.~>

object LogIOInterpreter extends (Log ~> IO) {

  def apply[A](fa: Log[A]) = fa match {
    case Info(msg)      => IO { println(s"[Info] $msg") }
    case Warning(msg)   => IO { println(s"[Warning] $msg") }
    case Error(msg, t)  => IO { println(s"[Error] $msg. Cause: $t") }
  }

}
