/***************************************************************************************************
Chapter 2: Data and control Structures

This chapter includes:
  - Data structures
  - Control structures
***************************************************************************************************/


object Chapter2
  @main def mainMethodCaller =
    variables                           // Can't add() as the original method def doesn't have ();
                                        // () will result in error
    numericOperations()
    stringOperations
    controlStructures


  private def variables =      // () not necessary if no input parameters
    println("__________________________________________________")
    println("++++++++++ Basic variables here ++++++++++++++++++")
    println("__________________________________________________")
    // Val is used to initialize constant value
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
      anExpression2 + 5   // Last statement is the returned value
    
    println("CodeBlock: " + codeBlock.toString)


  def numericOperations() =
    println("__________________________________________________")
    println("++++++++++ Numeric operations start here +++++++++")
    println("__________________________________________________")
    // Primitive datatypes:
    val b: Byte = 1
    val i: Int = 1
    val l: Long = 1
    val s: Short = 1
    val d: Double = 2.0
    val f: Float = 3.0

    // Because Int and Double are the default numeric types,
    // you typically create them without explicitly declaring the data type:
    val i2 = 123  // defaults to Int
    val j = 1.0   // defaults to Double

    // You can also append the L, D, and F (and their lowercase equivalents) to numbers
    // to specify that they are Long, Double, or Float values:
    val x = 1_000L  // val x: Long = 1000
    val y = 2.2D    // val y: Double = 2.2
    val z = 3.3F    // val z: Float = 3.3

    // When you need really large numbers, use the BigInt and BigDecimal types :
    var aBI = BigInt(1_234_567_890_987_654_321L)
    var aBD = BigDecimal(123_456.789)
    // Where Double and Float are approximate decimal numbers, BigDecimal is used
    // for precise arithmetic

    // Integers calculations while initialization
    val anExpression1 = 2 + 3
    println("anExpression1: " + anExpression1.toString)
    println("anExpression1 2: " + anExpression1)    // This works too


  def stringOperations =
    println("__________________________________________________")
    println("++++++++++ String operations start here ++++++++++")
    println("__________________________________________________")
    // String and Char data types:
    val name = "Bill"   // String
    val c = 'a'         // Char

    // String interpolation
    val firstName = "Varun"
    val middleInitial = "P"
    val lastName = "Rodrigues"

    println(s"Name is ${firstName} ${middleInitial} ${lastName}")
    println(s"2 + 2 = ${2 + 2}")          // prints "2 + 2 = 4"

    // Multiline strings
    val essay =
      """Scala is amazing language
        |test
        |""".stripMargin

    val essay2 =
      """Scala is functional language
        |test2
        .."""

    println("Essay: " + essay)
    println("Essay2: " + essay2)

    // Boolean
    val aBoolean1 = false
    val aBoolean2 = true
    println("Boolean values: " + aBoolean1 + " " + aBoolean2)


  def controlStructures =
    println("__________________________________________________")
    println("++++++++++ Control structures start here +++++++++")
    println("__________________________________________________")
