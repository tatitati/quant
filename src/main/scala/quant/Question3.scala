package quant

import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.ListTransaction
import scala.annotation.tailrec

object Question3 extends IOApp {

  @tailrec
  def analyze(transactions: List[Transaction], statsAcumulator: Map[OpTransactions.KeyWindow, StatByWindowAcc] = Map()): Map[OpTransactions.KeyWindow, StatByWindowAcc]  = {
    transactions match {
      case Nil => statsAcumulator
      case _ => analyze(
          transactions.tail,
          OpTransactions.processTransactionInWindow(transactions.head, statsAcumulator))
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")
    val stats = analyze(transactions.reverse)
    val tableText = Render.run(stats.values.toList.sortBy(_.day),
      """
        |Day|Account|Max|Avg|AAcat|CCcat|FFcat""".stripMargin)


    IO{println(tableText)}.as(ExitCode.Success)
  }
}

