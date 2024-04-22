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

    for i := range 5 {               // updated from range [5]int{} since go 1.22
        fmt.Println("Loop2: ", i)
        if err := makeTeaErrorImpl(i); err != nil {
            if errors.Is(err, ErrOutOfMilk){
                fmt.Println("Ran out of Milk")
            } else if errors.Is(err, ErrPower){
                fmt.Println("It's dark")
            } else {
                fmt.Printf("Unknown error: %s \n", err)
            }
            continue
        }
        fmt.Println("Tea ready")
    }
    /*
    Loop2:  0
    Tea ready
    Loop2:  1
    Tea ready
    Loop2:  2
    Ran out of Milk
    Loop2:  3
    Tea ready
    Loop2:  4
    It's dark
    */
    
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


// A sentinel error is a predeclared variable that is used to signify a specific error condition.
var ErrOutOfMilk = fmt.Errorf("No more milk!")
var ErrPower = fmt.Errorf("Insufficent power")

func makeTeaErrorImpl(kitchen int) error {
    if kitchen == 2 {
        return ErrOutOfMilk
    } else if kitchen == 4 {
        return fmt.Errorf("Making Tea: %w", ErrPower) 
    }
    return nil
}