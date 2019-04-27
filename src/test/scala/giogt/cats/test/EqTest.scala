package giogt.cats.test

import cats.syntax.option._

import giogt.cats.eq.Cat

import org.scalatest.{ Matchers, WordSpecLike }

final class EqTest extends WordSpecLike with Matchers {
  val cat1       = Cat("Garfield", 38, "orange and black")
  val cat2       = Cat("Heathcliff", 33, "orange and black")
  val optionCat1 = cat1.some
  val optionCat2 = none[Cat]

  "eq syntax" when {
    "comparing two different cats" should {
      "return false" in {
        cat1 === cat2 shouldBe false
      }
    }

    "comparing a Some(cat) and a None" should {
      "return false" in {
        optionCat1 === optionCat2 shouldBe false
      }
    }
  }
}
