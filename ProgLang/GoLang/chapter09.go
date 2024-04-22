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

    v1, e1:= errorsExplanation1(12)
    v2, e2:= errorsExplanation1(15)
    fmt.Println("Errors1: Val: ", v1, v2)  //Errors1: Val:  17 -1
    fmt.Println("Errors1: Err: ", e1, e2)  //Errors1: Err:  <nil> Can't work with 15

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
