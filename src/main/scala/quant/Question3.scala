package quant

import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.{ListTransaction, getStatWindow, processWindowByTransaction}

import scala.annotation.tailrec

object Question3 extends IOApp {

  @tailrec
  def getFullStat(transactions: List[Transaction], statsAcumulator: List[StatQ3] = List()): List[StatQ3] = {
    transactions match {
      case Nil => statsAcumulator
      case values => {
        val windowStats: List[StatQ3] = getStatWindow(values.head, statsAcumulator)
        val statsForTransaction = windowStats match {
          case Nil => statsAcumulator :+ StatQ3(
            values.head.transactionDay,
            values.head.accountId,
            max = 0,
            total = 0,
            fromNItems = 1
          )
          case listOfStats => statsAcumulator :+ processWindowByTransaction(transactions.head, listOfStats)
        }

        getFullStat(transactions.tail, statsForTransaction)
      }
    }


  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")
    val stats = getFullStat(transactions)

    val tableText = Render.run(stats.sortBy{x => (x.day, x.account)},
      """
        |Day|Account|Max|Avg|AAcat|CCcat|FFcat""".stripMargin)

    IO{println(tableText)}.as(ExitCode.Success)
  }
}

