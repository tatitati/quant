package quant

import org.scalatest.FunSuite

import scala.io.Source

class RepoTransactionsSpec extends FunSuite {

  test("can get URL of real file") {
    val file = getClass.getResource("/transactions.txt")

    // this test will fail in any other environment as is not environment agnostic.
    // A classic solution to this issue is creating a defined environment using docker, virtualbox, etc.
    assert("/Users/tati/Desktop/scala_lab/quant/target/scala-2.12/classes/transactions.txt" == file.getPath)
  }

  test("Can really read a file using the provided code") {
    val file = getClass.getResource("/transactions.txt")
    val transactionslines = Source.fromFile(file.getPath).getLines().drop(1).toList

    assert(991 == transactionslines.length)
    assert("T0001,A27,1,GG,338.11" == transactionslines(0))
  }

  test("Can read transactions") {
    val result = RepoTransaction.findAll("/transactions.txt").unsafeRunSync()

    assert(result.isRight)
    assert(991 == result.right.get.length)
  }

  test("Cannot read transactions") {
    val result = RepoTransaction.findAll("/unknownfile.txt").unsafeRunSync()

    assert(result.isLeft)
  }
}
