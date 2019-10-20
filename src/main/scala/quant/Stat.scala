package quant

sealed trait Stat {
  def toTable(): String
  def updateWithTransaction(transaction: Transaction): Stat
}

final case class StatByDay(day: Int, total: Double) extends Stat{
  def toTable(): String = {
    s"""
       |$day|$total""".stripMargin
  }

  def updateWithTransaction(transaction: Transaction): StatByDay = {
    StatByDay(
      this.day,
      this.total + transaction.transactionAmount
    )
  }
}

final case class StatByAccCat(account: String, category: String, total: Double, fromNItems: Int) extends Stat{
  def toTable(): String = {
    val avg = total/fromNItems
    s"""
       |$account|$category|$avg""".stripMargin
  }

  def updateWithTransaction(transaction: Transaction): StatByAccCat = {
    StatByAccCat(
      this.account,
      this.category,
      this.total + transaction.transactionAmount,
      this.fromNItems + 1
    )
  }
}

final case class StatByWindowAcc(
      day: Int,
      account: String,
      max: Double,
      total: Double,
      fromNItems: Int,
      catAA: Double,
      catCC: Double,
      catFF: Double
  ) extends Stat {

  def toTable(): String = {
    val avg = if(fromNItems == 0) 0 else total / fromNItems

    s"""
       |$day|$account|$max|$avg|$catAA|$catCC|$catFF""".stripMargin
  }

  def updateWithTransaction(transaction: Transaction): StatByWindowAcc = {
    StatByWindowAcc(
      this.day,
      this.account,
      List(this.max, transaction.transactionAmount).max,
      this.total + transaction.transactionAmount,
      this.fromNItems + 1,
      {if(transaction.category == "AA") this.catAA + transaction.transactionAmount else this.catAA},
      {if(transaction.category == "CC") this.catCC + transaction.transactionAmount else this.catCC},
      {if(transaction.category == "FF") this.catFF + transaction.transactionAmount else this.catFF},
    )
  }
}
