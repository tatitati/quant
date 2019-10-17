package quant

case class Stat(
   day: Int,
   account: String,
   maximum: Double,
   avg: Double,
   totalPerCategory: Map[String, Double]
 )
