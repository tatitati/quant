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

    val output: IO[Either[ErrorRead, Unit]] = EitherT(transactions).map{x: List[Transaction] =>
      val stats: DayMapStat = analyze(x)
      val body: String = Render.run(stats.values.toList.sortBy(_.day), "\nDay|Total\n")
      println(body)
    }.value

    output.as(ExitCode.Success)
  }
}

