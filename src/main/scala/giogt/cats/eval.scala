package giogt.cats

import cats.Eval

object eval {
  def factorialEval(n: BigInt): Eval[BigInt] =
    if (n == 1) Eval.now(n)
    else Eval.defer(factorialEval(n - 1).map(_ * n))

  def foldRightEval[A, B](as: List[A], acc: Eval[B])(fn: (A, B) => B): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer(foldRightEval(tail, acc)(fn)).map(a => fn(head, a))
      case Nil =>
        acc
    }

  def foldRightEval2[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer(fn(head, foldRightEval2(tail, acc)(fn)))
      case Nil =>
        acc
    }
}
