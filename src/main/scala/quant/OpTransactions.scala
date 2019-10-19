package quant

import cats.implicits._

object OpTransactions {
  type ListTransaction = List[Transaction]

  def processByDayNewTrasaction(transaction: Transaction, statsAcumulator: List[Stat1] = List()): List[Stat1] = {
    val foundStatForDay: List[Int] = statsAcumulator.zipWithIndex.filter(_._1.day == transaction.transactionDay).map(_._2)

    foundStatForDay match {
      case Nil => statsAcumulator :+ Stat1(transaction.transactionDay, transaction.transactionAmount)
      case List(idx) => statsAcumulator.updated(idx,
        Stat1(
          statsAcumulator(idx).day,
          statsAcumulator(idx).total + transaction.transactionAmount
        )
      )
    }
  }

  def processByAccountAndCatNewTransaction(transaction: Transaction, statsAcumulator: List[Stat2] = List()): List[Stat2] = {
    val statMatchedIdx: List[Int] = statsAcumulator
      .zipWithIndex
      .filter(stat =>
        stat._1.account == transaction.accountId
        && stat._1.category == transaction.category
      )
      .map(_._2)

    statMatchedIdx match {
      case Nil => statsAcumulator :+ Stat2(transaction.accountId, transaction.category, transaction.transactionAmount, 1)
      case List(idx) => statsAcumulator.updated(
          idx,
          Stat2(
            statsAcumulator(idx).account,
            statsAcumulator(idx).category,
            statsAcumulator(idx).total + transaction.transactionAmount,
            statsAcumulator(idx).fromNItems + 1
          )
        )
    }
  }

  def getWindow(transaction: Transaction, accStats: List[Stat3]): List[Stat3] = {
    accStats.filter { stat =>
      stat.day > transaction.transactionDay - 6 &&
      stat.day < transaction.transactionDay &&
      stat.account == transaction.accountId
    }
  }











  def combineWindow(transaction: Transaction, listOfStats: List[Stat3]): Stat3 = {
    listOfStats match {
      case Nil => Stat3(transaction.transactionDay, transaction.accountId, 0, 0, 0)
      case _ => Stat3(
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

  def getFullStat(transaction: Transaction, statsAcumulator: List[Stat3] = List()): List[Stat3] = {
    val windowStats: List[Stat3] = getWindow(transaction, statsAcumulator)

    windowStats match {
      case Nil => statsAcumulator :+ Stat3(
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
