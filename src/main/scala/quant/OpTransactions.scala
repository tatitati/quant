package quant

object OpTransactions {
  case class KeyWindow(dayrange: String, account: String)

  def processByDayNewTrasaction(transaction: Transaction, statsAcumulator: Map[Int, StatByDay] = Map()): Map[Int, StatByDay] = {
    val day = transaction.transactionDay

      statsAcumulator.get(day) match {
      case None => statsAcumulator + (day -> StatByDay(transaction.transactionDay, transaction.transactionAmount))
      case Some(stat) =>
        val udpated = statsAcumulator - day
        udpated + (day -> stat.updateWithTransaction(transaction))
    }
  }

  def processByAccountAndCatNewTransaction(transaction: Transaction, statsAcumulator: Map[(String, String), StatByAccCat] = Map()):  Map[(String, String), StatByAccCat] = {
    val acc = transaction.accountId
    val cat = transaction.category

    statsAcumulator.get((acc, cat)) match {
      case None => statsAcumulator + ((acc, cat) -> StatByAccCat(transaction.accountId, transaction.category, transaction.transactionAmount, 1))
      case Some(stat) =>
        val udpated = statsAcumulator - ((acc, cat))
        udpated + ((acc, cat) -> stat.updateWithTransaction(transaction))
    }
  }

  def processTransactionInWindow(trans: Transaction, statAcc: Map[KeyWindow, StatByWindowAcc]): Map[KeyWindow, StatByWindowAcc] = {
    val key = getKeyWindow(trans)
    val stat = statAcc.get(key)

    stat match {
      case None => statAcc + (key -> StatByWindowAcc(trans.transactionDay, trans.accountId ,0, 0, 0, 0, 0, 0))
      case Some(stat) =>
        val updated = statAcc - (key)
        updated + (key -> stat.updateWithTransaction(trans))
    }
  }

  def getKeyWindow(transaction: Transaction): KeyWindow = {
    val lowBoundary = transaction.transactionDay-5
    val windowKey = transaction.transactionDay-1 + "-" + {if(lowBoundary < 0 ) 0 else lowBoundary}

    KeyWindow(windowKey, transaction.accountId)
  }
}
