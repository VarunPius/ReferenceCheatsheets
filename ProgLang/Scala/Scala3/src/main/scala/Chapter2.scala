/***************************************************************************************************
Chapter 2: Data and control Structures

This chapter includes:
  - Data structures
  - Control structures
***************************************************************************************************/


object Chapter2
  @main def mainMethodCaller() =
    variableOperations                  // Can't add() as the original method def doesn't have ();
    println()                           // () will result in error

    numericOperations()
    println

    stringOperations
    println

    advancedDatatypes
    println()

    controlStructures
    println


  private def variableOperations =      // () not necessary if no input parameters
    println("--------------------------------------------------")
    println("++++++++++ Basic variables here ++++++++++++++++++")
    println("--------------------------------------------------")
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


  def numericOperations(): Unit =               // Alternate approach for empty returns; above we did without Unit
    println("--------------------------------------------------")
    println("++++++++++ Numeric operations start here +++++++++")
    println("--------------------------------------------------")
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
    println("--------------------------------------------------")
    println("++++++++++ String operations start here ++++++++++")
    println("--------------------------------------------------")
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
        | Multiline suggestion
        | this one's without margin"""

    println("Essay: " + essay)
    println("Essay2: " + essay2)
    println()

    // Boolean
    val aBoolean1 = false
    val aBoolean2 = true
    println("Boolean values: " + aBoolean1 + " " + aBoolean2)


  def advancedDatatypes =
    println("--------------------------------------------------")
    println("++++++++++ Advanced datatypes start here +++++++++")
    println("--------------------------------------------------")
    // Arrays, Map, Set, Stack, Queue

    //Seq and List
    // In Java terms, Scala's Seq would be Java's List,
    // and Scala 's List would be Java's LinkedList
    val l = List(1, 2, 3)
    println("List elements: " + l)

    // Map



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
