package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.OpTransactions.ListTransaction
import quant.{OpTransactions, StatQ3, Transaction}

class GetWindowSpec extends FunSuite {
  test("For an account I can get the previous transactions that are needed to be processed"){
      val givenTransaction = Transaction("any","accA",6,"any",50000)
      val givenStats = List(
        StatQ3(1,"accA",20000.0,20000.0,1,Map("BB" -> 0.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0)),
        StatQ3(2,"accA",20000.0,20000.0,1,Map("BB" -> 0.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0)),
        StatQ3(2,"accB",20000.0,20000.0,1,Map("BB" -> 0.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0)),
        StatQ3(3,"accA",20000.0,20000.0,1,Map("BB" -> 0.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0)),
        StatQ3(8,"accA",20000.0,20000.0,1,Map("BB" -> 0.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0))
      )

      val result = OpTransactions.getWindow(givenTransaction, givenStats)

      assert(List(
        StatQ3(1,"accA",20000.0,20000.0,1,Map("BB" -> 0.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0)),
        StatQ3(2,"accA",20000.0,20000.0,1,Map("BB" -> 0.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0)),
        StatQ3(3,"accA",20000.0,20000.0,1,Map("BB" -> 0.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0))
      ) == result)
  }
}
