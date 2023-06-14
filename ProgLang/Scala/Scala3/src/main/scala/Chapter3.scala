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

    // For Loops


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
