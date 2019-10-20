package quant

import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.{ListTransaction, getWindowForTransaction, sumUpWindow}

import scala.annotation.tailrec

object Question3 extends IOApp {

  @tailrec
  def analyze(transactions: List[Transaction], statsAcumulator: List[StatQ3] = List()): List[StatQ3] = {
    transactions match {
      case Nil => statsAcumulator
      case values => {
        val windowForTransaction = getWindowForTransaction(values.head, values)
        val windowStats = OpTransactions.convertWindowTrToWindowStat(windowForTransaction)
        analyze(values.tail, windowStats)
      }
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")
    val stats = analyze(transactions)
    val tableText = Render.run(stats,
      """
        |Day|Account|Max|Avg|AAcat|CCcat|FFcat""".stripMargin)

    IO{println(tableText)}.as(ExitCode.Success)
  }
}

