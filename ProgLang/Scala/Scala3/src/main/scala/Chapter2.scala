/***************************************************************************************************
Chapter 2: Data and control Structures

This chapter includes:
  - Data structures
  - Control structures
***************************************************************************************************/


object Chapter2 {
  @main def methodCaller =
    // Val is used to initialize constant value
    variables                  // can't add() as the original method def doesn't have () and this will result in error

  private def variables =      // () not necessary if no input parameters
    val num1 = 12
    println("Value Num1: " + num1)

    // We can use static type declaration too
    val num2: Int = 23
    println("Value Num2: " + num2)
    // But remember if we use val, we have to initialize value; we can't leave it empty
    // as val num2: Int; it expects a value

    // To declare a variable you may use var:
    var num3 = 12
    println("Variable Num3 pre: " + num3)
    num3 = 23
    println("Variable Num3 post: " + num3)
    // This results in an error: num3 = "test"
    // Because datatype can't be changed; only value can be

    // Code Block
    val codeBlock = 
      val anExpression2 = 4
      anExpression2 + 5
    
    println("CodeBlock: " + codeBlock.toString)

    // Integers
    val anExpression1 = 2 + 3
    println("anExpression1: " + anExpression1.toString)

    // Boolean
    val aBoolean1 = false
    val aBoolean2 = true
    println("Boolean values: " + aBoolean1 + " " + aBoolean2)


    


}
