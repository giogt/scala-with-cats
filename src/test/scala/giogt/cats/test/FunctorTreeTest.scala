package giogt.cats.test

import giogt.cats.functorTree.{ Branch, Leaf, Tree }

import cats.instances.string._
import cats.syntax.functor._
import cats.syntax.semigroup._

import org.scalatest.{ Matchers, WordSpecLike }

final class FunctorTreeTest extends WordSpecLike with Matchers {
  val tree1Level: Tree[String] = Leaf("l1")
  val tree2Levels: Tree[String] = Branch(
    Leaf("l2.1"),
    Leaf("l2.2")
  )
  val tree3Levels: Tree[String] = Branch(
    Branch(
      Leaf("l3.1"),
      Leaf("l3.2")
    ),
    Leaf("l2.1")
  )

  "map" when {
    "adding a trailing <!>" should {
      "add it for trees of different levels" in {
        tree1Level.map(s => s |+| "!") shouldBe Leaf("l1!")
        tree2Levels.map(s => s |+| "!") shouldBe Branch(
          Leaf("l2.1!"),
          Leaf("l2.2!")
        )
        tree3Levels.map(s => s |+| "!") shouldBe Branch(
          Branch(
            Leaf("l3.1!"),
            Leaf("l3.2!")
          ),
          Leaf("l2.1!")
        )
      }
    }
  }
}
