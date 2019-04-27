package giogt.cats

object state {
  import cats.data.State
  import cats.syntax.applicative._
  import cats.syntax.option._

  // TODO error handling

  sealed trait Op {
    def symbol: String
    def empty: Int
    def combine(implicit N: Numeric[Int]): (Int, Int) => Int
  }
  object Op {
    def fromString(s: String): Option[Op] = s match {
      case Addition.symbol       => Addition.some
      case Subtraction.symbol    => Subtraction.some
      case Multiplication.symbol => Multiplication.some
      case Division.symbol       => Division.some
      case _                     => none
    }
  }

  case object Addition extends Op {
    val symbol                                               = "+"
    val empty                                                = 0
    def combine(implicit N: Numeric[Int]): (Int, Int) => Int = N.plus
  }
  case object Subtraction extends Op {
    val symbol                                               = "-"
    val empty                                                = 0
    def combine(implicit N: Numeric[Int]): (Int, Int) => Int = N.minus
  }
  case object Multiplication extends Op {
    val symbol                                               = "*"
    val empty                                                = 1
    def combine(implicit N: Numeric[Int]): (Int, Int) => Int = N.times
  }
  case object Division extends Op {
    val symbol                                               = "/"
    val empty                                                = 1
    def combine(implicit N: Numeric[Int]): (Int, Int) => Int = (n, m) => n / m
  }

  type CalcState[A] = State[List[Int], A]
  object CalcState {
    def apply[A](f: List[Int] => (List[Int], A)): CalcState[A] = State[List[Int], A](f)
  }

  def evalOne(sym: String): CalcState[Int] =
    sym match {
      case Addition.symbol       => operator(Addition)
      case Subtraction.symbol    => operator(Subtraction)
      case Multiplication.symbol => operator(Multiplication)
      case Division.symbol       => operator(Division)
      case num                   => operand(toInt(num))
    }

  def evalAll(input: List[String]): CalcState[Int] =
    input.foldLeft(0.pure[CalcState]) { (acc, s) =>
      acc.flatMap(_ => evalOne(s))
    }

  def evalInput(input: String): Int =
    evalAll(input.split(" ").toList).runA(Nil).value

  private def toInt(s: String): Int = s.toInt

  private def operand(num: Int): CalcState[Int] =
    CalcState[Int] { stack =>
      (num :: stack, num)
    }

  private def operator(op: Op): CalcState[Int] =
    CalcState[Int] {
      case n :: tail =>
        val res = tail.foldLeft(n)(op.combine)
        (List(res), res)
      case _ => (List(op.empty), op.empty)
    }
}
