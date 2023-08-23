/*
Chapter 3:
    includes:
    - control structures
*/

package main

import (
    "fmt"
)

func chapter3() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 2 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    evalForLoop()

}

func evalForLoop() {
    fmt.Println("----- For Loop Explanation")
    i := 2
    for i < 5 {                        // The most basic type, with a single condition.
        fmt.Println("For1:", i)
        i = i + 1
    }

    for {                            // for without a condition will loop repeatedly
        fmt.Println("For2")          // until you break out of the loop or return from the enclosing function.
        break
    }

    for i := 1; i < 5; i++ {        // classic initial/condition/after for loop.
        fmt.Println("For3:", i)
    }

    for i = 1; i < 5; i++ {         // You can also continue to the next iteration of the loop.
        if i%2 == 0 {
            continue
        }

        fmt.Println("For4:", i)
    }
    fmt.Println()
    
}

func evalIfElseCondition() {
    fmt.Println("----- If/Else Explanation")
    
    
    fmt.Println()
}
