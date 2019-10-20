package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.OpTransactions.ListTransaction
import quant.{OpTransactions, StatQ3, Transaction}

class GetWindowForTransactionSpec extends FunSuite {
  test("For an account I can get the previous transactions that are needed to be processed"){
      val givenTransaction = Transaction("any","accA",6,"any",50000)
      val givenListOfTransactions = List(
        Transaction("any","accA",4,"BB",100),
        Transaction("any","accA",5,"BB",5),
        Transaction("any","accB",3,"BB",5),
        Transaction("any","accA",6,"CC",2000),
        Transaction("any","accA",8,"CC",999),
      )

      val result = OpTransactions.getWindowForTransaction(givenTransaction, givenListOfTransactions)

      assert(List(
        Transaction("any","accA",4,"BB",100),
        Transaction("any","accA",5,"BB",5)
      ) == result)
  }
}
