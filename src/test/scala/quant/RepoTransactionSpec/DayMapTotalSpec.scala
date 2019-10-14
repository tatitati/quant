package quant.RepoTransactionSpec

import cats.effect.IO
import org.scalatest.FunSuite
import quant.RepoTransaction.listTransaction
import quant.{ErrorRead, FileDontExist, RepoTransaction, Transaction}

class DayMapTotalSpec extends FunSuite {

  val givenListTransaction = List(
    Transaction("T000621","A36",20,"BB",161.01),
    Transaction("T000622","A45",5,"CC",62.03),
    Transaction("T000623","A22",20,"CC",987.04),
    Transaction("T000624","A23",8,"CC",909.93),
    Transaction("T000625","A26",5,"DD",114.63)
  )

  test("can group by date") {
    val grouped = givenListTransaction.groupBy(_.transactionDay)

    assert(
      Map(
        8 -> List(Transaction("T000624","A23",8,"CC",909.93)),
        20 -> List(Transaction("T000621","A36",20,"BB",161.01), Transaction("T000623","A22",20,"CC",987.04)),
        5 -> List(Transaction("T000622","A45",5,"CC",62.03), Transaction("T000625","A26",5,"DD",114.63))
      ) == grouped
    )
  }

  test("can group read transactions") {
    val result = RepoTransaction.findTotalByDay("/transactions.txt")

    println(result.unsafeRunSync())
  }
}
