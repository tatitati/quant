package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, Stat2, Transaction}

class SumByAccountAndCategorySpec extends FunSuite{
  test("question2 concept") {
    val statsA = OpTransactions.mergeTransactionByAccountAndCategory(
      Transaction("any","accA",1,"cat1",20000)
    )

    val statsB = OpTransactions.mergeTransactionByAccountAndCategory(
      Transaction("any","accB",1,"cat1",10000), statsA
    )

    val statsC = OpTransactions.mergeTransactionByAccountAndCategory(
      Transaction("any","accA",1,"cat1",888), statsB
    )

    val statsD = OpTransactions.mergeTransactionByAccountAndCategory(
      Transaction("any","accA",1,"cat2",555), statsC
    )

    assert(List(
      Stat2("accA","cat1",20888.0,2),
      Stat2("accB","cat1",10000.0,1),
      Stat2("accA","cat2",555.0,1)
    ) == statsD)
  }

  test("I can automate this process"){
    val transactions = List(
      Transaction("any","accA",1,"cat1",20000),
      Transaction("any","accB",1,"cat1",10000),
      Transaction("any","accA",1,"cat1",888),
      Transaction("any","accA",1,"cat2",555)
    )

    val result = OpTransactions.processAllByAccAndCategory(transactions)

    assert(List(
      Stat2("accA","cat1",20888.0,2),
      Stat2("accB","cat1",10000.0,1),
      Stat2("accA","cat2",555.0,1)
    ) == result)
  }
}
