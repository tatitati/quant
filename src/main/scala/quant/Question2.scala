package quant

import cats.data.EitherT
import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.ListTransaction

object Question2 extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: IO[Either[ErrorRead, ListTransaction]] = OpTransactions.findAll("/transactions.txt")

    val result = EitherT(transactions).map { listTransactions: ListTransaction =>
      OpTransactions.accountMapAvg(listTransactions)
    }.value

    val all = result.unsafeRunSync()

    IO(println(all)).as(ExitCode.Success)
  }
}

