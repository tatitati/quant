package quant

import org.scalatest.FunSuite

class Question3Spec extends FunSuite {
  test("Can select proper window of transactions") {
    val givenListTransaction = List(
      Transaction("any","any",1,"any",161.01),
      Transaction("any","any",2,"any",62.03),
      Transaction("any","any",3,"any",987.04),
      Transaction("any","any",4,"any",909.93),
      Transaction("any","any",5,"any",114.63),
      Transaction("any","any",6,"any",114.63),
      Transaction("any","any",7,"any",114.63)
    )

    val transactionsInWindow = RepoTransaction.selectTransactionsInWindow(givenListTransaction, 7)

    assert(
      List(
        Transaction("any","any",2,"any",62.03),
        Transaction("any","any",3,"any",987.04),
        Transaction("any","any",4,"any",909.93),
        Transaction("any","any",5,"any",114.63),
        Transaction("any","any",6,"any",114.63)
      ) == transactionsInWindow
    )
  }

  test("Can find maximum value of transaction from a bunch of transactions") {
    val givenListTransaction = List(
      Transaction("any","any",1,"any",20),
      Transaction("any","any",2,"any",5),
      Transaction("any","any",3,"any",600),
      Transaction("any","any",4,"any",300)
    )

    val maxValue = RepoTransaction.findTransactionWithMaxValue(givenListTransaction)

    assert(600 == maxValue)
  }

  test("Can calculate average value of a bunch of transactions") {
    val givenListTransaction = List(
      Transaction("any","any",1,"any",20),
      Transaction("any","any",2,"any",5),
      Transaction("any","any",3,"any",60),
      Transaction("any","any",4,"any",100)
    )

    val maxValue = RepoTransaction.averageValue(givenListTransaction)

    assert(46.25 == maxValue)
  }

  test("Can calculate total value of a bunch of transactions per type-transaction") {
    val givenListTransaction = List(
      Transaction("any","any",1,"AA",20),
      Transaction("any","any",2,"DD",5),
      Transaction("any","any",3,"AA",60),
      Transaction("any","any",4,"CC",100),
      Transaction("any","any",5,"DD",1000)
    )

    val valuePerType = RepoTransaction.findTotalByType(givenListTransaction)

    assert(Map(
      "DD" -> 1005.0,
      "AA" -> 80.0,
      "CC" -> 100.0
    ) == valuePerType
    )
  }
}
