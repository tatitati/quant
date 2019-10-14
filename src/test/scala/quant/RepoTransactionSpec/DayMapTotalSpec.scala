package quant.RepoTransactionSpec

import org.scalatest.FunSuite
import quant.Transaction

class DayMapTotalSpec extends FunSuite {

  test("can group by date") {
    val transactions = List(
      Transaction("T000621","A36",20,"BB",161.01),
      Transaction("T000622","A45",5,"CC",62.03),
      Transaction("T000623","A22",20,"CC",987.04),
      Transaction("T000624","A23",8,"CC",909.93),
      Transaction("T000625","A26",5,"DD",114.63)
    )

    val grouped = transactions.groupBy(_.transactionDay)

    assert(
      Map(
        8 -> List(Transaction("T000624","A23",8,"CC",909.93)),
        20 -> List(Transaction("T000621","A36",20,"BB",161.01), Transaction("T000623","A22",20,"CC",987.04), Transaction("T000626","A25",20,"FF",288.74)),
        5 -> List(Transaction("T000622","A45",5,"CC",62.03), Transaction("T000625","A26",5,"DD",114.63))
      ) == grouped
    )
  }
}
