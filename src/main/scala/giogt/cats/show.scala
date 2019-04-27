package giogt.cats

object show {
  import cats.Show
  import cats.instances.int._
  import cats.instances.string._
  import cats.syntax.show._

  final case class Cat(name: String, age: Int, color: String)
  final object Cat extends CatInstances

  sealed trait CatInstances {
    implicit val catShowInstance: Show[Cat] =
      (cat) => s"${cat.name.show} is a ${cat.age.show} year-old ${cat.color.show} cat."
  }
}
