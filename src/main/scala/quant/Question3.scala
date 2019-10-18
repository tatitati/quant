package quant

import cats.data.EitherT
import cats.effect._
import cats.syntax.all._
import quant.OpTransactions.ListTransaction

object Question3 extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val transactions: ListTransaction = RepositoryTransactions.findAll("/transactions.txt")


    IO{println("Error")}.as(ExitCode.Success)

//    val result = EitherT(transactions).map { listTransactions: ListTransaction =>
//      for{
//        transaction <- listTransactions
//        stat <- OpTransactions.getFullStat(transaction)
//      } yield stat
//    }.value
//
//    val all = result.unsafeRunSync()
////    IO{println("asdf")}.as(ExitCode.Success)
//    all match {
//      case Right(stats) => IO{
//        println("Day|Account|Maximum|Avg|AA|BB|CC|DD|EE|FF|GG")
//        stats.foreach{x => println(x.toTable)}
//      }.as(ExitCode.Success)
//      case _ => IO{
//        println("Error")
//      }.as(ExitCode.Error)
//    }
  }
}

