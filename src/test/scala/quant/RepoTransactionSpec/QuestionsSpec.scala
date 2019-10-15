package quant.RepoTransactionSpec

import cats.effect.IO
import org.scalatest.FunSuite
import quant.RepoTransaction.ListTransaction
import quant.{ErrorRead, FileDontExist, RepoTransaction, Transaction}

class QuestionsSpec extends FunSuite {
  test("Question1: can calculate total transactions value per day") {
    val givenListTransaction = List(
      Transaction("any","any",20,"any",161.01),
      Transaction("any","any",5,"any",62.03),
      Transaction("any","any",20,"any",987.04),
      Transaction("any","any",8,"any",909.93),
      Transaction("any","any",5,"any",114.63)
    )

    val dayMapTotalvalue = RepoTransaction.findTotalByDay(givenListTransaction)

    assert(Map(
      8 -> 909.93,
      20 -> 1148.05,
      5 -> 176.66
    ) == dayMapTotalvalue)
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

  test("Question3: Calculate stats") {
    val givenListTransaction = List(
      Transaction("T000621","accountA",20,"catA1",10),
      Transaction("T000622","accountA",5,"catA1",70),
      Transaction("T000623","accountA",20,"catA1",20),
      Transaction("T000623","accountA",20,"catA2",30),
      Transaction("T000623","accountA",20,"catA2",100),
      Transaction("T000624","accountB",8,"catB1",500),
      Transaction("T000625","accountB",5,"catB1",100),
      Transaction("T000625","accountB",5,"catB2",80)
    )

    val resume = RepoTransaction.accountMapAvg(givenListTransaction)

    assert(Map(
      "accountA" -> Map("catA2" -> 65.0, "catA1" -> 33.333333333333336),
      "accountB" -> Map("catB2" -> 80.0, "catB1" -> 300.0))
      == resume)
  }
}
