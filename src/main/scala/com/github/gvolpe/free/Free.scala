package com.github.gvolpe.free

import cats.Monad
import com.github.gvolpe.free.Free._

import scala.language.higherKinds

// Or use cats.free.Free
sealed trait Free[F[_], A] {
  def flatMap[B](f: A => Free[F, B]): Free[F, B] = Bind(this, f)

  def map[B](f: A => B): Free[F, B] = flatMap(a => Pure(f(a)))

  def foldMap[G[_] : Monad](nt: F ~> G): G[A] = this match {
    case Pure(a)          => Monad[G].pure(a)
    case Suspend(fa)      => nt(fa)
    case Bind(target, f)  => Monad[G].flatMap(target.foldMap(nt)) { e =>
      f(e).foldMap(nt)
    }
  }
}

// Or just use cats.~>
trait ~>[F[_], G[_]] {
  def apply[A](fa: F[A]): G[A]
}

object Free {
  def pure[F[_], A](a: A): Free[F, A] = Pure(a)

  def liftF[F[_], A](fa: F[A]): Free[F, A] = Suspend(fa)

  final case class Pure[F[_], A](a: A) extends Free[F, A]
  final case class Suspend[F[_], A](fa: F[A]) extends Free[F, A]
  final case class Bind[F[_], E, A](target: Free[F, E], f: E => Free[F, A]) extends Free[F, A]
}
