package giogt.cats.test

import giogt.cats.state.{ evalAll, evalInput, evalOne }
import org.scalatest.{ Matchers, WordSpecLike }

final class StateTest extends WordSpecLike with Matchers {

  "evalOne" when {
    "invoked once with a number" should {
      "return the number and add it in the stack" in {
        evalOne("1").run(Nil).value shouldBe Tuple2(List(1), 1)
      }
    }
    "invoked multiple times with numbers" should {
      "return the number and add it in the stack" in {
        (for {
          n1 <- evalOne("1")
          n2 <- evalOne("2")
          n3 <- evalOne("3")
        } yield (n1, n2, n3)).run(Nil).value shouldBe Tuple2(List(3, 2, 1), (1, 2, 3))
      }
    }
    "invoked with numbers and a plus operation" should {
      "perform the calculation correctly" in {
        (for {
          _   <- evalOne("2")
          _   <- evalOne("3")
          ans <- evalOne("+")
        } yield ans).runA(Nil).value shouldBe 5
      }
    }
    "invoked with numbers and a times operation" should {
      "perform the calculation correctly" in {
        (for {
          _   <- evalOne("2")
          _   <- evalOne("3")
          ans <- evalOne("*")
        } yield ans).runA(Nil).value shouldBe 6
      }
    }
    "invoked with numbers and a operations" should {
      "perform the calculation correctly" in {
        (for {
          _   <- evalOne("1")
          _   <- evalOne("2")
          _   <- evalOne("+")
          _   <- evalOne("5")
          ans <- evalOne("*")
        } yield ans).runA(Nil).value shouldBe 15
      }
    }
  }

  "evalAll" when {
    "invoked with numbers and a operations" should {
      "perform the calculation correctly" in {
        evalAll(List("1", "2", "+", "5", "*")).runA(Nil).value shouldBe 15
      }
    }
  }

  "evalInput" when {
    "invoked with numbers and a operations" should {
      "perform the calculation correctly" in {
        evalInput("1 2 + 5 *") shouldBe 15
      }
    }
  }
}
