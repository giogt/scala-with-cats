package giogt.cats.test

import giogt.cats.reader.{ checkLogin, Db }
import org.scalatest.{ Matchers, WordSpecLike }

final class ReaderTest extends WordSpecLike with Matchers {

  val users = Map(
    1 -> "dade",
    2 -> "kate",
    3 -> "margo"
  )

  val passwords = Map(
    "dade"  -> "zerocool",
    "kate"  -> "acidburn",
    "margo" -> "secret"
  )

  val db = Db(users, passwords)

  "checkLogin" when {
    "userId is not present" should {
      "return false" in {
        checkLogin(4, "davinci").run(db) shouldBe false
      }
    }
    "userId is present but the associated password is wrong" should {
      "return false" in {
        checkLogin(1, "wrongpassword").run(db) shouldBe false
      }
    }
    "userId and password combination is correct" should {
      "return true" in {
        checkLogin(1, "zerocool").run(db) shouldBe true
      }
    }
  }
}
