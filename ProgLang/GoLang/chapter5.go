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
    nums_eg := []int{1, 2, 3, 4}
    sum2 := variadic_sum(nums_eg...)    // slice applied to Variadic func 
    fmt.Println("Sum:", sum, sum2)

    nextInt := intSeq()
    fmt.Println("Closure1: ", nextInt())
    fmt.Println("Closure2: ", nextInt())
    fmt.Println("Closure3: ", nextInt())
    nextInt2 := intSeq()
    fmt.Println("Closure4: ", nextInt2())
    fmt.Println("Closure5: ", nextInt2())

    fct := 7
    fmt.Println("Factorial: ", fact(fct))

    // Recursive using closures
    // requires the closure to be declared with a typed var explicitly before it’s defined.
    var fib func(n int) int

    // Since fib was previously declared in main, Go knows which function to call with fib here.
    fib =  func()
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

// Closures: anonymous functions
func intSeq() func() int {
    i := 0
    return func() int {
        i++
        return i
    }
}

// Recursion/Recursive Functions
func fact(n int) int {
    if n == 0 {
        return 1
    }
    return n * fact(n - 1)
}