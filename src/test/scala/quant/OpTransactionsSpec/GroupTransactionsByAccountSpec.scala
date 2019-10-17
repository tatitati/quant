package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, Transaction}

class GroupTransactionsByAccountSpec extends FunSuite {

  test("RepoTransaction.groupTransactionsByAccount()") {
    val givenListTransaction = List(
      Transaction("any","accountA",20,"any",10),
      Transaction("any","accountA",5,"any",70),
      Transaction("any","accountB",8,"any",500),
      Transaction("any","accountB",5,"any",100),
      Transaction("any","accountB",5,"any",80)
    )

    val groups = OpTransactions.groupTransactionsByAccount(givenListTransaction)

    assert(Map(
      "accountB" -> List(
        Transaction("any","accountB",8,"any",500.0),
        Transaction("any","accountB",5,"any",100.0),
        Transaction("any","accountB",5,"any",80.0)
      ),
      "accountA" -> List(
        Transaction("any","accountA",20,"any",10.0),
        Transaction("any","accountA",5,"any",70.0))
    ) == groups
    )
  }
}
