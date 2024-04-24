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

	channelBufferedExplanation()
	fmt.Println()

}

func channelsExplanation() {
    // Channels are the pipes that connect concurrent goroutines.
	// You can send values into channels from one goroutine and receive those values into another goroutine.

	messages := make(chan string)	// Create a new channel with make(chan val-type). Channels are typed by the values they convey.
	go func() { messages <- "Ping"}()	
	go func() { messages <- "Ping2"}()	
	go func() { messages <- "Ping3"}()	

	msg := <- messages
	msg2 := <- messages
	msg3 := <- messages
	fmt.Println("Message from channel: ", msg, msg2, msg3)	//Message from channel:  Ping3 Ping Ping2
	// By default sends and receives block until both the sender and receiver are ready.
	// This property allowed us to wait at the end of our program for the "ping" message without having to use any other synchronization.
	// In the above example, every ping we sent we were able to send only because correesponding receive was available
	// ? Though i was able to send even if corresponding receive was not available. Maybe they changed in newer version
}

func channelBufferedExplanation(){
	// By default channels are unbuffered,
	// meaning that they will only accept sends (chan <-)
	// if there is a corresponding receive (<- chan) ready to receive the sent value.
	// Buffered channels accept a limited number of values without a corresponding receiver for those values.
	messages := make(chan string, 3)
	messages <- "Buffered"
	messages <- "Channel"
	messages <- "Example"

	fmt.Println(<- messages)
	fmt.Println(<- messages)
	fmt.Println(<- messages)
}