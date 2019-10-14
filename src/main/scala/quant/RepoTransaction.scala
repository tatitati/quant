package quant

import cats.effect.IO
import scala.io.Source
import cats.data._
import cats.effect._
import cats.implicits._
import scala.util.{Success, Try}
import cats.syntax.either._

object RepoTransaction {

  type listTransaction = List[Transaction]
  type dayMapTotal = Map[Int, Double]

  def findAll(resourceFilename: String): IO[Either[ErrorRead, listTransaction]] = IO {
    val file = getClass.getResource(resourceFilename)

    val transactions: Try[List[Transaction]] = Try {
      for {
        line <-  Source.fromFile(file.getPath).getLines().drop(1).toList
        fields = line.split(',')
      } yield Transaction(fields(0), fields(1), fields(2).toInt, fields(3), fields(4).toDouble)
    }

    transactions match {
      case Success(transactions) => transactions.asRight[ErrorRead]
      case _ => FileDontExist.asLeft[listTransaction]
    }
  }

  def findTotalByDay(resourceFilename: String): IO[Either[ErrorRead, dayMapTotal]] = {
    val result: IO[Either[ErrorRead, listTransaction]] = RepoTransaction.findAll("/transactions.txt")

    result.map{
      case Left(error) => Either.left(error)
      case Right(listTransaction) => Either.right(
        listTransaction
          .groupBy(_.transactionDay)
          .mapValues(_.map(_.transactionAmount).sum)
      )
    }
  }
}
