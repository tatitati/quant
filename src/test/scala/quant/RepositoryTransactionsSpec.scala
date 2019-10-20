package quant

import org.scalatest.FunSuite
import quant.RepositoryTransactions.ListTransaction

class RepositoryTransactionsSpec extends FunSuite {
  test("Right if we can read the file") {
    val result: Either[ErrorRead, ListTransaction] = RepositoryTransactions
      .findAll("/transactions.txt")
      .unsafeRunSync()

    assert(result.isRight)
  }

  test("Is left if file cannot be read") {
    val result: Either[ErrorRead, ListTransaction] = RepositoryTransactions
      .findAll("/unknownfile.txt")
      .unsafeRunSync()

    assert(result.isLeft)
  }
}
