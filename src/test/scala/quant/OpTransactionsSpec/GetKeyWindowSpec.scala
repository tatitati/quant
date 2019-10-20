package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.OpTransactions.KeyWindow
import quant.{OpTransactions, Transaction}

class GetKeyWindowSpec extends FunSuite {
  test("Key windows are created properly"){
      val givenTransaction1 = Transaction("any","accA",6,"any",50000)
      val givenTransaction2 = Transaction("any","accA",2,"any",50000)
      val givenTransaction3 = Transaction("any","accA",1,"any",50000)

      val result1 = OpTransactions.getKeyWindow(givenTransaction1)
      val result2 = OpTransactions.getKeyWindow(givenTransaction2)
      val result3 = OpTransactions.getKeyWindow(givenTransaction3)

      assert(KeyWindow("5-1","accA") == result1)
      assert(KeyWindow("1-0","accA") == result2)
      assert(KeyWindow("0-0","accA") == result3)
  }
}
