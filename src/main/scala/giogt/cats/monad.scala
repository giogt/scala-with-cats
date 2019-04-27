package giogt.cats

object monad {
  import cats.Id

  import cats.syntax.either._
  import cats.syntax.option._

  trait Monad[F[_]] {
    def pure[A](a: A): F[A]
    def flatMap[A, B](value: F[A])(f: A => F[B]): F[B]
    def map[A, B](value: F[A])(f: A => B): F[B] =
      flatMap(value) { a =>
        pure(f(a))
      }
  }

  object instances {
    implicit val optionMonad: Monad[Option] =
      new Monad[Option] {
        def pure[A](a: A): Option[A] = a.some
        def flatMap[A, B](value: Option[A])(f: A => Option[B]): Option[B] =
          value match {
            case Some(v) => f(v)
            case None    => none
          }
      }

    implicit val eitherMonad: Monad[Either[Throwable, ?]] =
      new Monad[Either[Throwable, ?]] {
        def pure[A](a: A): Either[Throwable, A] = a.asRight
        def flatMap[A, B](value: Either[Throwable, A])(f: A => Either[Throwable, B]): Either[Throwable, B] =
          value match {
            case Right(v) => f(v)
            case Left(e)  => e.asLeft
          }
      }

    implicit val idMonad: Monad[Id] =
      new Monad[Id] {
        def pure[A](a: A): Id[A]                               = a
        def flatMap[A, B](value: Id[A])(f: A => Id[B]): Id[B]  = f(value)
        override def map[A, B](value: Id[A])(f: A => B): Id[B] = f(value)
      }
  }

  object syntax {
    implicit class MonadOps[F[_], A](value: F[A])(implicit m: Monad[F]) {
      def flatMap_[B](f: A => F[B]) = m.flatMap(value)(f)
      def map_[B](f: A => B)        = m.map(value)(f)
    }
  }
}
