package giogt.cats

import com.github.ghik.silencer.silent

object functorTree {
  import cats.Functor
  import cats.Show

  sealed trait Tree[+A]
  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A)                        extends Tree[A]

  object Tree extends TreeInstances

  sealed trait TreeInstances {
    implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
      def map[A, B](tree: Tree[A])(f: A => B): Tree[B] =
        tree match {
          case Branch(left, right) => Branch(map(left)(f), map(right)(f))
          case Leaf(value)         => Leaf(f(value))
        }
    }

    @silent
    implicit def treeShow[A: Show]: Show[Tree[A]] =
      (tree: Tree[A]) => "Tree{ TODO }"
  }
}
