package quant

import scala.io.Source

class RepoTransaction {
  def findAll() = {
    val fileName = "C:/Users/User1/Desktop/transactions.txt"

    val transactionslines = Source.fromFile(fileName).getLines().drop(1)

    val transactions: List[Transaction] = transactionslines.map { line =>
      val split = line.split(',')
      Transaction(split(0), split(1), split(2).toInt, split(3), split(4).toDouble)
    }.toList
  }
}
