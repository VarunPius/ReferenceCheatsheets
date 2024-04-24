/*
Chapter 10:
    includes:
    - GoRoutines
*/

package main


import (
    "fmt"
    "time"
)

func chapter10() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 10 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    goroutinesExplanation()
    fmt.Println()
    
    fmt.Println()

}

func goroutinesExplanation() {
    // goroutine is a lightweight thread of execution

    printLoop("Sync")       // this prints synchronously and GoLang waits for it to execute completely

    go printLoop("aSync")   // This new goroutine will execute concurrently with the calling one.

    go func(msg string){    // can be used to create anonymous functions
        fmt.Println(msg)
    }("anon async")

    time.Sleep(time.Second)

    fmt.Println("done")


}

func printLoop(from string){
    for i:=0; i < 5; i++ {
        fmt.Println(from, ":", i)
    }
}

/*
Output:
Sync : 0
Sync : 1
Sync : 2
Sync : 3
Sync : 4
aSync : 0
aSync : 1
aSync : 2
anon async
aSync : 3
aSync : 4
done

Output 2:
Sync : 0
Sync : 1
Sync : 2
Sync : 3
Sync : 4
anon async
aSync : 0
aSync : 1
aSync : 2
aSync : 3
aSync : 4
done
*/