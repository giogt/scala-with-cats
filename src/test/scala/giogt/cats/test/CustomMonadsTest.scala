package giogt.cats.test

import cats.instances.string._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.semigroup._

import giogt.cats.customMonads.instances._
import giogt.cats.customMonads.tree.{ branch, leaf, Branch, Leaf, Tree }
import org.scalatest.{ Matchers, WordSpecLike }

final class CustomMonadsTest extends WordSpecLike with Matchers {
  val tree1Level: Tree[String] = leaf("l1")
  val tree2Levels: Tree[String] = branch(
    leaf("l2.1"),
    leaf("l2.2")
  )
  val tree3Levels: Tree[String] = Branch(
    branch(
      leaf("l3.1"),
      leaf("l3.2")
    ),
    leaf("l2.1")
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

  "flatMap" when {
    "concatenating multiple bramches" should {
      "return all left / right combinations" in {
        (for {
          a <- branch(leaf(100), leaf(200))
          b <- branch(leaf(a - 10), leaf(a + 10))
          c <- branch(leaf(b - 1), leaf(b + 1))
        } yield c) shouldBe branch(
          branch(
            branch(
              leaf(89),
              leaf(91)
            ),
            branch(
              leaf(109),
              leaf(111)
            )
          ),
          branch(
            branch(
              leaf(189),
              leaf(191)
            ),
            branch(
              leaf(209),
              leaf(211)
            )
          )
        )
      }
    }
  }
}
