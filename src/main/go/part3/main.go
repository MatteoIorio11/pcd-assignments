package main

import (
	"fmt"
)

type Hint int

const (
	GREATER Hint = iota
	SMALLER Hint = iota
	SAME    Hint = iota
)

type Message struct {
	guessedNumber int // message sent by the player
	playerID      int
}

// Message sent from the Oracle to the player
type Response struct {
	status   bool // true won, false lost
	hintV    Hint
	playerID int
}

// The oracle has X channels, one for each player. The playerID is the index for the channel
// The player has only one channel

func main() {
	fmt.Println("Hello World")
}
