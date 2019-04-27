package giogt.cats

object imap {

  final case class Box[A](value: A)

  trait Codec[A] {
    self =>

    def encode(value: A): String
    def decode(value: String): A
    def imap[B](dec: A => B, enc: B => A): Codec[B] =
      new Codec[B] {
        def encode(value: B): String = self.encode(enc(value))
        def decode(value: String): B = dec(self.decode(value))
      }
  }

  object Codec {
    def encode[A](value: A)(implicit c: Codec[A]): String = c.encode(value)
    def decode[A](value: String)(implicit c: Codec[A]): A = c.decode(value)
  }

  object instances {
    object codec {
      implicit val stringCodec: Codec[String] =
        new Codec[String] {
          def encode(value: String): String = value
          def decode(value: String): String = value
        }

      // this is just an example, type conversion errors here are not handled
      implicit val booleanCodec: Codec[Boolean] = stringCodec.imap(_.toBoolean, _.toString)
      implicit val doubleCodec: Codec[Double]   = stringCodec.imap(_.toDouble, _.toString)
      implicit val intCodec: Codec[Int]         = stringCodec.imap(_.toInt, _.toString)

      implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] = c.imap(Box(_), _.value)
    }
  }
}
