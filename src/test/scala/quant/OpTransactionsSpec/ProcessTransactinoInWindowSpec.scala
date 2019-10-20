package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.OpTransactions.KeyWindow
import quant.{OpTransactions, StatQ3, Transaction}

class ProcessTransactinoInWindowSpec extends FunSuite {
  test("I can update stat for a transaction inside the window") {
    val givenTransaction = Transaction("any","accA",8,"CC",30000)

    val givenAccStat: Map[OpTransactions.KeyWindow, StatQ3] = Map(
      KeyWindow("7-3","accA") -> StatQ3(4,"accA",max = 100.0,total = 200.0,fromNItems = 2,catAA = 100.0,catCC =0.0, catFF = 0.0)
    )

    val result = OpTransactions.processTransactionInWindow(givenTransaction, givenAccStat)

    assert(
      Map(KeyWindow("7-3","accA") -> StatQ3(4,"accA",30000.0,30200.0,3,100.0,30000.0,0.0))
      == result)
  }

  test("New entries are updated in the map if there is not sta-windows for the new transactions") {
    val givenTransaction = Transaction("any","accA",8,"CC",10000)
    val givenAccStat: Map[OpTransactions.KeyWindow, StatQ3] = Map()

    val result = OpTransactions.processTransactionInWindow(givenTransaction, givenAccStat)

    assert(
      Map(KeyWindow("7-3","accA") -> StatQ3(8,"accA",0.0,0.0,0,0.0,0.0,0.0))
      == result
    )
  }
}
