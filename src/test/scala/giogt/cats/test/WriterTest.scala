package giogt.cats.test

import giogt.cats.writer.factorial
import org.scalatest.{ Matchers, WordSpecLike }

final class WriterTest extends WordSpecLike with Matchers {
  "factorial" when {
    "only one instance is running" should {
      "calculate the factorial and accumulate logs" in {
        val (log, res) = factorial(5).run
        res shouldBe 120
        log shouldBe Vector(
          "fact 0 1",
          "fact 1 1",
          "fact 2 2",
          "fact 3 6",
          "fact 4 24",
          "fact 5 120"
        )
      }
    }

    "multiple instances are running in parallel" should {
      "calculate the factorial and accumulate logs separately for each instance" in {
        import scala.concurrent._
        import scala.concurrent.ExecutionContext.Implicits.global
        import scala.concurrent.duration._

        val res: Vector[(Vector[String], Int)] = Await.result(
          Future.sequence(
            Vector(
              Future(factorial(3).run),
              Future(factorial(5).run)
            )
          ),
          5.seconds
        )

        res.length shouldBe 2

        val (log1, res1) = res.head
        res1 shouldBe 6
        log1 shouldBe Vector(
          "fact 0 1",
          "fact 1 1",
          "fact 2 2",
          "fact 3 6"
        )

        val (log2, res2) = res.drop(1).head
        res2 shouldBe 120
        log2 shouldBe Vector(
          "fact 0 1",
          "fact 1 1",
          "fact 2 2",
          "fact 3 6",
          "fact 4 24",
          "fact 5 120"
        )
      }
    }
  }
}
