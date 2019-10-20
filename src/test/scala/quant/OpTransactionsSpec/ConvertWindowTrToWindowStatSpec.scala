package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, StatQ2, StatQ3, Transaction}

class ConvertWindowTrToWindowStatSpec extends FunSuite {
  test("Question3: Can convert list of transactions into a list of stats") {
    val transactions: List[Transaction] = List(
      Transaction("any","accA",4,"BB",100),
      Transaction("any","accA",4,"AA",100),
      Transaction("any","accA",5,"BB",5),
      Transaction("any","accB",3,"BB",5)
    )

    val result = OpTransactions.convertWindowTrToWindowStat(transactions)

    assert(List(
      StatQ3(4,"accA",max = 100.0,total = 200.0,fromNItems = 2,catAA = 100.0,catCC =0.0, catFF = 0.0),
      StatQ3(5,"accA",max = 5.0, total = 5.0, fromNItems = 1, catAA = 0.0, catCC = 0.0, catFF = 0.0),
      StatQ3(3,"accB",max = 5.0, total = 5.0, fromNItems = 1, catAA = 0.0, catCC = 0.0, catFF = 0.0)
    ) == result)
  }
}
