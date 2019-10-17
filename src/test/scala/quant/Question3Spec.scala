package quant

import org.scalatest.FunSuite

class Question3Spec extends FunSuite {

  test("Can order by day a bunch of transactions") {
    val givenListTransaction = List(
      Transaction("any", "any", 6, "any", 161.01),
      Transaction("any", "any", 3, "any", 62.03),
      Transaction("any", "any", 1, "any", 114.63),
      Transaction("any", "any", 7, "any", 114.63)
    )

    val sortedTransactions = OpTransactions.orderByDayTransactions(givenListTransaction)

    assert(List(
      Transaction("any","any",1,"any",114.63),
      Transaction("any","any",3,"any",62.03),
      Transaction("any","any",6,"any",161.01),
      Transaction("any","any",7,"any",114.63)
    ) == sortedTransactions

    )
  }

  test("Can select proper window of transactions") {
    val givenListTransaction = List(
      Transaction("any","any",6,"any",161.01),
      Transaction("any","any",3,"any",62.03),
      Transaction("any","any",4,"any",987.04),
      Transaction("any","any",5,"any",909.93),
      Transaction("any","any",2,"any",114.63),
      Transaction("any","any",1,"any",114.63),
      Transaction("any","any",7,"any",114.63)
    )

    val transactionsInWindow = OpTransactions.selectTransactionsInWindow(givenListTransaction, 7)

    assert(List(
      Transaction("any","any",6,"any",161.01),
      Transaction("any","any",3,"any",62.03),
      Transaction("any","any",4,"any",987.04),
      Transaction("any","any",5,"any",909.93),
      Transaction("any","any",2,"any",114.63))
      == transactionsInWindow
    )
  }

  test("Can find maximum value of transaction from a bunch of transactions") {
    val givenListTransaction = List(
      Transaction("any","any",1,"any",20),
      Transaction("any","any",2,"any",5),
      Transaction("any","any",3,"any",600),
      Transaction("any","any",4,"any",300)
    )

    val maxValue = OpTransactions.findTransactionWithMaxValue(givenListTransaction)

    assert(600 == maxValue)
  }

  test("Can calculate average value of a bunch of transactions") {
    val givenListTransaction = List(
      Transaction("any","any",1,"any",20),
      Transaction("any","any",2,"any",5),
      Transaction("any","any",3,"any",60),
      Transaction("any","any",4,"any",100)
    )

    val maxValue = OpTransactions.averageValue(givenListTransaction)

    assert(46.25 == maxValue)
  }

  test("i can have all the stats from a bunch of transactions") {
    val givenTransactionsForAccount = List(
      Transaction("any","accountA",20,"catA1",10),
      Transaction("any","accountA",20,"catA1",70),
      Transaction("any","accountA",20,"catA1",20),
      Transaction("any","accountA",20,"catA2",30),
      Transaction("any","accountA",20,"catA2",100),
      Transaction("any","accountA",20,"catB1",500),
      Transaction("any","accountA",20,"catB1",100)
    )



    val maximum = OpTransactions.findTransactionWithMaxValue(givenTransactionsForAccount)
    val avg = OpTransactions.averageValue(givenTransactionsForAccount)
    val totalByCategory = OpTransactions.groupTransactionsByCategory(givenTransactionsForAccount)
      .mapValues(OpTransactions.sumTransactions(_))

    Stat(20, "accountA", maximum, avg, totalByCategory)
  }

  test("Question 3") {
    val givenIsDay = 7

    val givenTransactions = List(
      Transaction("any","accountA",20,"catA1",10),
      Transaction("any","accountA",2,"catA1",70),
      Transaction("any","accountA",14,"catA1",20),
      Transaction("any","accountA",20,"catA2",30),
      Transaction("any","accountA",12,"catA2",100),
      Transaction("any","accountB",8,"catB1",500),
      Transaction("any","accountB",20,"catB1",100),
      Transaction("any","accountB",5,"catB2",80)
    )

    val window =  (a: List[Transaction]) => OpTransactions.selectTransactionsInWindow(a, 16)
    val sorted =  (a: List[Transaction]) => OpTransactions.orderByDayTransactions(a)

//    val windowSorted: List[Transaction] = (_: OpTransactions.ListTransaction) => window andThen sorted

//    val trByDay = OpTransactions
//      .groupTransactionsByDay(windowSorted)
//      .mapValues(OpTransactions.groupTransactionsByAccount(_))




  }
}
