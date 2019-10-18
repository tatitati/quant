package quant

import cats.data.EitherT
import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.ListTransaction


object Question1 extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")

    val stats = OpTransactions.processAllByDay(transactions, List())
    val tableText = Render.run(stats,
      """
        |Day|Total""".stripMargin)

    IO{println(tableText)}.as(ExitCode.Success)
  }
}

