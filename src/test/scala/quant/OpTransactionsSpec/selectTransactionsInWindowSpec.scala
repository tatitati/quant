package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, Transaction}

class selectTransactionsInWindowSpec extends FunSuite {

  test("Can select proper window of transactions") {
    val givenListTransaction = List(
      Transaction("any","any",6,"any",161.01),
      Transaction("any","any",3,"any",62.03),
      Transaction("any","any",4,"any",987.04),
      Transaction("any","any",5,"any",909.93),
      Transaction("any","any",2,"any",114.63),
      Transaction("any","any",1,"any",114.63),
      Transaction("any","any",7,"any",114.63)
    )

    val transactionsInWindow = OpTransactions.selectTransactionsInWindow(givenListTransaction, 7)

    assert(List(
      Transaction("any","any",6,"any",161.01),
      Transaction("any","any",3,"any",62.03),
      Transaction("any","any",4,"any",987.04),
      Transaction("any","any",5,"any",909.93),
      Transaction("any","any",2,"any",114.63))
      == transactionsInWindow
    )
  }
}
