package quant

import org.scalatest.FunSuite

class StatSpec extends FunSuite{
  test("stat1 can display as table") {
    val stat = Stat1(2,10000.0)

    assert("2|10000.0" == stat.toTable())
  }

  test("stat2 can display as table") {
    val stat = Stat2("accA","cat1",20888.0,2)

    assert("accA|cat1|10444.0" == stat.toTable())
  }

  test("stat3 can display as table") {
    val stat = Stat3(1,"accA",20000.0,20000.0,1,Map(
      "BB" -> 0.0, "AA" -> 20000.0, "FF" -> 0.0, "GG" -> 0.0, "EE" -> 88.0, "CC" -> 0.0, "DD" -> 0.0))

    assert("1|accA|20000.0|20000.0|20000.0|0.0|0.0|0.0|88.0|0.0|0.0" == stat.toTable())
  }
}
