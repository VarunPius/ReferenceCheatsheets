/*
Chapter 9:
    includes:
    - Error handling
*/

package main


import (
    "fmt"
    "errors"
    //"time"
)

func chapter09() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 9 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    // errorsExplanation1 demonstration
    for _, i := range []int{10, 12, 15} {
        if retval, err := errorsExplanation1(i); err != nil{
            fmt.Println("func1 failed", err)
        } else {
            fmt.Println("func1 worked", retval)
        }

    }

    fmt.Println()
    
    fmt.Println()

}

func errorsExplanation1(arg int) (int, error){
    // By convention, Errors are the last return values with return type error

    if arg == 15 {
        // errors.New constructs a basic error value with the given error message.
        return -1, errors.New("Can't work with 15")
    }

    return arg + 5, nil     // A nil value in the error position indicates that there was no error.
}
