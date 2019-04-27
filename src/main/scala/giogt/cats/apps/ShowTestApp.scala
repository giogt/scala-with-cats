package giogt.cats.apps

import cats.syntax.show._

import cats.effect.{ ExitCode, IO, IOApp, Sync }

import giogt.cats.printer.Printer
import giogt.cats.show.Cat

object ShowTestApp extends IOApp {
  val testCat = Cat(name = "Garfield", age = 10, color = "orange")

  private def testAndPrint[F[_]: Sync](implicit P: Printer): F[Unit] =
    P.println(s"test cat: ${testCat.show}")

  def run(args: List[String]): IO[ExitCode] =
    testAndPrint[IO].map(_ => ExitCode.Success)
}
