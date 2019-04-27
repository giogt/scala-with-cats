package giogt.cats

object printable {
  final case class Box[A](value: A)
  final case class Cat(name: String, age: Int, color: String)

  trait Printable[A] {
    self =>

    def format(value: A): String
    def contramap[B](f: B => A): Printable[B] = b => self.format(f(b))
  }

  object instances {
    implicit val booleanPrintable: Printable[Boolean] = b => if (b) "yes" else "no"
    implicit val intPrintable: Printable[Int]         = n => n.toString
    implicit val stringPrintable: Printable[String]   = s => "\"" + s + "\""

    implicit def boxPrintable[A](implicit p: Printable[A]) =
      p.contramap[Box[A]](_.value)

    implicit val catPrintable: Printable[Cat] =
      cat => s"${cat.name} is a ${cat.age} year-old ${cat.color} cat."

    implicit def optionPrintable[A](implicit p: Printable[A]): Printable[Option[A]] =
      maybeValue =>
        maybeValue match {
          case Some(value) => p.format(value)
          case None        => ""
      }
  }

  object syntax {
    implicit class PrintableOps[A](value: A) {
      def format(implicit P: Printable[A]): String = P.format(value)
      def print(implicit P: Printable[A]): Unit    = println(format)
    }
  }

  object Printable {
    def format[A](value: A)(implicit P: Printable[A]): String =
      P.format(value)

    def print[A](value: A)(implicit P: Printable[A]): Unit =
      println(format(value))
  }
}
