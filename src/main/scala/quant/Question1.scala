package quant

import cats.data.EitherT
import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.ListTransaction

import scala.annotation.tailrec


object Question1 extends IOApp {

  @tailrec
  def processAllByDay(transactions: List[Transaction], statsAcumulator: List[Stat1]): List[Stat1] = {
    transactions match {
      case Nil => statsAcumulator
      case transactions =>
        processAllByDay(
          transactions.tail,
          OpTransactions.processByDayNewTrasaction(transactions.head, statsAcumulator)
        )
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")

    val stats = processAllByDay(transactions, List())
    val tableText = Render.run(stats,
      """
        |Day|Total""".stripMargin)

    IO{println(tableText)}.as(ExitCode.Success)
  }
}

