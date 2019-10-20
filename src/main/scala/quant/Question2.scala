package quant

import cats.effect._
import cats.syntax.all._
import scala.annotation.tailrec

object Question2 extends IOApp {

  @tailrec
  def analyze(transactions: List[Transaction], statsAcumulator: Map[(String, String), StatByAccCat] = Map()): Map[(String, String), StatByAccCat] = {
    transactions match {
      case Nil => statsAcumulator
      case _ => analyze(
        transactions.tail,
        OpTransactions.processByAccountAndCatNewTransaction(transactions.head, statsAcumulator))
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
//    val transactions: List[Transaction] = RepositoryTransactions.findAll("/transactions.txt")
//
//    val stats = analyze(transactions)
//    val tableText = Render.run(stats.values.toList.sortBy(_.account),
//      """
//        |Account|Category|Avg""".stripMargin)
//
//    IO{println(tableText)}.as(ExitCode.Success)
    IO{println("asdfasdf")}.as(ExitCode.Success)
  }
}

