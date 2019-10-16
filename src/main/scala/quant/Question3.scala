package quant

import cats.data.EitherT
import cats.effect._
import cats.syntax.all._
import quant.RepoTransaction.ListTransaction

object Question3 extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: IO[Either[ErrorRead, ListTransaction]] = RepoTransaction.findAll("/transactions.txt")

    val result = EitherT(transactions).map { listTransactions: ListTransaction =>
      RepoTransaction.accountMapAvg(listTransactions)
    }.value

    val all = result.unsafeRunSync()

    IO(println(all)).as(ExitCode.Success)
  }
}

