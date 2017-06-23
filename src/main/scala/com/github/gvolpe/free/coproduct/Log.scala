package com.github.gvolpe.free.coproduct

import cats.free.{Free, Inject}

import scala.language.higherKinds

sealed trait Log[A]

final case class Info(msg: String) extends Log[Unit]
final case class Warning(msg: String) extends Log[Unit]
final case class Error(msg: String, throwable: Option[Throwable]) extends Log[Unit]

class Logs[F[_]](implicit I: Inject[Log, F]) {
  def info(msg: String): Free[F, Unit] = Free.inject[Log, F](Info(msg))
  def warning(msg: String): Free[F, Unit] = Free.inject[Log, F](Warning(msg))
  def error(msg: String, throwable: Option[Throwable]): Free[F, Unit] = Free.inject[Log, F](Error(msg, throwable))
}

object Logs {
  implicit def logs[F[_]](implicit I: Inject[Log, F]): Logs[F] = new Logs[F]
}