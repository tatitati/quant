package quant

import quant.OpTransactions.ListTransaction
import scala.io.Source

object RepositoryTransactions {

  def findAll(resourceFilename: String): ListTransaction = {
    val file = getClass.getResource(resourceFilename)

    for {
      line <- Source.fromFile(file.getPath).getLines().drop(1).toList
      fields = line.split(',')
    } yield Transaction(fields(0), fields(1), fields(2).toInt, fields(3), fields(4).toDouble)
  }
}
