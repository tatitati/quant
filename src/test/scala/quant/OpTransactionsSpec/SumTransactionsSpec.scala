package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, Transaction}

class SumTransactionsSpec extends FunSuite {

  test("RepoTransaction.sumTransactions()") {
    val givenListTransaction = List(
      Transaction("any","any",44,"any",20),
      Transaction("any","any",55,"any",5),
      Transaction("any","any",33,"any",100)
    )

    val total = OpTransactions.sumTransactions(givenListTransaction)

    assert(125.0 == total)
  }
}
