/*
Chapter 11:
    includes:
    - Channels
*/

package main


import (
    "fmt"
    //"time"
)

func chapter11() {
    fmt.Println("------------------------------------------------")
    fmt.Println("------------------ Chapter 11 -------------------")
    fmt.Println("------------------------------------------------")
    fmt.Println()

    channelsExplanation()
    fmt.Println()

    channelsExplanation2()
    fmt.Println()

    channelsExplanation3()
    fmt.Println()

    channelBufferedExplanation()
    fmt.Println()
}

func channelsExplanation() {
    // Channels are the pipes that connect concurrent goroutines.
    // You can send values into channels from one goroutine and receive those values into another goroutine.

    fmt.Println("Channel Explanation 1")
    messages := make(chan string)    // Create a new channel with make(chan val-type). Channels are typed by the values they convey.
    go func() { messages <- "Ping"}()    // Send a value into a channel using the channel <- syntax
    go func() { messages <- "Ping2"}()   // We send values from a new goroutine.
    go func() { messages <- "Ping3"}()    

    msg := <- messages      // <-channel syntax receives a value from the channel
    msg2 := <- messages
    msg3 := <- messages
    fmt.Println("Message from channel: ", msg, msg2, msg3)    //Message from channel:  Ping3 Ping Ping2
    
    // By default sends and receives block until both the sender and receiver are ready.
    // This property allowed us to wait at the end of our program for the "ping" message without having to use any other synchronization.
    // In the above example, every ping we sent we were able to send only because corresponding receive was available
    // ? Though i was able to send even if corresponding receive was not available. Maybe they changed in newer version
}

func channelsExplanation2() {
    fmt.Println("Channel Explanation 2")

    messages := make(chan string)
    go func() { messages <- "Ping"}()    

    msg := <- messages
    //msg2 := <- messages       // We can't do this if only one message is sent in channel

    fmt.Println("Message from channel: ", msg)  // Message from channel:  Ping
    //fmt.Println("Message from channel: ", msg2)

    /*
    Also, sending messages are done within goroutine
    If you don't send withing goroutine, you will get the following error: fatal error: all goroutines are asleep - deadlock!
    Only way to not do within goroutine is via buffered channel.
    If the sending code is in the main function, and there's no other goroutine to receive the message, the program deadlocks.
    Goroutines provide a separate execution context, preventing the main program from being blocked while waiting for a channel operation.

    Here's a sample program that FAILS:
    messages := make(chan string)
    messages <- "Ping"
    msg := <- messages
    fmt.Println("Message from channel: ", msg)

    Even if the receiver is in the very next line, it fails
    */
}


func channelsExplanation3() {
    fmt.Println("Channel Explanation 3")

    messages := make(chan string)
    go func() { messages <- "Ping"}()    
    go func() { messages <- "Ping2"}()    

    msg := <- messages
    fmt.Println("Message from channel: ", msg)  // Message from channel:  Ping2
    
    msg = <- messages
    fmt.Println("Message from channel: ", msg)  // Message from channel:  Ping

    /*
    Sometimes, the order is inverted:
    Channel Explanation 3
    Message from channel:  Ping
    Message from channel:  Ping2
    */
}


func channelBufferedExplanation(){
    fmt.Println("Bufffered Explanation")
    // By default channels are unbuffered,
    // meaning that they will only accept sends (chan <-)
    // if there is a corresponding receive (<- chan) ready to receive the sent value.
    // Buffered channels accept a limited number of values without a corresponding receiver for those values.
    messages := make(chan string, 3)
    messages <- "Buffered"
    messages <- "Channel"
    messages <- "Example"
    fmt.Println(<- messages)
    messages <- "Example2"

    fmt.Println(<- messages)
    fmt.Println(<- messages)
    fmt.Println(<- messages)
}