package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, StatQ3, Transaction}

class ProcessWindowByTransactionSpec extends FunSuite {
  test("I can create the new stat for the transaction in case there is no hitorial for this transactions") {
    val givenTransaction = Transaction("any","accA",6,"BB",20000)

    val statTransaction: StatQ3 = OpTransactions.processWindowByTransaction(givenTransaction)

    assert(
      StatQ3(
        6,
        "accA",
        max = 0.0,
        total = 0.0,
        fromNItems = 0,
        Map("AA" -> 0.0, "BB" -> 0.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0)
      ) == statTransaction)
  }

  test("Process step by step") {
    val givenTransaction = Transaction("any","accA",6,"BB",20000)
    val givenStatWindow = List()

    val stat = OpTransactions.processWindowByTransaction(givenTransaction, givenStatWindow)

    assert(
      StatQ3(
        6,
        "accA",
        max = 0.0,
        total = 0.0,
        fromNItems = 0,
        Map("AA" -> 0.0, "BB" -> 0.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0)
      ) == stat)
  }

  test("I can obtain the new stat for the new transaction from the previous window") {
    val givenTransaction = Transaction("any","accA",6,"BB",20000)
    val givenWindow = List(
      StatQ3(1,"accA",150.0,20.0,4,Map("AA" -> 5.0, "BB" -> 20000.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0)),
      StatQ3(3,"accA",100.0,100.0,3,Map("AA" -> 100.0, "BB" -> 20000.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0)),
      StatQ3(5,"accA",500.0,55.0,5,Map("AA" -> 1.0, "BB" -> 20000.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0))
    )

    val stat: StatQ3 = OpTransactions.processWindowByTransaction(givenTransaction, givenWindow)

    assert(
      StatQ3(
        day = 6,
        account = "accA",
        max = 500.0,
        total = 175.0,
        fromNItems = 12,
        Map("AA" -> 106.0, "BB" -> 0.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0)
      ) == stat)
  }
}
