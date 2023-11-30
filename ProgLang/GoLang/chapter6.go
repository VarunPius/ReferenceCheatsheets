/*
Chapter 6:
    includes:
    - Pointers
*/

package main

import (
	"fmt"
)


func chapter6() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 6 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    i := 1
    fmt.Println("Initial: ", i)

    zeroval(i)
    fmt.Println("Zero Value: ", i)

    zeroptr(&i)
    fmt.Println("Zero after pointer: ", i)

    fmt.Println("Pointer address:", &i)

	fmt.Println()

}

func zeroval(ival int) {
    ival = 0
}

func zeroptr(iptr *int) {
    *iptr = 0
}
