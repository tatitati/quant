package quant

import cats.implicits._

object OpTransactions {
  type ListTransaction = List[Transaction]

  def selectTransactionsInWindow(transactions: ListTransaction, day: Int): ListTransaction = {
    transactions.filter{ tr =>
      tr.transactionDay < day && tr.transactionDay >= day-5
    }
  }

  def sumByDay(transaction: Transaction, statsAcumulator: List[Stat1] = List()): List[Stat1] = {
    val statMatchedIdx: List[Int] = statsAcumulator.zipWithIndex.filter(_._1.day == transaction.transactionDay).map(_._2)

    val result = statMatchedIdx match {
      case Nil => statsAcumulator :+ Stat1(transaction.transactionDay, transaction.transactionAmount)
      case List(idx) => {
        statsAcumulator.updated(
          idx,
          Stat1(
            statsAcumulator(idx).day,
            statsAcumulator(idx).total + transaction.transactionAmount
          )
        )
      }
    }

    result.sortBy(_.day)
  }

  def sumByAccountAndCategory(transaction: Transaction, statsAcumulator: List[Stat2] = List()): List[Stat2] = {
    val statMatchedIdx: List[Int] = statsAcumulator
      .zipWithIndex
      .filter(stat =>
        stat._1.account == transaction.accountId
        && stat._1.category == transaction.category
      )
      .map(_._2)

    statMatchedIdx match {
      case Nil => statsAcumulator :+ Stat2(transaction.accountId, transaction.category, transaction.transactionAmount, 1)
      case List(idx) => {
        statsAcumulator.updated(
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
  }

  def getFullStat(transaction: Transaction, statsAcumulator: List[Stat3] = List()): List[Stat3] = {
    val statMatchedIdx: List[Int] = statsAcumulator
      .zipWithIndex
      .filter(stat =>
        stat._1.day == transaction.transactionDay
          && stat._1.account == transaction.accountId
      )
      .map(_._2)

    statMatchedIdx match {
      case Nil =>
        val defaultMap = Map("AA" -> 0.0, "BB" -> 0.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0)
        statsAcumulator :+ Stat3(
          transaction.transactionDay,
          transaction.accountId,
          transaction.transactionAmount,
          transaction.transactionAmount,
          1,
          defaultMap + (transaction.category -> transaction.transactionAmount)
        )
      case List(idx) => {
        statsAcumulator.updated(
          idx,
          Stat3(
            statsAcumulator(idx).day,
            statsAcumulator(idx).account,
            if(transaction.transactionAmount > statsAcumulator(idx).max) transaction.transactionAmount else statsAcumulator(idx).max,
            statsAcumulator(idx).total + transaction.transactionAmount,
            statsAcumulator(idx).fromNItems + 1,
            {
              val statsAcc = statsAcumulator(idx).categoryMapTotal

              statsAcc.contains(transaction.category) match {
                case true => {
                  val originalvalue = statsAcc(transaction.category)
                  val update1 = statsAcc - transaction.category
                  update1 + (transaction.category -> (originalvalue + transaction.transactionAmount))
                }
                case false => statsAcc + (transaction.category -> transaction.transactionAmount)
              }

            }
          )
        )
      }
    }
  }
}
