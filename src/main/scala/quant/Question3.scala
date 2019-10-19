package quant

import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.ListTransaction

object Question3 extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")

    IO{println("Error")}.as(ExitCode.Success)
  }
}

