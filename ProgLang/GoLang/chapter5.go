/*
Chapter 5:
    includes:
    - Functions
*/

package main

import (
    "fmt"
)

func chapter5() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 5 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    f1 := plus(2, 3)
    fmt.Println("Function: F1", f1)

    f2 := plus2(3, 4, 5)
    fmt.Println("Function: F2", f2)
}

func plus(a int, b int) int {
    return a + b
}

func plus2(a, b, c int) int {
    return a + b + c
}
