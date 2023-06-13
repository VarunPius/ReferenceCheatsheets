/***************************************************************************************************
Chapter 3: Functions and Methods

This chapter includes:
  - Methods
  - currying
 ***************************************************************************************************/


object Chapter3 {
  @main def mainChp3 =
    println("Chapter 3: Methods")
    val sum1 = addNum(2,3)
    val sum2 = addNum2(4,5)
    println("Sum: " + sum1 + " | " + sum2)
    val prod1 = prodNum(12, 13)
    val prod2 = prodNum(15)   // if value of a was set, this wouldn't have worked.
                              // So all default values of partial num of parameters are set at end
                              // here, 15 is set for a, while b takes default value
    val prod3 = prodNum2()
    println("Product: " + prod1 + " | " + prod2 + " | " + prod3)



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
