package giogt.cats.test

import giogt.cats.printable.{ Box, Cat }
import giogt.cats.printable.instances._
import giogt.cats.printable.syntax._

import org.scalatest.{ Matchers, WordSpecLike }

final class PrintableTest extends WordSpecLike with Matchers {
  val testCat = Cat(name = "Garfield", age = 10, color = "orange")

  "format syntax" when {
    "applied on a Boolean instance" should {
      "return the boolean string representation" in {
        true.format  shouldBe "yes"
        false.format shouldBe "no"
      }
    }
    "applied on a Box instance" should {
      "return the string representation of the value in the box" in {
        Box("hello, world").format shouldBe "\"hello, world\""
        Box(true).format           shouldBe "yes"
      }
    }
    "applied on a Int instance" should {
      "return the integer string representation" in {
        42.format shouldBe "42"
      }
    }
    "applied on a Cat instance" should {
      "format the cat in the expected string format" in {
        testCat.format shouldBe "Garfield is a 10 year-old orange cat."
      }
    }
  }
}
