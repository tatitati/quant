package quant.RepoTransactionSpec

import cats.effect.IO

import scala.io.Source
import cats.data._
import cats.effect._
import cats.implicits._
import org.scalatest.FunSuite
import quant.RepoTransaction.{accountMapAvg, listTransaction}
import quant.{ErrorRead, RepoTransaction}

import scala.util.{Success, Try}

class AccountMapAvgSpec extends FunSuite {

  test("one") {
    val result: IO[Either[ErrorRead, listTransaction]] = RepoTransaction.findAll("/transactions.txt")

    val mapped = result.map {
      case Left(error) => Either.left(error)
      case Right(listTransaction) => Either.right(
        listTransaction
          .groupBy(_.accountId)
          .mapValues{
            _.groupBy(_.category)
            .mapValues(_.size)
          }
        )
    }



    println(mapped.unsafeRunSync)
  }

  test("two") {
    val givenMaps = Map(
      "A9" -> Map(
        "BB" -> 3,
        "AA" -> 5,
        "FF" -> 1,
        "GG" -> 2,
        "EE" -> 4,
        "CC" -> 2,
        "DD" -> 4
      )
    )

    val result = for{
      (key, submap) <- givenMaps
    } yield (key, submap.map(_._2).sum)

    assert(Map("A9" -> 21) == result)
  }
}
