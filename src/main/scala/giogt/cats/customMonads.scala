package giogt.cats

object customMonads {

  import cats.Monad
  import scala.annotation.tailrec

  object tree {
    sealed trait Tree[+A]
    final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
    final case class Leaf[A](value: A)                        extends Tree[A]

    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)
    def leaf[A](value: A): Tree[A]                        = Leaf(value)
  }

  object instances {
    import giogt.cats.customMonads.tree.{Branch, Leaf, Tree}

    implicit val optionMonad = new Monad[Option] {
      def flatMap[A, B](opt: Option[A])(f: A => Option[B]): Option[B] =
        opt flatMap f

      def pure[A](opt: A): Option[A] =
        Some(opt)

      @tailrec
      def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] =
        f(a) match {
          case None           => None
          case Some(Left(a1)) => tailRecM(a1)(f)
          case Some(Right(b)) => pure(b)
        }
    }

    implicit val treeMonad = new Monad[Tree] {
      def flatMap[A, B](tree: Tree[A])(f: A => Tree[B]): Tree[B] =
        tree match {
          case Leaf(value) => f(value)
          case Branch(left, right) => Branch(flatMap(left)(f), flatMap(right)(f))
        }

      def pure[A](a: A): Tree[A] = Leaf(a)

      def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] =
        f(a) match {
          case Branch(left, right) =>
            Branch(
              flatMap(left) {
                case Left(tree) => tailRecM(tree)(f)
                case Right(tree) => pure(tree)
              },
              flatMap(right) {
                case Left(tree) => tailRecM(tree)(f)
                case Right(tree) => pure(tree)
              }
            )
          case Leaf(Left(value)) => tailRecM(value)(f)
          case Leaf(Right(value)) => pure(value)
        }
    }
  }

}
