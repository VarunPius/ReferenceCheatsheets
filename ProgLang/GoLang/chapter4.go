/*
Chapter 4:
    includes:
    - Data structures
*/

package main

import (
    "fmt"
    //"slices"
    "maps"
)

func chapter4() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 4 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    evalArrays()
    evalSlices()
    evalMaps()
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


    // Multidimensional slices:
    twoD := make([][]int, 3)
    for i := 0; i < 3; i++ {
        inner := i + 1
        twoD[i] = make([]int, inner)
        for j := 0; j < inner; j++ {
            twoD[i][j] = i + j
        }
    }
    fmt.Println("2D Slice:", twoD)

    /*
    // Issue in importing in standard library
    // Slice packages:
    i_2 := []int{1, 2, 3, 4}
    if slices.Equal(i_1, i_2) {
        fmt.Println("Slices are equal")
    }
    */

    fmt.Println()
}

func slicesDetailed() {
    fmt.Println("----- Slices Explanation: Length vs Capacity")

    s := make([]int, 3, 6)
    fmt.Println("Slice2: ", s, ", Length: ", len(s), ", Capacity: ", cap(s))

    fmt.Println()
}

func evalMaps() {
    fmt.Println("----- Maps Explanation:")
    // To create an empty map, use the builtin make: make(map[key-type]val-type)

    mp1 := make(map[string]int)
    mp1["k1"] = 1
    mp1["k2"] = 2
    fmt.Println("Map:", mp1)

    v1 := mp1["k1"]
    v3 := mp1["k3"]     // zero value if key not exists
    fmt.Println("Map Value:", v1, v3)
    fmt.Println("Map Get Length:", len(mp1))

    delete(mp1, "k2")
    fmt.Println("Map delete key:", mp1)
    clear(mp1)      // GoLang 1.21+; before that iterate inside loop and delete
    fmt.Println("Map clear key:", mp1)


    // optional second return value when getting a value from a map to indicate if the key is present in the map
    // This can be used to disambiguate between missing keys and keys with zero values like 0 or ""
    // Here we didn’t need the value itself, so we ignored it with the blank identifier _.
    _, mp_flag := mp1["k3"]
    fmt.Println("Map present:", mp_flag)

    // Initialize non-empty map
    mp2 := map[string]int{"foo": 1, "bar": 2}
    mp3 := map[string]int{"foo": 1, "bar": 2}
    if maps.Equal(mp2, mp3) {
        fmt.Println("Maps are EQUAL")
    } else {
        fmt.Println("Maps NOT EQUAL")
    }

    fmt.Println()
}