package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, Stat2, Transaction}

class SumByAccountAndCategorySpec extends FunSuite{
  test("question2 concept") {
    val statsA = OpTransactions.processByAccountAndCatNewTransaction(
      Transaction("any","accA",1,"cat1",20000)
    )

    val statsB = OpTransactions.processByAccountAndCatNewTransaction(
      Transaction("any","accB",1,"cat1",10000), statsA
    )

    val statsC = OpTransactions.processByAccountAndCatNewTransaction(
      Transaction("any","accA",1,"cat1",888), statsB
    )

    val statsD = OpTransactions.processByAccountAndCatNewTransaction(
      Transaction("any","accA",1,"cat2",555), statsC
    )

    assert(List(
      Stat2("accA","cat1",20888.0,2),
      Stat2("accB","cat1",10000.0,1),
      Stat2("accA","cat2",555.0,1)
    ) == statsD)
  }
}
