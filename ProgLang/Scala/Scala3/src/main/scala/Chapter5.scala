/** *************************************************************************************************
*Chapter 5: Functional Programming
 *
 *This chapter includes:
  *- Functional Programming
 ***************************************************************************************************/

object Chapter5 {
  println("Chapter 5: Functional Programming")
  @main def funcProg() =
    println("FP Main")

  class Person(name: String):
    // apply method allows us to invoke the instance of the class as object
    // Example is given below in main
    def apply(age: Int): Unit =
      println(s"Age of $name is $age years")

  val bob = Person("Bob")
  bob.apply(21)     // apply method allows us to invoke the instance of the class as object
  bob(21)           // Invoking bob as a function; is same as bob.apply(21)
  //Output for both before: Age of Bob is 21 years

  /*
  Scala runs on JVM; JVM was designed primarily to run on Objects, so it knows what an object is but doesn't a function as first class citizen.

  In functional programming, functions as first class element, meaning we should be able to work them like we work with any other values.
  Therefore, we should be able to:
    - compose function
    - pass functions as arguments
    - return functions as results

  To get Scala to work with JVM, creators therefore created something called as FunctionX
  Conclusion: FunctionX = Function1, Function2, ... Function22 which is max number of implementations
  */

  val simpleIncrementor = new Function[Int, Int]:
    override def apply(arg: Int): Int = arg + 1
  
  println("Incrementor1: " + simpleIncrementor.apply(23))   // 24
  println("Incrementor2: " + simpleIncrementor(23))         // same as simpleIncrementer.apply(23)

  // defined a function!
  // ALL SCALA FUNCTIONS ARE INSTANCES OF THESE FUNCTION_X TYPES
  // Effectively, scala created an object of type Function_X

  // function with 2 arguments and a String return type
  val stringConcatenator = new Function2[String, String, String]:
    override def apply(arg1: String, arg2: String): String = arg1 + arg2
  
  println("Str Concatenator: " + stringConcatenator("test", "data"))    // Str Concatenator: testdata


  // Syntax sugar
  val doubler: Function1[Int, Int] = (x: Int) => 2*x
  val doubler2: Int => Int = (x: Int) => 2 * x
  val doubler3 = (x: Int) => 2 * x

  println("Doubler: " + doubler(10))          //Doubler: 20
  println("Doubler2: " + doubler2(10))        //Doubler2: 20
  println("Doubler3: " + doubler3(20))        //Doubler3: 40

  /*
  Equivalent to writing
  val doubler = new Function1[Int, Int]:
    override def apply(arg: Int): Int = 2*arg
  */

  //Continuing with same examples
  val stringConcatenator2: Function2[String, String, String] = (arg1: String, arg2: String) => arg1 + arg2
  val stringConcatenator3: (String, String) => String = (arg1: String, arg2: String) => arg1 + arg2
  val stringConcatenator4 = (arg1: String, arg2: String) => arg1 + arg2

  println("Str Concatenator2: " + stringConcatenator2("test", "data"))      //Str Concatenator2: testdata
  println("Str Concatenator3: " + stringConcatenator3("test", "data"))      //Str Concatenator3: testdata
  println("Str Concatenator4: " + stringConcatenator4("test", "data"))      //Str Concatenator4: testdata


}
