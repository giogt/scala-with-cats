package giogt.cats.test

import cats.syntax.show._

import giogt.cats.show.Cat

import org.scalatest.{ Matchers, WordSpecLike }

final class ShowTest extends WordSpecLike with Matchers {
  val testCat = Cat(name = "Garfield", age = 10, color = "orange")

  "show syntax" when {
    "applied on a Cat instance" should {
      "show the cat in the expected string format" in {
        testCat.show shouldBe "Garfield is a 10 year-old orange cat."
      }
    }
  }
}
