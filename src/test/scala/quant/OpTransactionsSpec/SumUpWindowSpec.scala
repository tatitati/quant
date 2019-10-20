package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, StatQ3, Transaction}

class SumUpWindowSpec extends FunSuite {

  test("For a transaction with empty window") {
    val givenTransaction = Transaction("any","accA",8,"BB",100)
    val givenWindowStatsForTransaction = List()

    val statTransaction: StatQ3 = OpTransactions.sumUpWindow(givenTransaction, givenWindowStatsForTransaction)

    assert(
      StatQ3(8,"accA",max = 0, total = 0, fromNItems = 0, catAA = 0, catCC = 0, catFF = 0)
        == statTransaction)
  }

  test("For a transaction with no-empty window") {
    val givenTransaction = Transaction("any","accA",8,"BB",100)
    val givenWindowStatsForTransaction = List(
      StatQ3(3, "accA", max = 20, total = 20, fromNItems = 3, catAA = 10, catCC = 50, catFF=100),
      StatQ3(5, "accA", max = 30, total = 50, fromNItems = 2, catAA = 20, catCC = 150, catFF=2000),
      StatQ3(6, "accA", max = 50, total = 100, fromNItems = 1,catAA = 30, catCC = 250, catFF=30000),
    )

    val statTransaction: StatQ3 = OpTransactions.sumUpWindow(givenTransaction, givenWindowStatsForTransaction)

    assert(
      StatQ3(8,"accA",max = 50.0, total = 170.0, fromNItems = 6, catAA = 60, catCC = 450, catFF = 32100)
      == statTransaction)
  }
}
