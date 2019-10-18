package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, Stat2, Transaction}

class SumByAccountAndCategorySpec extends FunSuite{
  test("question2 better") {
    val statsA = OpTransactions.sumByAccountAndCategory(
      Transaction("tr0","accA",1,"cat1",20000)
    )

    val statsB = OpTransactions.sumByAccountAndCategory(
      Transaction("tr1","accB",1,"cat1",10000), statsA
    )

    val statsC = OpTransactions.sumByAccountAndCategory(
      Transaction("tr2","accA",1,"cat1",888), statsB
    )

    val statsD = OpTransactions.sumByAccountAndCategory(
      Transaction("tr3","accA",1,"cat2",555), statsC
    )

    assert(List(
      Stat2("accA","cat1",20888.0,2),
      Stat2("accB","cat1",10000.0,1),
      Stat2("accA","cat2",555.0,1)
    ) == statsD)
  }
}
