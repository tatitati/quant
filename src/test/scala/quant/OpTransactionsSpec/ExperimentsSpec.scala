package quant.OpTransactionsSpec

import org.scalatest.FunSuite

class ExperimentsSpec extends FunSuite {

  test("groupby experiment") {
    case class Person(name: String, city: String, color: String)
    val persons = List(
      Person("fran", "madrid", "white"),
      Person("diego", "madrid", "white"),
      Person("raul", "barcelona", "black"),
      Person("antonio", "madrid", "black"),
    )

    var mygroup = persons.groupBy{x => (x.color, x.city)}

    //println(mygroup)

    //    Map((
    //      black,barcelona) -> List(Person(raul,barcelona,black)),
    //      (black,madrid) -> List(Person(antonio,madrid,black)),
    //      (white,madrid) -> List(
    //            Person(fran,madrid,white),
    //            Person(diego,madrid,white))
    //    )

    //    println(mygroup("white", "madrid"))

    //    List(
    //    Person(fran,madrid,white),
    //    Person(diego,madrid,white))


    mygroup += (("black", "madrid") -> List(Person("sancho", "madrid", "black")))

    //    Map(
    //      (black,barcelona) -> List(Person(raul,barcelona,black)),
    //      (black,madrid) -> List(Person(sancho,madrid,black)),
    //      (white,madrid) -> List(Person(fran,madrid,white), Person(diego,madrid,white)))

  }
}
