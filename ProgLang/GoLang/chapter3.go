/*
Chapter 3:
    includes:
    - control structures
*/

package main

import (
    "fmt"
    "time"
)

func chapter3() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 2 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    evalForLoop()
    evalIfElseCondition()
    evalSwitchCase()
    evalSwitchTime()

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
    n := 5

    if n < 5 {
        fmt.Println("If: number lass than 5")
    } else if (n >= 5) && (n < 10) {
        fmt.Println("Else If: Number greater than 5 and less than 10")
    } else {
        fmt.Println("Else: Number greater than 10")
    }
    
    fmt.Println()
}

func evalSwitchCase() {
    fmt.Println("----- Switch Case Explanation")

    i := 2
    switch i {
    case 1:
        fmt.Println("Switch1_1: ", i)
    case 2:
        fmt.Println("Switch1_2:", i)
    case 3:
        fmt.Println("Switch1_3:", i)
    }

    fmt.Println()
}

func evalSwitchTime() {
    fmt.Println("----- Switch Case Time Example")

    switch time.Now().Weekday() {
    case time.Saturday, time.Sunday :
        fmt.Println("Switch2_1: It's a Weekend")
    default:
        fmt.Println("Switch2_2: It's a weekday")
    }

    t := time.Now()
    switch  {
    case t.Hour() < 12 :
        fmt.Println("Switch3_1: It's morning")
    default:
        fmt.Println("Switch3_2: After Noon")
    }

    fmt.Println()
}