/***************************************************************************************************
Chapter 2: Data and control Structures

This chapter includes:
  - Data structures
  - Control structures
***************************************************************************************************/


object Chapter2 {
  @main def dataStructures() =
    // Val is used to initialize constant value
    val num1 = 12
    println("Value Num1: " + num1)

    // We can use static type declaration too
    val num2: Int = 23
    println("Value Num2: " + num2)

    //But remember if we use val, we have to initialize value; we can't leave it empty
    // as val num2: Int; it expects a value

    // To declare a variable you may use var:
    var num3 = 12
    println("Variable Num3 pre: " + num3)
    num3 = 23
    println("Variable Num3 post: " + num3)

    //This results in an error: num3 = "test"
    // Because datatype can't be changed; only value can be




}
