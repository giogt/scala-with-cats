package giogt.cats.test

import giogt.cats.eval

import cats.Eval

import org.scalatest.{ Matchers, WordSpecLike }

final class EvalTest extends WordSpecLike with Matchers {
  "factorialEval" when {
    "calculating a large number" should {
      "not fail for stack overflow" in {
        eval.factorialEval(50000).value
      }
    }
  }

  "foldRightEval" when {
    "used on a large list" should {
      "not fail for stack overflow" in {
        val numbers = (1 to 50000).toList
        val start   = Eval.now(BigInt(0))

        eval
          .foldRightEval[Int, BigInt](numbers, start) { (n, acc) =>
            acc + n
          }
          .value shouldBe 1250025000
        eval
          .foldRightEval2[Int, BigInt](numbers, start) { (n, acc) =>
            acc.map(_ + n)
          }
          .value shouldBe 1250025000
      }
    }
  }
}
