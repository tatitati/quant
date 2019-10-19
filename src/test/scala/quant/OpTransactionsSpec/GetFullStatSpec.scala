package quant.OpTransactionsSpec

import org.scalatest.FunSuite
import quant.{OpTransactions, StatQ2, StatQ3, Transaction}

class GetFullStatSpec extends FunSuite {

//  test("Can update full stat as transactions are comming"){
//    val accStats1 = OpTransactions.getFullStat(
//      Transaction("any","accA",1,"AA",20000)
//    )
//
//    val accStats2 = OpTransactions.getFullStat(
//      Transaction("any","accA",2,"BB",20000), accStats1
//    )
//
//    val accStats3 = OpTransactions.getFullStat(
//      Transaction("any","accA",3,"BB",20000), accStats2
//    )
//
//   assert(
//     List(StatQ3(1,"accA",0.0,0.0,1,Map("BB" -> 0.0, "AA" -> 0.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0)))
//       == accStats1)
//
//
//   assert(
//     List(
//       StatQ3(1,"accA",0.0,0.0,1,Map("AA" -> 0.0, "BB" -> 0.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0)),
//       StatQ3(2,"accA",0.0,0.0,1,Map("AA" -> 0.0, "BB" -> 0.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0))
//     ) == accStats2)
////
////   assert(List(
////     Stat3(
////       1,"accA",20000.0,40000.0,2,Map(
////       "BB" -> 20000.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0)),
////     Stat3(2,"accA",20000.0,20000.0,1,Map(
////       "BB" -> 20000.0, "AA" -> 0.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 0.0, "CC" -> 0.0, "DD" -> 0.0))
////   ) == accStats3)
//  }
}
