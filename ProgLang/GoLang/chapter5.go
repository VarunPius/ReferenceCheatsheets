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

    first_name, last_name := multi_return("Varun", "Pius", "Rodrigues")
    fmt.Println("Function: Multiple return:", first_name, last_name)
    fmt.Println()

    sum := variadic_sum(1,2,4,5,6)
    fmt.Println("Sum:", sum)

}

func plus(a int, b int) int {
    return a + b
}

func plus2(a, b, c int) int {
    return a + b + c
}

// Multiple return values
func multi_return(a, b, c string) (string, string) {
    return a, c
}

// Variadic functions can be called with variable number of trailing arguments. eg: fmt.Println
func variadic_sum(nums ...int)int {
    fmt.Println("NUmbers:", nums)
    sum := 0

    for _, num := range nums {
        sum += num
    }


    return sum

}