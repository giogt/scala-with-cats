package giogt.cats.test

import giogt.cats.monoidInstances
import giogt.cats.monoidInstances.{ MultInt, SumInt }

import org.scalatest.{ Matchers, WordSpecLike }

final class MonoidInstancesTest extends WordSpecLike with Matchers {
  "combine" when {
    "two SumInt value are provided" should {
      "sum the two integers" in {
        monoidInstances.combine(SumInt(2), SumInt(5))  shouldBe 7
        monoidInstances.combine(SumInt(-1), SumInt(9)) shouldBe 8
      }
    }

    "two MultInt value are provided" should {
      "multiply the two integers" in {
        monoidInstances.combine(MultInt(2), MultInt(5))  shouldBe 10
        monoidInstances.combine(MultInt(-1), MultInt(9)) shouldBe -9
      }
    }
  }

  "empty" when {
    "type is SumInt" should {
      "return  0" in {
        monoidInstances.empty[SumInt] shouldBe 0
      }
    }

    "type is MultInt" should {
      "return  1" in {
        monoidInstances.empty[MultInt] shouldBe 1
      }
    }
  }
}
