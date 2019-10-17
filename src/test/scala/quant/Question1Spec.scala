package quant

import cats.data.EitherT
import cats.effect.IO
import org.scalatest.FunSuite
import quant.OpTransactions.ListTransaction

class Question1Spec extends FunSuite {

  test("Question1") {
    val transactions: IO[Either[ErrorRead, ListTransaction]] = OpTransactions.findAll("/transactions.txt")

    val result = EitherT(transactions).map { listTransactions: ListTransaction =>
      OpTransactions.findTotalByDay(listTransactions)
    }.value

    val all = result.unsafeRunSync()
//    println(all)
  }
}
