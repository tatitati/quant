package quant

import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.ListTransaction

import scala.annotation.tailrec

object Question2 extends IOApp {

  @tailrec
  def processAllByAccAndCategory(transactions: List[Transaction], statsAcumulator: List[StatQ2] = List()): List[StatQ2] = {
    transactions match {
      case Nil => statsAcumulator
      case values => processAllByAccAndCategory(
        values.tail,
        OpTransactions.processByAccountAndCatNewTransaction(values.head, statsAcumulator)
      )
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")

    val stats = processAllByAccAndCategory(transactions, List())
    val tableText = Render.run(stats.sortBy(_.account),
      """
        |Account|Category|Avg""".stripMargin)

    IO{println(tableText)}.as(ExitCode.Success)
  }
}

