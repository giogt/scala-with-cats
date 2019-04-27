package giogt.cats.test

import giogt.cats.functionComposition

import org.scalatest.{ Matchers, WordSpecLike }

final class FunctionCompositionTest extends WordSpecLike with Matchers {
  "function composition" when {
    "using map" should {
      "return a working composition" in {
        functionComposition.f1mapf2("123") shouldBe 246
      }
    }
    "using andThen" should {
      "return a working composition" in {
        functionComposition.f1andthenf2("123") shouldBe 246
      }
    }
    "using application - f2(f1(_))" should {
      "return a working composition" in {
        functionComposition.f2f1("123") shouldBe 246
      }
    }
    "using compose" should {
      "return a working composition" in {
        functionComposition.f2composef1("123") shouldBe 246
      }
    }
    "using contramap" should {
      "return a working composition" in {
        functionComposition.f2contramapf1("123") shouldBe 246
      }
    }
  }
}
