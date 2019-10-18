package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, Stat1, Transaction}

class SumByDaySpec extends FunSuite{
  test("OpTransactions.sumByDay() update stats on they are received")
  {
    val statsA = OpTransactions.mergeTransactionByDay(
      Transaction("EEE","any",3,"any",10000)
    )

    val statsB = OpTransactions.mergeTransactionByDay(
      Transaction("EEE","any",2,"any",10000), statsA
    )

    val statsC = OpTransactions.mergeTransactionByDay(
      Transaction("EEE","any",3,"any",50000), statsB
    )

    val sorted = statsC.sortBy(_.day)

    assert(List(Stat1(2,10000.0), Stat1(3,60000.0)) == sorted)
  }

  test("I can automate the whole process") {
    val transactions = List(
      Transaction("any","any",3,"any",10000),
      Transaction("any","any",2,"any",10000),
      Transaction("any","any",3,"any",50000)
    )

    val result = OpTransactions.processAllByDay(transactions, List())

    assert(List(
      Stat1(3,60000.0),
      Stat1(2,10000.0)
    ) == result)
  }
}
