package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, Transaction}

class FindTotalByDaySpec extends FunSuite {

  test("RepoTransaction.findTotalByDay()") {
    val givenListTransaction = List(
      Transaction("any","any",20,"any",161.01),
      Transaction("any","any",5,"any",62.03),
      Transaction("any","any",20,"any",987.04),
      Transaction("any","any",8,"any",909.93),
      Transaction("any","any",5,"any",114.63)
    )

    val dayMapTotalvalue = OpTransactions.findTotalByDay(givenListTransaction)

    assert(Map(
      8 -> 909.93,
      20 -> 1148.05,
      5 -> 176.66
    ) == dayMapTotalvalue)
  }
}
