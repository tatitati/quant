package quant

import scala.util.{Success, Try}

sealed trait Stat {
  def toTable(): String
}

final case class StatQ1(day: Int, total: Double) extends Stat{
  def toTable(): String = {
    s"""
       |$day|$total""".stripMargin
  }

  def updateWithTransaction(transaction: Transaction): StatQ1 = {
    StatQ1(
      this.day,
      this.total + transaction.transactionAmount
    )
  }
}

final case class StatQ2(account: String, category: String, total: Double, fromNItems: Int) extends Stat{
  def toTable(): String = {
    val avg = total/fromNItems
    s"""
       |$account|$category|$avg""".stripMargin
  }

  def updateWithTransaction(transaction: Transaction): StatQ2 = {
    StatQ2(
      this.account,
      this.category,
      this.total + transaction.transactionAmount,
      this.fromNItems + 1
    )
  }
}

final case class StatQ3(
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

  def updateWithTransaction(transaction: Transaction): StatQ3 = {
    StatQ3(
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

  def sumUp(withStat: StatQ3): StatQ3 = {
    StatQ3(
      this.day,
      this.account,
      List(this.max, withStat.max).max,
      this.total + withStat.total,
      this.fromNItems + withStat.fromNItems,
      this.catAA + withStat.catAA,
      this.catCC + withStat.catCC,
      this.catFF + withStat.catFF
    )
  }
}
