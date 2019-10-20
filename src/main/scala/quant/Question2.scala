package quant

import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.ListTransaction
import scala.annotation.tailrec

object Question2 extends IOApp {

  @tailrec
  def analyze(transactions: List[Transaction], statsAcumulator: Map[(String, String), StatQ2] = Map()): Map[(String, String), StatQ2] = {
    transactions match {
      case Nil => statsAcumulator
      case values => analyze(
        values.tail,
        OpTransactions.processByAccountAndCatNewTransaction(values.head, statsAcumulator)
      )
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")

    val stats = analyze(transactions)
    val tableText = Render.run(stats.values.toList.sortBy(_.account),
      """
        |Account|Category|Avg""".stripMargin)

    IO{println(tableText)}.as(ExitCode.Success)
  }
}

