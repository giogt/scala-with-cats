package giogt.cats.apps

import cats.syntax.option._

import giogt.cats.printable.Cat
import giogt.cats.printable.instances._
import giogt.cats.printable.syntax._

object PrintableTestApp extends App {
  val testCat = Cat(name = "Garfield", age = 10, color = "orange")

  testCat.print
  testCat.some.print
  none[Cat].print
}
