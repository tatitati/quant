package quant.RepoTransactionSpec

import cats.effect.IO
import org.scalatest.FunSuite
import quant.RepoTransaction.{ListTransaction, groupTransactionsByDay, sumTransactions}
import quant.{ErrorRead, FileDontExist, RepoTransaction, Transaction}

class Question1Spec extends FunSuite {
  test("Can sum up any group of transactions") {
    val givenListTransaction = List(
      Transaction("any","any",44,"any",20),
      Transaction("any","any",55,"any",5),
      Transaction("any","any",33,"any",100)
    )

    val total = RepoTransaction.sumTransactions(givenListTransaction)

    assert(125.0 == total)
  }

  test("Can group by day any group of transactions") {
    val givenListTransaction = List(
      Transaction("any","any",44,"any",20),
      Transaction("any","any",55,"any",5),
      Transaction("any","any",44,"any",100)
    )

    val dayMapTransactions = RepoTransaction.groupTransactionsByDay(givenListTransaction)

    assert(Map(
      44 -> List(Transaction("any","any",44,"any",20.0), Transaction("any","any",44,"any",100.0)),
      55 -> List(Transaction("any","any",55,"any",5.0)))
      == dayMapTransactions)
  }

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
}
