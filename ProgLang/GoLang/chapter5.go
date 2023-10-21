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
    fmt.Println("------------------ Chapter 4 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    plus(2, 3)
}

func plus(a int, b int) int {
    return a + b
}


