package quant

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

  def getWindowForTransaction(transaction: Transaction, allTransactions: List[Transaction]): List[Transaction] = {
    allTransactions.filter { x =>
      x.transactionDay >= transaction.transactionDay - 5 &&
      x.transactionDay < transaction.transactionDay &&
      x.accountId == transaction.accountId
    }
  }

  def processWindowByTransaction(transaction: Transaction, statsWindow: List[StatQ3] = List()): StatQ3 = {
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
