/*
Chapter 2:
    includes:
    - datatypes
    - variables
    - constants
*/

package main

import (
    "fmt"
    "math"
)

func chapter2() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 2 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    datatypesExplanation()
    variablesExplanation()
}

func datatypesExplanation() {
    fmt.Println("----- Datatype Explanation")
    fmt.Println("GoLang " + "Variables")
    fmt.Println("10 + 12:", 10 + 12)
    fmt.Println("Division:")
    fmt.Println("18/8: ", 18/8)
    fmt.Println("18.0/8: ", 18.0/8)
    fmt.Println("18/8.0: ", 18/8.0)
    fmt.Println("18.0/8.0: ", 18.0/8.0)

    fmt.Println("Boolean:")
    fmt.Println("AND: ", true && false)
    fmt.Println("OR: ", true || false)
    fmt.Println("NOT: ", !true)
    fmt.Println()
}

func variablesExplanation() {
    fmt.Println("----- Variable Explanation")
    var a string = "Delta"  // Default basic implementation
    var b, c int = 3, 4     // multiple initialization

    var f = false           // type inference
    var d, e = "Test", 5    // multiple type inference

    fmt.Println("a-f:", a, b, c, d, e, f)

    var g bool              // Non-initialized variable
    fmt.Println("g:", g)    // Variables declared without a corresponding initialization are zero-valued.
                            // For example, the zero value for an int is 0.

    h := "Home"             // := syntax is shorthand for declaring and initializing a variable,
    i := 9                  // e.g. for var f string = "apple"  => f := "apple"
    fmt.Println("h-i", h, i)
    fmt.Println()

    fmt.Println("----- Constant Explanation")
    const c_a = "constant"
    const c_b = 500000
    const c_c = 6e15/c_b
    fmt.Println("Printing constant:", c_a, c_b, c_c)
        // Constant expressions perform arithmetics with arbitrary precision.
        // A numeric constant has no type until it’s given one, such as by an explicit conversion.
    fmt.Println("Constant after conversion:", int64(c_c))

    // A number can be given a type by using it in a context that requires one,
    // such as a variable assignment or function call.
    // For example, here math.Sin expects a float64.
    fmt.Println("Sine:", math.Sin(c_b))
    fmt.Println()
}
