package giogt.cats

/**
  * An utility to print to the console.
  */
object printer {
  import cats.instances.string._
  import cats.syntax.monoid._

  import cats.effect.Sync

  trait Printer {
    def print[F[_]](s: String)(implicit F: Sync[F]): F[Unit]
    def println[F[_]](s: String)(implicit F: Sync[F]): F[Unit]
  }

  class ConsolePrinter extends Printer {
    private val newLine = "\n"

    def print[F[_]](s: String)(implicit F: Sync[F]): F[Unit] =
      F.delay(Console.print(s))

    def println[F[_]](s: String)(implicit F: Sync[F]): F[Unit] =
      print(s |+| newLine)
  }

  object ConsolePrinter {
    def apply(): ConsolePrinter = new ConsolePrinter()
  }
}
