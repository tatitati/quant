package quant

import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.{ListTransaction, getWindowForTransaction, processWindowByTransaction}

import scala.annotation.tailrec

object Question3 extends IOApp {

//  @tailrec
//  def getFullStat(transactions: List[Transaction], statsAcumulator: List[StatQ3] = List()): List[StatQ3] = {
//    transactions match {
//      case Nil => statsAcumulator
//      case values => {
//        val windowForTransaction = getWindowForTransaction(values.head, values.tail)
//        val accStats = statsAcumulator :+ OpTransactions.processWindowByTransaction(values.head, windowForTransaction)
//        getFullStat(values.tail, accStats)
//      }
//    }
//  }

  override def run(args: List[String]): IO[ExitCode] = {
//    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")
//    val stats = getFullStat(transactions)
//
//    val tableText = Render.run(stats.sortBy{x => (x.day, x.account)},
//      """
//        |Day|Account|Max|Avg|AAcat|CCcat|FFcat""".stripMargin)

    IO{println("asdfa")}.as(ExitCode.Success)
  }
}

