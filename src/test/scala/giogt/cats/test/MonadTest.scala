package giogt.cats.test

import cats.{ Id, Monad }
import cats.syntax.either._
import cats.syntax.option._

import giogt.cats.monad.instances._
import giogt.cats.monad.syntax._

import org.scalatest.{ Matchers, WordSpecLike }

final class MonadTest extends WordSpecLike with Matchers {

  val testError = new Throwable("test")

  "monad.flatMap_ syntax" when {
    "invoked on the available instances" should {
      "follow flatMap rules" in {
        none[String].flatMap_(s => (s + "!").some) shouldBe none
        "foo".some.flatMap_(s => (s + "!").some)   shouldBe "foo!".some

        testError.asLeft[String].flatMap_(s => (s + "!").asRight[Throwable]) shouldBe testError.asLeft[String]
        "foo".asRight[Throwable].flatMap_(s => (s + "!").asRight[Throwable]) shouldBe "foo!"
          .asRight[Throwable]

        Monad[Id].pure("foo").flatMap_(s => (s + "!")) shouldBe "foo!"
      }
    }
  }

  "monad.map_ syntax" when {
    "invoked on the available instances" should {
      "follow map rules" in {
        none[String].map_(s => s + "!") shouldBe none
        "foo".some.map_(s => s + "!")   shouldBe "foo!".some

        testError.asLeft[String].map_(s => s + "!") shouldBe testError.asLeft[String]
        "foo".asRight[Throwable].map_(s => s + "!") shouldBe "foo!".asRight[Throwable]

        Monad[Id].pure("foo").map_(s => s + "!") shouldBe "foo!"
      }
    }
  }
}
