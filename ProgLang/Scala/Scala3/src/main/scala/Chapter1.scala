// We don't necessarily need to create an object
// Here in this chapter, we will take user input

import scala.io.StdIn.readLine

@main def Hello() = {
  HelloWorld()
  HelloParameterized("Varun")
  HelloInteractive()
}

def HelloWorld() = {
  println("Hello World")
}

def HelloParameterized(name: String) = {
  println("Hello Parameterized " + name)
}


def HelloInteractive() = {
  print("Enter name: ")
  val name = readLine()

  println("Hello " + name + "!")
}

/*
Following implementation is nice to look at:
Firstly, we created another main method.
When we compile this, it will create 2 different .class files corresponding to the 2 different main methods.
Then when we run, we run the method you want to run.
Step:
  - For terminal:
    - scalac Chapter1.scala
    - scala Hello
      or
    - scala happyBirthday

  - For IntelliJ
    - Just press the `Run` option besides the respective method
    To configure the arguments,
        go to `Run | Edit configurations...` & set the arguments under `Program arguments`
    Alternatively, in the Run besides the program name, choose Modify Configurations

Secondly, we create this method to include arguments (variable number of arguments at that)
  Here, `others` indicates multiple arguments

Thirdly, we don't use braces but rather spacing/tabbing to indicate blocks.
This is new in Scala 3. This is to move more inline to Python-esque syntax
*/

@main def happyBirthday(age: Int, name: String, others: String*) =
  val suffix = (age % 100) match
    case 11 | 12 | 13 => "th"
    case _ => (age % 10) match
      case 1 => "st"
      case 2 => "nd"
      case 3 => "rd"
      case _ => "th"

  val sb = StringBuilder(s"Happy $age$suffix birthday, $name")
  for other <- others do sb.append(" and ").append(other)
  println(sb.toString)