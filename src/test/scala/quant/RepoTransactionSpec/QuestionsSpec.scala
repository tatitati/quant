package quant.RepoTransactionSpec

import cats.effect.IO
import org.scalatest.FunSuite
import quant.RepoTransaction.ListTransaction
import quant.{ErrorRead, FileDontExist, RepoTransaction, Transaction}

class QuestionsSpec extends FunSuite {
  test("Question1: can calculate total transactions value per day") {
    val givenListTransaction = List(
      // transactionId, accountId, transactionDay, category, transactionAmount
      Transaction("T000621","A36",20,"BB",161.01),
      Transaction("T000622","A22",5,"CC",62.03),
      Transaction("T000623","A22",20,"AA",987.04),
      Transaction("T000624","A36",8,"CC",909.93),
      Transaction("T000625","A21",5,"DD",114.63)
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
