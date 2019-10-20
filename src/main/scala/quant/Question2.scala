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

    val stats: IO[Either[ErrorRead, AccCatMapStat]] = EitherT(transactions).map(analyze(_)).value

    val tableText = EitherT(stats).map((x: AccCatMapStat) =>
      Render.run(x.values.toList.sortBy(_.account), "\nAccount|Category|Avg\n")
    ).value

    val output: IO[Either[ErrorRead, Unit]] = EitherT(tableText).map(x=> println(x)).value
    output.as(ExitCode.Success)
  }
}

