/*
Chapter 4:
    includes:
    - Data structures
*/

package main

import (
    "fmt"
)

func chapter4() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 4 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    evalArrays()
}

func evalArrays() {
    var a_i [5]int
    fmt.Println("Array 1:", a_i)

    a_i[3] = 2
    fmt.Println("Array SET:", a_i)
    fmt.Println("Array GET:", a_i[3])
    fmt.Println("Length of Array: ", len(a_i))

    b_i := [4]int{1, 2, 5, 6}
    fmt.Println("Array declare:", b_i)

    var twoD [2][3]int
    for i := 0; i < len(twoD); i++ {
        for j := 0; j < len(twoD[0]); j++ {
            twoD[i][j] = i + j
        }
    }
    fmt.Println("2-D array:", twoD)

    fmt.Println()
}

