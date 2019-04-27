package giogt.cats

object reader {
  import cats.data.Reader
  import cats.syntax.applicative._

  case class Db(
    usernames: Map[Int, String],
    passwords: Map[String, String]
  )

  type DbReader[A] = Reader[Db, A]
  object DbReader {
    def apply[A](f: Db => A): DbReader[A] = Reader[Db, A](f)
  }

  private def findUsername(userId: Int): DbReader[Option[String]] =
    DbReader(db => db.usernames.get(userId))

  private def checkPassword(
    username: String,
    password: String
  ): DbReader[Boolean] =
    DbReader(db => db.passwords.get(username).contains(password))

  def checkLogin(
    userId: Int,
    password: String
  ): DbReader[Boolean] =
    for {
      maybeUser <- findUsername(userId)
      canLogin  <- maybeUser.fold(false.pure[DbReader])(user => checkPassword(user, password))
    } yield canLogin
}
