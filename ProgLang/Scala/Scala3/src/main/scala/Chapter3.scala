/***************************************************************************************************
Chapter 3: Functions and Methods

This chapter includes:
  - Methods
  - currying
 ***************************************************************************************************/


object Chapter3 {
  @main def mainChp3 =
    println("Chapter 3: Methods & control structures")
    controlStructures

    val sum1 = addNum(2,3)
    val sum2 = addNum2(4,5)
    println("Sum: " + sum1 + " | " + sum2)
    val prod1 = prodNum(12, 13)
    val prod2 = prodNum(15)   // if value of a was set, this wouldn't have worked.
                              // So all default values of partial num of parameters are set at end
                              // here, 15 is set for a, while b takes default value
    val prod3 = prodNum2()
    println("Product: " + prod1 + " | " + prod2 + " | " + prod3)


  def controlStructures =
    println("--------------------------------------------------")
    println("++++++++++ Control structures start here +++++++++")
    println("--------------------------------------------------")

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


  def addNum(a:Int, b:Int):Int=
    a + b

  def addNum2(a:Int, b:Int) =
    a + b   // Last statement is always return statement

  // Taking default values
  def prodNum(a: Int, b: Int = 24) =
    a * b

  def prodNum2(a: Int = 24, b: Int = 23) =
    a * b

}
