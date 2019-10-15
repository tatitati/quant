package quant.RepoTransactionSpec

import cats.effect.IO
import org.scalatest.FunSuite
import quant.RepoTransaction.ListTransaction
import quant.{ErrorRead, FileDontExist, RepoTransaction, Transaction}

class Question3Spec extends FunSuite {
  test("Can select proper window of transactions") {
    val givenListTransaction = List(
      Transaction("any","any",1,"any",161.01),
      Transaction("any","any",2,"any",62.03),
      Transaction("any","any",3,"any",987.04),
      Transaction("any","any",4,"any",909.93),
      Transaction("any","any",5,"any",114.63),
      Transaction("any","any",6,"any",114.63),
      Transaction("any","any",7,"any",114.63)
    )

    val transactionsInWindow = RepoTransaction.selectTransactionsInWindow(givenListTransaction, 7)

    assert(
      List(
        Transaction("any","any",2,"any",62.03),
        Transaction("any","any",3,"any",987.04),
        Transaction("any","any",4,"any",909.93),
        Transaction("any","any",5,"any",114.63),
        Transaction("any","any",6,"any",114.63)
      ) == transactionsInWindow
    )
  }

  test("Question3: Calculate stats") {
    val givenListTransaction = List(
      Transaction("T000621","accountA",20,"catA1",10),
      Transaction("T000622","accountA",5,"catA1",70),
      Transaction("T000623","accountA",20,"catA1",20),
      Transaction("T000623","accountA",20,"catA2",30),
      Transaction("T000623","accountA",20,"catA2",100),
      Transaction("T000624","accountB",8,"catB1",500),
      Transaction("T000625","accountB",5,"catB1",100),
      Transaction("T000625","accountB",5,"catB2",80)
    )

   // todo

  }
}
