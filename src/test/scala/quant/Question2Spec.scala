package quant

import cats.data.EitherT
import cats.effect.IO
import org.scalatest.FunSuite
import quant.OpTransactions.ListTransaction

class Question2Spec extends FunSuite {
  test("Question 2"){
    val transactions: IO[Either[ErrorRead, ListTransaction]] = OpTransactions.findAll("/transactions.txt")

    val result = EitherT(transactions).map { listTransactions: ListTransaction =>
      OpTransactions.accountMapAvg(listTransactions)
    }.value

    val all = result.unsafeRunSync()
//    println(all)
  }


}
