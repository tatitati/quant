package quant

import cats.data.EitherT
import cats.effect._
import cats.syntax.all._
import quant.Question2.{AccCatMapStat, analyze}
import quant.RepositoryTransactions.ListTransaction

import scala.annotation.tailrec

object Question3 extends IOApp {

  type WindowMapStat = Map[OpTransactions.KeyWindow, StatByWindowAcc]

  @tailrec
  def analyze(transactions: List[Transaction], statsAcumulator: WindowMapStat = Map()): WindowMapStat = {
    transactions match {
      case Nil => statsAcumulator
      case _ => analyze(
          transactions.tail,
          OpTransactions.processTransactionInWindow(transactions.head, statsAcumulator))
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: IO[Either[ErrorRead, ListTransaction]] = RepositoryTransactions.findAll("/transactions.txt")

    val stats: IO[Either[ErrorRead, WindowMapStat]] = EitherT(transactions).map(analyze(_)).value

    val tableText = EitherT(stats).map((x: WindowMapStat) =>
      Render.run(x.values.toList.sortBy(_.day), "\n|Day|Account|Max|Avg|AAcat|CCcat|FFcat\n")
    ).value

    println(tableText.unsafeRunSync())
    IO{println("tableText")}.as(ExitCode.Success)
  }
}

