/*
Chapter 4:
    includes:
    - Data structures
*/

package main

import (
    "fmt"
    "slices"
)

func chapter4() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 4 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    evalArrays()
    evalSlices()
}

func evalArrays() {
    fmt.Println("----- Arrays Explanation")
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

func evalSlices() {
    fmt.Println("----- Slices Explanation")
    // slices are typed only by the elements they contain (not the number of elements).

    // An uninitialized slice equals to nil and has length 0
    var s_s []string
    fmt.Println("Slices Initialized: ", s_s == nil, len(s_s) == 0, len(s_s))

    // Creating slice of non-zero length
    s_s = make([]string, 4)         // should already be initialized
                                    // else use :=
    fmt.Println("Slices non-zero: ", s_s, ", Length: ", len(s_s), ", Capacity: ", cap(s_s))

    s_s[0] = "a"
    s_s[3] = "c"
    fmt.Println("Set: ", s_s, ", Get: ", s_s[3], ", Capacity: ", cap(s_s))

    s_s = append(s_s, "d")
    s_s = append(s_s, "e")
    fmt.Println("Slice Append:", s_s, ", Length: ", len(s_s))


    // Copy slice:
    s_c := make([]string, len(s_s))
    copy(s_c, s_s)
    fmt.Println("Slice Copy 1: ", s_c, s_s)
    s_c[0] = "x"
    s_c[1] = "y"
    s_c[2] = "z"
    fmt.Println("Slice Copy 2: ", s_c, s_s) // You will see that the slices are different


    // "Slicing" or selecting subset
    s_part := s_c[2:5]                      // bounds should not exceed length limits else error
    fmt.Println("Slice: Part: ", s_part)
    s_part2 := s_c[:4]
    s_part3 := s_c[3:]
    fmt.Println("Slice: Part2: ", s_part2, s_part3)


    // Initialing slice at declaration:
    i_1 := []int{1, 2, 3, 4}
    fmt.Println("Slice: Init2: ", i_1)


    // Slice packages:
    i_2 := []int{1, 2, 3, 4}
    if slices.Equal(i_1, i_2) {
        fmt.Println("Slices are equal")
    }

    // Length vs Capacity
    s_i := make([]int, 3, 6)        // Initialize with length and capacity
    fmt.Println("Slice2: ", s_i, ", Length: ", len(s_i), ", Capacity: ", cap(s_i))

    fmt.Println()
}

func slicesDetailed() {
    fmt.Println("----- Slices Explanation: Length vs Capacity")

    s := make([]int, 3, 6)
    fmt.Println("Slice2: ", s, ", Length: ", len(s), ", Capacity: ", cap(s))

    fmt.Println()

}