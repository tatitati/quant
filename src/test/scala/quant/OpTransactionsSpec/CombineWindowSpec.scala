package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, StatQ3, Transaction}

class CombineWindowSpec extends FunSuite {
  test("can create a new stat from previous ones") {
    val givenTransaction = Transaction("any","accA",6,"BB",20000)
    val givenWindow = List(
      StatQ3(1,"accA",150.0,20000.0,4,Map("AA" -> 5.0, "BB" -> 20000.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0)),
      StatQ3(3,"accA",100.0,20000.0,3,Map("AA" -> 100.0, "BB" -> 20000.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0)),
      StatQ3(5,"accA",500.0,20000.0,5,Map("AA" -> 1.0, "BB" -> 20000.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0))
    )

    val stat: StatQ3 = OpTransactions.combineWindow(givenTransaction, givenWindow)

    assert(
      StatQ3(6,"accA",500.0,60000.0,12,Map("AA" -> 106.0, "BB" -> 0.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0))
      == stat)
  }

  test("Edge case") {
    val givenTransaction = Transaction("tr0","accA",6,"BB",20000)
    val givenWindow = List()

    val stat: StatQ3 = OpTransactions.combineWindow(givenTransaction, givenWindow)

    assert(
      StatQ3(6,"accA",0.0,0.0,0,Map("AA" -> 0.0, "BB" -> 0.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0))
        == stat)
  }
}
