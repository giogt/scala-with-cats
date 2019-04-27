package giogt.cats.test

import giogt.cats.imap.Box
import giogt.cats.imap.Codec
import giogt.cats.imap.instances.codec._

import org.scalatest.{ Matchers, WordSpecLike }

final class ImapTest extends WordSpecLike with Matchers {
  "Codec.encode" when {
    "an instance is available for the type to encode" should {
      "return the string representation for the type" in {
        Codec.encode(true)      shouldBe "true"
        Codec.encode(Box(true)) shouldBe "true"

        Codec.encode(123.4)      shouldBe "123.4"
        Codec.encode(Box(123.4)) shouldBe "123.4"

        Codec.encode(123)      shouldBe "123"
        Codec.encode(Box(123)) shouldBe "123"
      }
    }
  }

  "Codec.decode" when {
    "an instance is available for the type to decode" should {
      "return a type instance from the string representation" in {
        Codec.decode[Boolean]("true")      shouldBe true
        Codec.decode[Box[Boolean]]("true") shouldBe Box(true)

        Codec.decode[Double]("123.4")      shouldBe 123.4
        Codec.decode[Box[Double]]("123.4") shouldBe Box(123.4)

        Codec.decode[Int]("123")      shouldBe 123
        Codec.decode[Box[Int]]("123") shouldBe Box(123)
      }
    }
  }
}
