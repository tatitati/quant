package quant

import cats.effect._
import cats.syntax.all._
import scala.annotation.tailrec

object Question1 extends IOApp {

  @tailrec
  def analyze(transactions: List[Transaction], statsAcumulator: Map[Int, StatByDay] = Map()): Map[Int, StatByDay] = {
    transactions match {
      case Nil => statsAcumulator
      case _ => analyze(
          transactions.tail,
          OpTransactions.processByDayNewTrasaction(transactions.head, statsAcumulator))
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: List[Transaction] = RepositoryTransactions.findAll("/transactions.txt")

    val stats = analyze(transactions)
    val tableText = Render.run(stats.values.toList.sortBy(_.day),
      """
        |Day|Total""".stripMargin)


    IO{println(tableText)}.as(ExitCode.Success)
  }
}

