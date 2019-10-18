package quant

sealed trait Stat {
  def toTable(): String
}

final case class Stat1(day: Int, total: Double) extends Stat{
  def toTable(): String = {
    s"""
       |$day|$total""".stripMargin
  }
}

final case class Stat2(account: String, category: String, total: Double, fromNItems: Int) extends Stat{
  def toTable(): String = {
    val avg = total/fromNItems
    s"""
       |$account|$category|$avg""".stripMargin
  }
}

final case class Stat3(
      day: Int,
      account: String,
      max: Double,
      total: Double,
      fromNItems: Int,
      categoryMapTotal: Map[String, Double] = Map("AA" -> 0.0, "BB" -> 0.0, "CC" -> 0.0, "DD" -> 0.0, "EE" -> 0.0, "FF" -> 0.0, "GG" -> 0.0)
  ) extends Stat {
  override def toString(): String = {
    s"""
       |Stat3($day,"$account",$max,$total,$fromNItems,Map("AA" -> ${categoryMapTotal("AA")}, "BB" -> ${categoryMapTotal("BB")}, "CC" -> ${categoryMapTotal("CC")}, "DD" -> ${categoryMapTotal("DD")}, "EE" -> ${categoryMapTotal("EE")}, "FF" -> ${categoryMapTotal("FF")}, "GG" -> ${categoryMapTotal("GG")}))[""".stripMargin
  }

  def toTable(): String = {
    val avg = total / fromNItems
    s"$day|$account|$max|$avg|${categoryMapTotal("AA")}|${categoryMapTotal("CC")}|${categoryMapTotal("FF")}".stripMargin
  }
}
