package quant

import quant.OpTransactions.convertWindowTrToWindowStat

import scala.annotation.tailrec

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

  @tailrec
  def convertWindowTrToWindowStat(transactions: List[Transaction], statsAcc: List[StatQ3] = List()): List[StatQ3] = {
      transactions match {
        case Nil => statsAcc
        case values =>
          val stats = updateStatsAccFromTransaction(transactions, statsAcc)
          convertWindowTrToWindowStat(transactions.tail, stats)
      }
  }

  def updateStatsAccFromTransaction(transactions: List[Transaction], statsAcc: List[StatQ3] = List()): List[StatQ3] = {
    val tr = transactions.head
    val statMatchedIdx: List[Int] = statsAcc
      .zipWithIndex
      .filter(stat =>
        stat._1.account == tr.accountId
          && stat._1.day == tr.transactionDay
      )
      .map(_._2)

    statMatchedIdx match {
      case Nil =>
        val newstat = StatQ3(
          day =  tr.transactionDay,
          account = tr.accountId,
          max = tr.transactionAmount,
          total = tr.transactionAmount,
          fromNItems = 1,
          catAA = {if(tr.category == "AA") tr.transactionAmount else 0},
          catCC = {if(tr.category == "CC") tr.transactionAmount else 0},
          catFF = {if(tr.category == "FF") tr.transactionAmount else 0}
        )
        statsAcc :+ newstat
      case List(idx) => statsAcc.updated(
        idx,
        StatQ3(
          day =  tr.transactionDay,
          account = tr.accountId,
          max = List(statsAcc(idx).max, tr.transactionAmount).max,
          total = statsAcc(idx).total + tr.transactionAmount,
          fromNItems = statsAcc(idx).fromNItems + 1,
          catAA = {if(tr.category == "AA") statsAcc(idx).catAA + tr.transactionAmount else 0},
          catCC = {if(tr.category == "CC") statsAcc(idx).catCC + tr.transactionAmount else 0},
          catFF = {if(tr.category == "FF") statsAcc(idx).catFF + tr.transactionAmount else 0}
        )
      )
    }
  }

  def getWindowForTransaction(transaction: Transaction, allTransactions: List[Transaction]): List[Transaction] = {
    allTransactions.filter { any =>
      any.transactionDay >= transaction.transactionDay - 5 &&
      any.transactionDay < transaction.transactionDay &&
      any.accountId == transaction.accountId
    }
  }

  def sumUpWindow(transaction: Transaction, statsWindow: List[StatQ3] = List()): StatQ3 = {
    val statZero = StatQ3(
      transaction.transactionDay,
      transaction.accountId,
      max = 0,
      total = 0,
      fromNItems = 0,
      catAA = 0,
      catCC = 0,
      catFF = 0
    )

    statsWindow match {
      case Nil => statZero
      case _ => statsWindow.foldLeft(statZero)( _ sumUp _)
    }
  }
}
