package quant.RepoTransactionSpec

import org.scalatest.FunSuite
import quant.{RepoTransaction, Transaction}

class GroupTransactionsByDaySpec extends FunSuite {

  test("RepoTransactoin.groupTransactionsByDay()") {
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
}
