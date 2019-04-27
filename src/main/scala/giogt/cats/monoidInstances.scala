package giogt.cats

/**
  * An extra exercise to test different monoid instances for the same type, but
  * for different operation (e.g., sum of integers and multiplication of
  * integers).
  */
object monoidInstances {
  import cats.{ Monoid, Show }
  import cats.instances.int._
  import cats.syntax.monoid._
  import cats.syntax.show._

  final val SumInt = MkSumInt
  final type SumInt = SumInt.T

  final val MultInt = MkMultInt
  final type MultInt = MultInt.T

  object MkSumInt extends newtype[Int] with SumIntInstances

  sealed trait SumIntInstances {
    implicit val sumIntMonoid: Monoid[SumInt] = new Monoid[SumInt] {
      def empty: SumInt = SumInt(0)
      def combine(n: SumInt, m: SumInt): SumInt =
        SumInt(n.unMk + m.unMk)
    }

    implicit val sumIntshow: Show[SumInt] =
      (n: SumInt) => n.unMk.show
  }

  object MkMultInt extends newtype[Int] with MultiIntInstances

  sealed trait MultiIntInstances {
    implicit val multIntMonoid: Monoid[MultInt] = new Monoid[MultInt] {
      def empty: MultInt = MultInt(1)
      def combine(n: MultInt, m: MultInt): MultInt =
        MultInt(n.unMk * m.unMk)
    }

    implicit val multIntShow: Show[MultInt] =
      (n: MultInt) => n.unMk.show
  }

  def empty[A: Monoid]                    = Monoid[A].empty
  def combine[A: Monoid](a1: A, a2: A): A = a1 |+| a2
}
