package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, Transaction}

class GetKeyWindowSpec extends FunSuite {
  test("For an account I can get the previous transactions that are needed to be processed"){
      val givenTransaction = Transaction("any","accA",6,"any",50000)
    
      val result = OpTransactions.getKeyWindow(givenTransaction)

      println(result)
  }
}
