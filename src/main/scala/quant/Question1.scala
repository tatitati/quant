package quant

import cats.data.{EitherT}
import cats.effect._
import quant.RepositoryTransactions.{ListTransaction, OrListTransaction}
import cats.implicits._

import scala.annotation.tailrec

object Question1 extends IOApp {

  type DayMapStat = Map[Int, StatByDay]

  @tailrec
  def analyze(transactions: List[Transaction], statsAcumulator: DayMapStat = Map()): DayMapStat = {
    transactions match {
      case Nil => statsAcumulator
      case _ => analyze(
          transactions.tail,
          OpTransactions.processByDayNewTrasaction(transactions.head, statsAcumulator))
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: IO[Either[ErrorRead, ListTransaction]] = RepositoryTransactions.findAll("/transactions.txt")

    val stats: IO[Either[ErrorRead, DayMapStat]] = EitherT(transactions).map(analyze(_)).value

    val body = EitherT(stats).map((x: DayMapStat) =>
      Render.run(x.values.toList, "\nDay|Total\n")
    ).value

    println(body.unsafeRunSync())
    IO{println("asdf")}.as(ExitCode.Success)
  }
}

