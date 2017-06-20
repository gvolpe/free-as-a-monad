package com.github.gvolpe.free.coproduct

import cats.free.{Free, Inject}

import scala.language.higherKinds

sealed trait Action[A]
final case class UppercaseData(key: String) extends Action[String]
final case class LowercaseData(key: String) extends Action[String]

class Actions[F[_]](implicit I: Inject[Action, F]) {
  def uppercaseData(key: String): Free[F, String] = Free.inject[Action, F](UppercaseData(key))
  def lowercaseData(key: String): Free[F, String] = Free.inject[Action, F](LowercaseData(key))
}

object Actions {
  implicit def actions[F[_]](implicit I: Inject[Action, F]): Actions[F] = new Actions[F]
}