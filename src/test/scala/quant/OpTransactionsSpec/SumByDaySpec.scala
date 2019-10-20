package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, StatByDay, Transaction}

class SumByDaySpec extends FunSuite{
  test("OpTransactions.sumByDay() update stats on they are received")
  {
    val statsA = OpTransactions.processByDayNewTrasaction(
      Transaction("EEE","any",3,"any",10000)
    )

    val statsB = OpTransactions.processByDayNewTrasaction(
      Transaction("EEE","any",2,"any",10000), statsA
    )

    val statsC = OpTransactions.processByDayNewTrasaction(
      Transaction("EEE","any",3,"any",50000), statsB
    )

    assert(List(
      StatByDay(2,10000.0),
      StatByDay(3,60000.0)
    ) == statsC.values.toList)
  }
}
