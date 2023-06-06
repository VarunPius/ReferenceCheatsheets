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

    //controlStructures
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

    println("-- Sequence --------------------------------------")
    sequenceExplanation
    println("-- List ------------------------------------------")
    listExplanation
    println("-- Range ------------------------------------------")
    rangeExplanation
    println("-- Array ------------------------------------------")
    arrayExplanation
    println("-- Vector -----------------------------------------")
    vectorExplanation

    // Arrays, Map, Set, Stack, Queue
    // Seq and List
    // Map
    // In Java terms, Scala's Seq would be Java's List,
    // and Scala's List would be Java's LinkedList


  // Seq = well-defined ordering and sequence
  def sequenceExplanation =
    val aSequence = Seq(1, 2, 3, 4, 5)
    // val aSequence: Seq[Int] = Seq(1, 2, 3, 4, 5)   // alternative approach

    val secondElement = aSequence(2)
    val thirdElement = aSequence.apply(3)   // Diff between apply and not using apply is specified in Chp 5
    println("Accessing elements: " + secondElement + " | " + thirdElement)

    // Other methods:
    val reversed = aSequence.reverse
    println("Reversed: " + reversed)
    val concatSeq = aSequence ++ Seq(6, 7)
    println("Concatenated: " + concatSeq)
    val sortedSeq = aSequence.sorted
    println("Sorted: " + sortedSeq)

    // Map/flatmap, filter, for comprehensions
    val incrementSeq = aSequence.map(_ + 1)
    println("Increment Seq: " + incrementSeq)
    val flatmapSeq = aSequence.flatMap(x => Seq(x, x + 2))
    println("Flatmap Seq: " + flatmapSeq)
    val filteredSeq = aSequence.filter(_ % 2 == 0)
    println("Filtered Seq: " + filteredSeq)
    val sum = aSequence.foldLeft(0)(_ + _)    // 0 here is initial default value; for double you would use 0.0
                                              // for string, you would use "
    println("Sequence Sum: " + sum)

    val donutSeq = Seq("Plain", "Glazed", "Chocolate")
    val donuts = donutSeq.foldLeft("Tasty ")((a, b) => a + b + " donut ")
    println("Donuts: " + donuts)    // Donuts: Tasty Plain donut Glazed donut Chocolate donut
    // println(s"All donuts = ${donutSeq.foldLeft("")((a, b) => a + b + " Donut ")}")   // alternative
    val strSeq1 = aSequence.mkString
    val strSeq2 = aSequence.mkString(":")
    val strSeq3 = aSequence.mkString("[", ", ", "]")     // start, sep, end
    println("Mkstring: " + strSeq1 + " || " + strSeq2 + " || " + strSeq3)
    println()


  def listExplanation =
    val aList = List(1, 2, 3, 4, 5, 6)
    // Special implementation of Seq
    // Same methods as Seq

    // Extra methods:
    // head and tail
    val firstElement = aList.head
    val rest = aList.tail
    println("First element: " + firstElement)
    println("Remaining element: " + rest)

    // appending and prepending
    val aBiggerList = 0 +: aList :+ 7
    val prependList = 8 :: aList
    println("Appending: " + aBiggerList)
    println("Prepending: " + prependList)

    // fill
    val scala5x = List.fill(5)("Scala")
    val scala5x2 = List.fill(2, 3)("JVM")   // similarly can create higher dimension matrix filled with values too
                                            // such fill(n1, n2, n3 ... nn)
    println("List Fill: "+ scala5x + " || " + scala5x2)
    println()


  def rangeExplanation =
    //yet another implementation of Seq
    val aRangeAlt: Seq[Int] = 1 to 20   // alternative way
    val aRange = 1 to 10    // Won't hold all values; more like lazy evaluation
    // will allow all Seq functions, but won't hold all values in memory
    (1 to 10).foreach(_ => println("Scala"))
    (1 to 10).foreach(x => println("Scala" + x))

    val aNonInclusiveRange = 1 until 10   // in `to` 10 is included; here it's only until 9
    println("Ranges: " + aRange + " | " + aRangeAlt + " | " + aNonInclusiveRange)
    println()


  // Similar to int[] in JVM
  def arrayExplanation =
    val anArray = Array(1, 2, 3, 4, 5, 6, 7)
    println("Array: " + anArray)
    // Arrays have access to most Seq APIs
    // But arrays are NOT Sequences
    val aSequence = anArray.toIndexedSeq
    println("Array to Seq: " + aSequence)
    // Main capability of arrays is it's mutable; Seq, List and ranges we discussed so far are immutable
    anArray.update(2, 45)   // (idx, value); operation is inplace, no new array is created
    println("Modified Array: " + anArray)
    println()


  // Linear collections; fast sequence implementation;
  // share a lot of Seq APIs but useful when dealing with huge dataset
  def vectorExplanation =
    val aVector1: Vector[Int] = Vector(1, 2, 3, 4, 5)
    val aVector = Vector(1, 2, 3, 4, 5)
    println("Vector: " + aVector)
    // Refer benchmark method
    println()


  def vectorVsSeqBenchmark =
    // def getWriteTime()

    val maxIter = 1000
    val maxCapacity = 1_000_000

    println()


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
