package main

import "fmt"

func chapter2() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 2 -------------------")
    fmt.Println("------------------------------------------------")

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
}

func variablesExplanation() {
    fmt.Println("----- Variable Explanation")
    var a string = "Delta"  // Default basic implementation
    var b, c int = 3, 4     // multiple initialization

    var f = false           // type inference
    var d, e = "Test", 5    // multiple type inference

    fmt.Println(a, b, c, d, e, f)

    var g bool              // Non-initialized variable
    fmt.Println(g)

}
