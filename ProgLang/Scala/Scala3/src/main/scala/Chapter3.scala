/***************************************************************************************************
Chapter 3: Functions and Methods

This chapter includes:
  - Methods
  - currying
 ***************************************************************************************************/


object Chapter3 {
  @main def mainChp3 =
    println("Chapter 3: Methods & control structures")
    
    println("--------------------------------------------------")
    println("++++++++++ Control structures start here +++++++++")
    println("--------------------------------------------------")
    controlStructures

    println("--------------------------------------------------")
    println("++++++++++ Methods and functions start here ++++++")
    println("--------------------------------------------------")
    val sum1 = addNum(2,3)
    val sum2 = addNum2(4,5)
    println("Sum: " + sum1 + " | " + sum2)
    val prod1 = prodNum(12, 13)
    val prod2 = prodNum(15)   // if value of a was set, this wouldn't have worked.
                              // So all default values of partial num of parameters are set at end
                              // here, 15 is set for a, while b takes default value
    val prod3 = prodNum2()
    println("Product: " + prod1 + " | " + prod2 + " | " + prod3)

    println("--------------------------------------------------")
    println("++++++++ Higher order methods and functions ++++++")
    println("--------------------------------------------------")
    higherOrderFunctions

  def controlStructures =
    println("-- If Block --------------------------------------")
    val x = 10
    // If uses if-then or if-then-else format in scala
    if x == 10 then println("if1: Equals")

    if x == 10 then
      println("If2: Equals again")
      println("Value of x: " + x)

    if x < 5 then
      println("If3: Less than")
    else if x > 20 then
      println("If3: greater than")
    else
      println("If3: Equals")
    // YOu may also include an optional end if statement; though not necessary

    // For Loops
    println("-- For loop --------------------------------------")
    val idx = Seq(1,2,3,4)
    for i <- idx do println("For loop index: " + i)
    for i <- idx
    do
      val x = i * 2
      println(s"For Calc: i = $i | x = $x")

    //Multiple generators:
    for
      i <- 1 to 2
      j <- 'a' to 'b'
      k <- 1 to 10 by 5
    do
      println(s"Multiple for: i = $i | j = $j | k = $k")

    // Guards: for loops can also contain if statements, which are known as guards:
    for
      i <- 1 to 5
      if i%2 == 0
    do
      println(s"For Loop guards: $i")

    for
      i <- 1 to 10
      if i > 3
      if i <= 6
      if i%2 == 0
    do
      println(s"Multiple for loop Guards: $i")

    // Loop with maps
    val states = Map("CA" -> "California", "AB" -> "Alberta")
    for (abbr, state) <- states do println(s"$abbr : $state")

    // for loop expressions:
    forExpressions
    val aForBody = forAsBodyOfMethod(List(1,3,5,6,7))
    println(s"For expressions as body of method: $aForBody")

    // While loop
    println("-- While loop ------------------------------------")
    var i = 0
    while i < 3 do
      println(s"While loop: $i")
      i += 1      // needs to be var and not val

    // Match expressions
    println("-- Match expressions -----------------------------")
    matchExpressions


  def forExpressions =
    println("-- For expressions -------------------------------")
    // while in previous examples we used for loops to print values, we can also use them to return values.
    // This is called `for expressions` and done using the `yield` keyword
    val list =
      for i <-10 to 12
      yield i*2
    println(s"For expressions: $list")

    val list2 = (10 to 12).map(i => i*3)
    println(s"For expression2: $list2")

    val names = Seq("_olivia", "_isabella")
    val capNames = for name <- names yield
      val nameWOUnderscore = name.drop(1)
      val capName = nameWOUnderscore.capitalize
      capName
    println(s"For expressions complex example: $capNames")


  def forAsBodyOfMethod(xs: List[Int]): List[Int] =
    for
      x <- xs
      if x >= 5
      if x <= 10
    yield x


  def matchExpressions =
    val i = 4
    val day = i match
      case 0 => "Sunday"
      case 1 => "Monday"
      case 2 => "Tuesday"
      case 3 => "Wednesday"
      case 4 => "Thursday"
      case 5 => "Friday"
      case 6 => "Saturday"
      case _ => "invalid day"
    println(s"MatchExpr: Day: $day")

    // When you need to access the catch-all, default value in a match expression,
    // just provide a variable name on the left side of the case statement instead of _,
    // and then use that variable name on the right side of the statement as needed:
    val N = 4
    i match
      case 0 => println("defaultMatch: 0")
      case 1 => println("defaultMatch: 1")
      case N => println("defaultMatch: 12")
      case n => println(s"defaultMatch: You chose $n")

    // Multiple matches in single line:
    var x = 3
    val evenOdd = x match
      case 1 | 3 | 5 | 7 | 9 => println("evenOdd: Odd number")
      case 0 | 2 | 4 | 6 | 8 => println("evenOdd: Even number")
      case _ => println("evenOdd: Invalid number")

    // Match expression: using if
    x = 2
    val party = 4
    party match
      case 1 => println("Party: Stag")
      case x if x == 2 || x == 3 => println("Party: 2's a party, 3's a crowd")
      case x if x > 3 => println("Party: 4+ that's a party")
      case _ => println("Party: you don't have anyone")
      // here x denotes party AND NOT the value x; this is important to remember
      // this is different from above

    val r = 14
    r match
      case x if 0 to 9 contains x => println("Match Range case1")
      case x if 10 to 19 contains x => println("Match Range case2")
      case _ => println("Match Range case3")


    // Match expr as body of method:
    println("isTruthy: " + isTruthy(0))
    println("isTruthy: " + isTruthy(1))
    println("isTruthy: " + isTruthy("asd"))
    println("isTruthy: " + isTruthy(""))


  def isTruthy(a: Matchable) = a match
    case 0 | "" | false => false
    case _                => true

  /*
  More Match Expressions:
  # Case classes:
  case class Person(name: String)

  def speak(p: Person) = p match
    case Person(name) if name == "Fred" => println(s"$name says, Yubba dubba doo")
    case Person(name) if name == "Bam Bam" => println(s"$name says, Bam bam!")
    case _ => println("Watch the Flintstones!")

  speak(Person("Fred"))      // "Fred says, Yubba dubba doo"
  speak(Person("Bam Bam"))   // "Bam Bam says, Bam bam!"
  */

  /*
  // match expressions with different types
  def pattern(x: Matchable): String = x match

    // constant patterns
    case 0 => "zero"
    case true => "true"
    case "hello" => "you said 'hello'"
    case Nil => "an empty List"

    // sequence patterns
    case List(0, _, _) => "a 3-element list with 0 as the first element"
    case List(1, _*) => "list, starts with 1, has any number of elements"
    case Vector(1, _*) => "vector, starts w/ 1, has any number of elements"

    // tuple patterns
    case (a, b) => s"got $a and $b"
    case (a, b, c) => s"got $a, $b, and $c"

    // constructor patterns
    case Person(first, "Alexander") => s"Alexander, first name = $first"
    case Dog("Zeus") => "found a dog named Zeus"

    // type test patterns
    case s: String => s"got a string: $s"
    case i: Int => s"got an int: $i"
    case f: Float => s"got a float: $f"
    case a: Array[Int] => s"array of int: ${a.mkString(",")}"
    case as: Array[String] => s"string array: ${as.mkString(",")}"
    case d: Dog => s"dog: ${d.name}"
    case list: List[?] => s"got a List: $list"
    case m: Map[?, ?] => m.toString

    // the default wildcard pattern
    case _ => "Unknown"
  */


  def addNum(a:Int, b:Int):Int=
    a + b

  def addNum2(a:Int, b:Int) =
    a + b   // Last statement is always return statement

  // Taking default values
  def prodNum(a: Int, b: Int = 24) =
    a * b

  def prodNum2(a: Int = 24, b: Int = 23) =
    a * b


  // Higher order functions
  def higherOrderFunctions =
    sayHello(printHello)
    sayHello(printBonjour)
    println("Higher Order | executeAndPrint Add : ")
    executeAndPrint(add, 3, 4)
    println("Higher Order | executeAndPrint Subtract : ")
    executeAndPrint(subtract, 7, 9)
    executeNTimes(helloNTimes, 3)


  // passing functions as parameters
  def sayHello(f: () => Unit): Unit = f()
  def printHello() = println("Higher order Hello")
  def printBonjour() = println("Higher order Bonjour")
  /*
  - `f` is the name of the function input parameter.
    It’s just like naming a String parameter `s` or an Int parameter `i`.
  - The type signature of f specifies the type of the functions this method will accept.
  - The () portion of f’s signature (on the left side of the => symbol) states that f takes no input parameters.
  - The Unit portion of the signature (on the right side of the => symbol) indicates that f should not return a meaningful result.
  - Looking back at the body of the sayHello method (on the right side of the = symbol), the f() statement there invokes the function that’s passed in.
  */

  def add(a: Int, b: Int): Int = a + b
  def subtract(a: Int, b: Int) = a - b
  def multiply(a: Int, b: Int) = a * b
  def executeAndPrint(f:(Int, Int) => Int, i: Int, j: Int): Unit =
    println(s"($i, $j) : " + f(i, j))

  def executeNTimes(f: () => Unit, n: Int) =
    for i <- 1 to n do f()

  def helloNTimes() = println("Hello N Times")

  // Methods vs Functions
  /*
  Historically, methods have been a part of the definition of a class,
    although in Scala 3 you can now have methods outside of classes, such as Toplevel definitions and extension methods.

  Unlike methods, functions are complete objects themselves, making them first-class entities.
  Their syntax is also different.
  This example shows how to define a method and a function that perform the same task,
    determining if the given integer is even:
  */
  def isEvenMethod(i: Int) = i % 2 == 0       // a method
  val isEvenFunction = (i: Int) => i % 2 == 0 // a function


}
