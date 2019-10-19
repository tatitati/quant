package quant

import cats.implicits._

object OpTransactions {
  type ListTransaction = List[Transaction]

  def processByDayNewTrasaction(transaction: Transaction, statsAcumulator: List[StatQ1] = List()): List[StatQ1] = {
    val foundStatForDay: List[Int] = statsAcumulator.zipWithIndex.filter(_._1.day == transaction.transactionDay).map(_._2)

    foundStatForDay match {
      case Nil => statsAcumulator :+ StatQ1(transaction.transactionDay, transaction.transactionAmount)
      case List(idx) => statsAcumulator.updated(idx,
        StatQ1(
          statsAcumulator(idx).day,
          statsAcumulator(idx).total + transaction.transactionAmount)
      )
    }
  }

  def processByAccountAndCatNewTransaction(transaction: Transaction, statsAcumulator: List[StatQ2] = List()): List[StatQ2] = {
    val statMatchedIdx: List[Int] = statsAcumulator
      .zipWithIndex
      .filter(stat =>
        stat._1.account == transaction.accountId
        && stat._1.category == transaction.category
      )
      .map(_._2)

    statMatchedIdx match {
      case Nil => statsAcumulator :+ StatQ2(transaction.accountId, transaction.category, transaction.transactionAmount, 1)
      case List(idx) => statsAcumulator.updated(
          idx,
          StatQ2(
            statsAcumulator(idx).account,
            statsAcumulator(idx).category,
            statsAcumulator(idx).total + transaction.transactionAmount,
            statsAcumulator(idx).fromNItems + 1)
        )
    }
  }

  def getWindow(transaction: Transaction, accStats: List[StatQ3]): List[StatQ3] = {
    accStats.filter { stat =>
      stat.day > transaction.transactionDay - 6 &&
      stat.day < transaction.transactionDay &&
      stat.account == transaction.accountId
    }
  }











  def combineWindow(transaction: Transaction, listOfStats: List[StatQ3]): StatQ3 = {
    listOfStats match {
      case Nil => StatQ3(transaction.transactionDay, transaction.accountId, 0, 0, 0)
      case _ => StatQ3(
        transaction.transactionDay,
        transaction.accountId,
        listOfStats.maxBy(_.max).max,
        listOfStats.foldLeft(0.0)(_ + _.total),
        listOfStats.foldLeft(0)(_ + _.fromNItems),
        Map(
          "AA" -> listOfStats.foldLeft(0.0)(_ + _.categoryMapTotal("AA")),
          "BB" -> 0,
          "CC" -> listOfStats.foldLeft(0.0)(_ + _.categoryMapTotal("CC")),
          "DD" -> 0,
          "EE" -> 0,
          "FF" -> listOfStats.foldLeft(0.0)(_ + _.categoryMapTotal("FF")),
          "GG" -> 0
        )
      )
    }
  }

  def getFullStat(transaction: Transaction, statsAcumulator: List[StatQ3] = List()): List[StatQ3] = {
    val windowStats: List[StatQ3] = getWindow(transaction, statsAcumulator)

    windowStats match {
      case Nil => statsAcumulator :+ StatQ3(
          transaction.transactionDay,
          transaction.accountId,
          0,
          0,
          1
        )
      case listOfStats => statsAcumulator :+ combineWindow(transaction, listOfStats)
    }
  }
}
