package quant

import cats.effect.IO
import scala.io.Source
import cats.data._
import cats.effect._
import cats.implicits._
import scala.util.{Success, Try}

object RepoTransaction {

  type listTransaction = List[Transaction]
  type dayMapTotal = Map[Int, Double]
  type accountMapAvg = Map[String, Double]

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
    val result: IO[Either[ErrorRead, listTransaction]] = RepoTransaction.findAll(resourceFilename)

    result.map{
      case Left(error) => Either.left(error)
      case Right(listTransaction) => Either.right(
        listTransaction
          .groupBy(_.transactionDay)
          .mapValues(_.map(_.transactionAmount).sum)
      )
    }
  }

  def accountMapAvg(resourceFilename: String): IO[Either[ErrorRead, accountMapAvg]] = {
    val result: IO[Either[ErrorRead, listTransaction]] = RepoTransaction.findAll(resourceFilename)

    result.map{
      case Left(error) => Either.left(error)
      case Right(listTransaction) => Either.right {
        val result1 = listTransaction
          .groupBy(_.accountId)
          .mapValues{
            _.groupBy(_.category)
              .mapValues(_.size)
          }

         val result2 = for{
          (key, submap) <- result1
        } yield (key, submap.map(_._2).sum.toDouble)

        result2
      }
    }
  }
}
