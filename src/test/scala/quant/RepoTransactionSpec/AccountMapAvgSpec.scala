package quant.RepoTransactionSpec

import org.scalatest.FunSuite
import quant.{RepoTransaction, Transaction}

class AccountMapAvgSpec extends FunSuite {

  test("RepoTransaction.accountMapAvg()") {
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
