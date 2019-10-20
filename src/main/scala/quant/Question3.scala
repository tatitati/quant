package quant

import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.{ListTransaction, getWindowForTransaction}

import scala.annotation.tailrec

object Question3 extends IOApp {

  @tailrec
  def analyze(transactions: List[Transaction], statsAcumulator: List[StatQ3] = List()): List[StatQ3] = {
    transactions match {
      case Nil => statsAcumulator
      case values => {
        val windowForTransaction = getWindowForTransaction(transactions.head, transactions.tail)
        val windowStats = OpTransactions.convertWindowTrToWindowStat(windowForTransaction)
        analyze(transactions.tail, windowStats)
      }
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")
    val stats = analyze(transactions.reverse)
    val tableText = Render.run(stats,
      """
        |Day|Account|Max|Avg|AAcat|CCcat|FFcat""".stripMargin)

    IO{println(tableText)}.as(ExitCode.Success)
  }
}

