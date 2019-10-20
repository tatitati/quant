package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, StatQ2, Transaction}

class SumByAccountAndCategorySpec extends FunSuite{
  test("Creates a new stat if there is no info for the new transaction") {
    val stat = OpTransactions.processByAccountAndCatNewTransaction(
      Transaction("any","accA",1,"cat1",20000)
    )

    assert(
      Map(("accA","cat1") -> StatQ2("accA","cat1",20000.0,1))
      == stat)
  }

  test("Can update the proper stat when received a new transaction") {
    val givenTransaction = Transaction("any","accA",1,"cat1",20000)
    val givenStats = Map(
      ("accA","cat1") -> StatQ2("accA","cat1",20888.0,2),
      ("accA","cat2") -> StatQ2("accA","cat2",555.0,1)
    )

    val result = OpTransactions.processByAccountAndCatNewTransaction(
      givenTransaction, givenStats
    )

    assert(Map(
      ("accA","cat2") -> StatQ2("accA","cat2",555.0,1),
      ("accA","cat1") -> StatQ2("accA","cat1",40888.0,3) // updated
    ) == result)
  }
}
