package main

import (
	"fmt"
	"math/rand"
)


type Player struct {
	playerId		 int
	sendingChannel   chan Message
	receivingChannel chan Response
	currentGuess     int
	currentHint      Hint
	currentMin		 int
	currentMax 		 int
}

func GuessNumber(upperBound int) int {
	return rand.Intn(upperBound)
}

func (p Player) SendGuess() {
 
	switch p.currentHint {
		case GREATER:
			if p.currentMin < p.currentGuess {
				p.currentMin = p.currentGuess
			}

		case SMALLER:
			if p.currentMax < p.currentGuess {
				p.currentMax = p.currentGuess
			}
	}
	p.currentGuess = rand.Intn(p.currentMax - p.currentMin) + p.currentMin
	p.sendingChannel <- Message { p.currentGuess, p.playerId }
}

func (p Player) ReceiveOutcome() {
	res := <- p.receivingChannel
	if res.status && res.playerID == p.playerId {
		fmt.Printf("[player-%d]: I won with %d!\n", p.playerId, p.currentGuess)
	} else if res.status {
		fmt.Printf("[player-%d]: GG grande player-%d ci hai preso!\n", p.playerId, res.playerID)
	} else {
		p.currentHint = res.hintV
	}
}

