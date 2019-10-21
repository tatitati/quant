package quant

import cats.data.EitherT
import cats.effect._
import cats.syntax.all._
import quant.RepositoryTransactions.ListTransaction
import scala.annotation.tailrec

object Question2 extends IOApp {

  type AccCatMapStat = Map[(String, String), StatByAccCat]

  @tailrec
  def analyze(transactions: List[Transaction], statsAcumulator: AccCatMapStat = Map()): AccCatMapStat = {
    transactions match {
      case Nil => statsAcumulator
      case _ => analyze(
        transactions.tail,
        OpTransactions.processByAccountAndCatNewTransaction(transactions.head, statsAcumulator))
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: IO[Either[ErrorRead, ListTransaction]] = RepositoryTransactions.findAll("/transactions.txt")

    val output: IO[Either[ErrorRead, Unit]] = EitherT(transactions).map{ x: ListTransaction =>
      val stats:AccCatMapStat = analyze(x)
      val bodyText = Render.run(stats.values.toList.sortBy(_.account), "\nAccount|Category|Avg")
      println(bodyText)
    }.value

    output.as(ExitCode.Success)
  }
}

