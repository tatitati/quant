package quant

import cats.data.EitherT
import cats.effect.IO
import org.scalatest.FunSuite
import quant.RepoTransaction.ListTransaction

class Question1Spec extends FunSuite {

  test("Question1") {
    val transactions: IO[Either[ErrorRead, ListTransaction]] = RepoTransaction.findAll("/transactions.txt")

    val result = EitherT(transactions).map { listTransactions: ListTransaction =>
      RepoTransaction.findTotalByDay(listTransactions)
    }.value

    val all = result.unsafeRunSync()
    println(all)
  }
}
