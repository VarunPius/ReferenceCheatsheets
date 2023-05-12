/****************************************************************************************************
Chapter 5: Functional Programming

This chapter includes:
  - Functional Programming
****************************************************************************************************/

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

  // Higher order functions (HOF):
  // These are Functions that take functions as arguments or returns functions
  val aMappedList = List(1,2,3).map(x => x + 1) // Here, the argument of map is a HOF which is an anonymous function
  println("HOF List:" + aMappedList)    // HOF List:List(2, 3, 4)
  // aMappedList is a different list than the original supplied list (1,2,3)

  // Another way to write (for practice)
  val aMappedList2: List[Int] = List(4,5,6).map(x => x + 2)   // (Int => Int)
  println("HOF List2:" + aMappedList2)    // HOF List2:List(6, 7, 8)

  // Flatmap
  val aFlatmappedList = List(1,4,5).flatMap(x => List(x, 2*x))    // (Int => List)
  println("Flatmap: " + aFlatmappedList)    //List(1, 2, 4, 8, 5, 10)
  // alternative syntax
  val aFlatmappedList2 = List(1,4,5).flatMap {
    x => List(x, 3*x)
  }
  println("Flatmap2: " + aFlatmappedList2)    //List(1, 2, 4, 8, 5, 10)

  // Filter
  val aFilteredList = List(1,2,3,4,5,6).filter(x => x < 3)    // (Int => Boolean)
  println("Filtered list: " + aFilteredList)    // Filtered listList(1, 2)
  // ALternative shorter syntax
  val aFilteredList2 = List(1, 2, 3, 4, 5, 6).filter(_ <= 3) //
  println("Filtered list 2: " + aFilteredList2) // Filtered listList(1, 2, 3)
  // This is equivalent to (x => x <=3)

  // Chaining
  // Since in scala, all operations yield immutable datatypes, we can chain them one after another
  // Eg: all pairs of number 1,2,3 and letters a,b,c
  val pairs = List(1,2,3).flatMap(num => List('a','b','c').map(letter => s"$num-$letter"))
  println("All pairs: " + pairs)  // All pairs: List(1-a, 1-b, 1-c, 2-a, 2-b, 2-c, 3-a, 3-b, 3-c)

  // for comprehensions
  val altPairs = for
    num <- List(1, 2, 3)
    letter <- List('a', 'b', 'c')
  yield s"$num-$letter"
  println("Alt pairs: " + altPairs)   // Alt pairs: List(1-a, 1-b, 1-c, 2-a, 2-b, 2-c, 3-a, 3-b, 3-c)
  // Remember for comprehensions are not for loops, they are just another way to chain
  /*
  can also be written as:
  val altPairs = for{
      num <- List(1,2,3)
      letter <- List('a','b','c')
    } yield s"$num-$letter"
  */

  // Collections ****************************************************/
  // Lists
  val aList = List(1,2,3,4,5)
  val first = aList.head
  val rest = aList.tail
  val aPrependedList = 0::aList // List(0,1,2,3,4,5)
  val aExtendedList = 0 +: aList :+ 6   // List(0,1,2,3,4,5,6)

  // Sequences
  // In Java terms
  // Scala's Seq would be Java's List
  // and Scala 's List would be Java's LinkedList. There are more technical details.
  val aSequence: Seq[Int] = Seq(1,2,3,4)  // Similar to Seq.apply(1,2,3,4)
                                          // as mentioned above in discussion about apply
  val accessedElement = aSequence(1)      // similar to aSequence.apply(1); will yield element at index 1: 2

  // Vectors
  // Particular type sequence good for large data
  // It is a fast sequence implementation

}
