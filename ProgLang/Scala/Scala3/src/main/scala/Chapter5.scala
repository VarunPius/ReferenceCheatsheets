import Chapter5.Person

/** *************************************************************************************************
*Chapter 5: Functional Programming
 *
 *This chapter includes:
  *- Functional Programming
 ***************************************************************************************************/

object Chapter5 {
  class Person(name: String):

    // apply method allows us to invoke the instance of the class as object
    // Example is given below in main
    def apply(age: Int): Unit =
      println(s"Age of $name is $age years")


  @main def funcProg() =
    println("FP Main")

    val bob = Person("Bob")
    bob.apply(21)     // apply method allows us to invoke the instance of the class as object
    bob(21)           // Invoking bob as a function; is same as bob.apply(21)

    /*
    Scala runs on JVM; JVM was designed primarily to run on Objects, so it knows what an object is but doesn't a function as first class citizen.

    In functional programming, functions as first class element, meaning we should be able to work them like we work with any other values.
    Therefore, we should be able to:
      - compose function
      - pass functions as arguments
      - return functions as results

    To get Scala to work with JVM, creators therefore created something called as FunctionX
    */

}
