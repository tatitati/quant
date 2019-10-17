package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, Transaction}

class FindTotalByTypeSpec extends FunSuite {

  test("RepoTransaction.findTotalByType()") {
    val givenListTransaction = List(
      Transaction("any","any",1,"AA",20),
      Transaction("any","any",2,"DD",5),
      Transaction("any","any",3,"AA",60),
      Transaction("any","any",4,"CC",100),
      Transaction("any","any",5,"DD",1000)
    )

    val valuePerType = OpTransactions.findTotalByType(givenListTransaction)

    assert(Map(
      "DD" -> 1005.0,
      "AA" -> 80.0,
      "CC" -> 100.0
    ) == valuePerType
    )
  }
}
