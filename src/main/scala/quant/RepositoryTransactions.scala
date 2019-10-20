package quant

import cats.effect.IO
import scala.io.Source
import cats.implicits._
import scala.util.{Success, Try}

object RepositoryTransactions {

  type ListTransaction = List[Transaction]

  def findAll(resourceFilename: String): IO[Either[ErrorRead, ListTransaction]] = IO {
    val file = getClass.getResource(resourceFilename)

    val transactions: Try[List[Transaction]] = Try {
      for {
        line <-  Source.fromFile(file.getPath).getLines().drop(1).toList
        fields = line.split(',')
      } yield Transaction(fields(0), fields(1), fields(2).toInt, fields(3), fields(4).toDouble)
    }

    transactions match {
      case Success(transactions) => transactions.asRight[ErrorRead]
      case _ => FileDontExist.asLeft[ListTransaction]
    }
  }
}
