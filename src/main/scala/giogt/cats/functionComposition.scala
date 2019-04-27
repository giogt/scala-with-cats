package giogt.cats

object functionComposition {
  import cats.instances.function._

  import cats.syntax.contravariant._
  import cats.syntax.functor._

  val f1: String => Int = (s: String) => s.toInt
  val f2: Int => Int    = (n: Int) => n * 2

  // map, andThen
  val f1mapf2: String => Int     = f1 map f2
  val f1andthenf2: String => Int = f1 andThen f2

  // application, composition
  val f2f1: String => Int        = s => f2(f1(s))
  val f2composef1: String => Int = f2.compose(f1)

  // contramap
  type <=[B, A] = A => B
  val f2b: Int <= Int = f2

  val f2contramapf1: String => Int = f2b.contramap(f1)
}
