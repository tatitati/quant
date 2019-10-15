package quant.RepoTransactionSpec

import cats.effect.IO
import org.scalatest.FunSuite
import quant.RepoTransaction.ListTransaction
import quant.{ErrorRead, FileDontExist, RepoTransaction, Transaction}

class Question2Spec extends FunSuite {
  test("can group transactions by account") {
    val givenListTransaction = List(
      Transaction("any","accountA",20,"any",10),
      Transaction("any","accountA",5,"any",70),
      Transaction("any","accountB",8,"any",500),
      Transaction("any","accountB",5,"any",100),
      Transaction("any","accountB",5,"any",80)
    )

    val groups = RepoTransaction.groupTransactionsByAccount(givenListTransaction)

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

  test("Question2: can calculate the average value of transactions per account for each type of transaction") {
    val givenListTransaction = List(
      Transaction("any","accountA",20,"catA1",10),
      Transaction("any","accountA",5,"catA1",70),
      Transaction("any","accountA",20,"catA1",20),
      Transaction("any","accountA",20,"catA2",30),
      Transaction("any","accountA",20,"catA2",100),
      Transaction("any","accountB",8,"catB1",500),
      Transaction("any","accountB",5,"catB1",100),
      Transaction("any","accountB",5,"catB2",80)
    )

    val resume = RepoTransaction.accountMapAvg(givenListTransaction)

    assert(Map(
      "accountA" -> Map("catA2" -> 65.0, "catA1" -> 33.333333333333336),
      "accountB" -> Map("catB2" -> 80.0, "catB1" -> 300.0))
      == resume)
  }
}
