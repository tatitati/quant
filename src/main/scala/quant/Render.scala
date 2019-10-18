package quant

import scala.annotation.tailrec

object Render {
  @tailrec
  def run[A <: Stat](stats: List[A], textAcc: String = ""): String = {
    stats match {
      case Nil => textAcc
      case values => run(values.tail, textAcc + values.head.toTable())
    }
  }
}
