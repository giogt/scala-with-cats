package giogt.cats

object eq {
  import cats.{ Eq, Show }
  import cats.instances.int._
  import cats.instances.string._
  import cats.syntax.eq._
  import cats.syntax.show._

  final case class Cat(name: String, age: Int, color: String)
  final object Cat extends CatInstances

  sealed trait CatInstances {
    implicit val catEq: Eq[Cat] =
      Eq.instance[Cat] { (cat1, cat2) =>
        cat1.name === cat2.name && cat1.age === cat2.age && cat1.color === cat2.color
      }

    implicit val catShow: Show[Cat] =
      (cat) => s"${cat.name.show} is a ${cat.age.show} year-old ${cat.color.show} cat."
  }
}
