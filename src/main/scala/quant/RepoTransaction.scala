package quant

import cats.effect.IO
import scala.io.Source
import cats.data._
import cats.effect._
import cats.implicits._
import scala.util.{Success, Try}

object RepoTransaction {

  type ListTransaction = List[Transaction]
  type daysMapTotals = Map[Int, Double]
  type CategoriesMapAvgs = Map[String, Double]
  type AccountId =  String

  def findAll(resourceFilename: String): IO[Either[ErrorRead, ListTransaction]] = IO {
    val file = getClass.getResource(resourceFilename)

    val transactions: Try[List[Transaction]] = Try {
      for {
        line <-  Source.fromFile(file.getPath).getLines().drop(1).toList
        fields = line.split(',')
      } yield Transaction(fields(0), fields(1), fields(2).toInt, fields(3), fields(4).toDouble)
    }

    transactions match {
      case Success(transactions) => transactions.asRight[ErrorRead]
      case _ => FileDontExist.asLeft[ListTransaction]
    }
  }

  def findTotalByDay(listTransaction: ListTransaction): daysMapTotals = {
      listTransaction
        .groupBy(_.transactionDay)
        .mapValues(_.map(_.transactionAmount).sum)
  }

  def accountMapAvg(listTransaction: ListTransaction): Map[AccountId, CategoriesMapAvgs] = {
    listTransaction.groupBy(_.accountId)
      .mapValues { (transactionsInAccount: ListTransaction) => transactionsInAccount.groupBy(_.category)
          .mapValues { (transactionsInCategory: ListTransaction) =>
            transactionsInCategory.map{ (transaction: Transaction) =>
              transaction.transactionAmount
            }.sum / transactionsInCategory.length
          }
      }
  }
}
