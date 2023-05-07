/***************************************************************************************************
Chapter 0:  Getting Started

This chapter includes:
  - various ways to start a simple "Hello World"
  - writing main methods
***************************************************************************************************/

// Scala 3 implementation: we initializes main with the use of @main annotation
object Chapter0 {
  @main def HelloWorld() = 
    println("Hello World!")
  
  println("If mentioned outside main, gets executed first")
  /*
  you can create methods and objects at same level as @main and during compilation all these objects get created before the main gets executed
  This is why, here you see preceding print statement before main's print
  */
}


/***************************************************************************************************
 *  Scala 2 implementations
*************************************************************************************************/

/*
// Scala 2 implementation with main method
object Chapter0{
  def main(args: Array[String]) = {
    println("Hello World!")
  }
}
*/

/*
// Scala 2 implementation with App: Main method is not present as the trait App will include it
object Chapter0 extends App {
  println("Hello World!")
}
*/

/*
object HelloYou extends App {
  if (args.size == 0)
    println("Hello, you")
  else
    println("Hello, " + args(0))
}
*/