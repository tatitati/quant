package quant

import cats.data.EitherT
import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.ListTransaction

object Question3 extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: IO[Either[ErrorRead, ListTransaction]] = RepositoryTransactions.findAll("/transactions.txt")

    val result = EitherT(transactions).map { listTransactions: ListTransaction =>
      for{
        transaction <- listTransactions
        stat <- OpTransactions.getFullStat(transaction)
      } yield stat
    }.value

    val all = result.unsafeRunSync()

    all match {
      case Right(stats) => IO{
        println("Day|Account|Maximum|Avg|AA|BB|CC|DD|EE|FF|GG")
        stats.foreach{x => println(x.toTable)}
      }.as(ExitCode.Success)
      case _ => IO{
        println("Error")
      }.as(ExitCode.Error)
    }
  }
}

