package giogt.cats

object writer {
  import cats.data.Writer
  import cats.instances.vector._
  import cats.syntax.applicative._
  import cats.syntax.writer._

  type Logged[A] = Writer[Vector[String], A]

  private def slowly[A](body: =>A): A =
    try body
    finally Thread.sleep(100)

  private def log(msg: String*): Vector[String] = msg.toVector

  def factorial(n: Int): Logged[Int] =
    for {
      ans <- if (n == 0) 1.pure[Logged]
            else slowly(factorial(n - 1).map(_ * n))
      _ <- log(s"fact $n $ans").tell
    } yield ans
}
