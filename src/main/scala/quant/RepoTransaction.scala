package quant

import cats.effect.IO
import scala.io.Source
import cats.data._
import cats.effect._
import cats.implicits._
import scala.util.{Success, Try}

object RepoTransaction {

  type ListTransaction = List[Transaction]
  type DaysMapTotals = Map[Int, Double]
  type CategoriesMapAvgs = Map[String, Double]
  type AccountId =  String
  type Day =  Int

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

  def sumTransactions(transactions: ListTransaction): Double = {
    transactions.foldLeft(0.0)(_ + _.transactionAmount)
  }

  def groupTransactionsByDay(transactions: ListTransaction): Map[Day, ListTransaction] = {
    transactions.groupBy(_.transactionDay)
  }

  def groupTransactionsByAccount(transactions: ListTransaction): Map[AccountId, ListTransaction] = {
    transactions.groupBy(_.accountId)
  }

  def groupTransactionsByCategory(transactions: ListTransaction): Map[String, ListTransaction] = {
    transactions.groupBy(_.category)
  }

  def selectTransactionsInWindow(transactions: ListTransaction, day: Day): ListTransaction = {
    transactions.filter{ tr =>
      tr.transactionDay < day && tr.transactionDay >= day-5
    }
  }

  def findTotalByDay(listTransaction: ListTransaction): DaysMapTotals = {
    groupTransactionsByDay(listTransaction)
      .mapValues(sumTransactions(_))
  }

  def accountMapAvg(listTransaction: ListTransaction): Map[AccountId, CategoriesMapAvgs] = {
    groupTransactionsByAccount(listTransaction).mapValues { (transactionsInAccount: ListTransaction) =>
        groupTransactionsByCategory(transactionsInAccount).mapValues { (transactionsInCategory: ListTransaction) =>
            sumTransactions(transactionsInCategory) / transactionsInCategory.length
          }
      }
  }

  def findTransactionWithMaxValue(listTransaction: List[Transaction]): Double = {
    listTransaction
      .map(_.transactionAmount)
      .reduceLeft(_ max _)
  }

  def averageValue(listTransaction: List[Transaction]): Double = {
    listTransaction
      .map(_.transactionAmount)
      .foldLeft(0.0)(_ + _) / listTransaction.length
  }
}
