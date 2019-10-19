package quant

import org.scalatest.FunSuite

class StatSpec extends FunSuite{
  test("stat3 can display as table") {
    val stat = StatQ3(1,"accA",20000.0,20000.0,1,Map(
      "BB" -> 0.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 88.0, "CC" -> 0.0, "DD" -> 0.0))

    assert("1|accA|20000.0|20000.0|20000.0|0.0|0.0" == stat.toTable())
  }
}
