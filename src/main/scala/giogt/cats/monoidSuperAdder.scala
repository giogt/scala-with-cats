package giogt.cats

object monoidSuperAdder {
  import cats.Monoid
  import cats.Show
  import cats.instances.int._
  import cats.syntax.monoid._

  case class Order(totalCost: Int, quantity: Int)
  object Order extends OrderInstances

  sealed trait OrderInstances {
    implicit val orderMonoid: Monoid[Order] = new Monoid[Order] {
      val empty: Order = Order(0, 0)
      def combine(x: Order, y: Order): Order =
        Order(x.totalCost |+| y.totalCost, x.quantity |+| y.quantity)
    }

    implicit val orderShow: Show[Order] =
      (order: Order) => s"Order{ totalCost: ${order.totalCost}, quantity: ${order.quantity} }"
  }

  object SuperAdder {
    def add[A](items: List[A])(implicit M: Monoid[A]): A =
      items.foldLeft(M.empty)(M.combine)
  }
}
