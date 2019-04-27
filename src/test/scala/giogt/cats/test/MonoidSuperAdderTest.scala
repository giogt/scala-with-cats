package giogt.cats.test

import cats.instances.int._
import cats.instances.option._
import cats.syntax.option._

import giogt.cats.monoidSuperAdder.{ Order, SuperAdder }

import org.scalatest.{ Matchers, WordSpecLike }

final class MonoidSuperAdderTest extends WordSpecLike with Matchers {

  private val testListInt       = List(1, 2, 3, 4, 5)
  private val testListOptionInt = List(1.some, none, 3.some, none, 5.some)
  private val testListOrder     = List(Order(100, 2), Order(75, 3))

  "super adder" when {
    "a List[Int] is provided" should {
      "sum all integers in the list" in {
        SuperAdder.add(testListInt) shouldBe 15
      }
    }

    "a List[Option[Int]] is provided" should {
      "sum all integers in the list, excluding nones" in {
        SuperAdder.add(testListOptionInt) shouldBe 9.some
      }
    }

    "a List[Order] is provided" should {
      "sum all orders in the list" in {
        SuperAdder.add(testListOrder) shouldBe Order(175, 5)
      }
    }
  }
}
