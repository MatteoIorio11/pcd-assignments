package main

import (
	"math/rand/v2"
)

type Oracle struct {
	value         int
	inputChannel  []chan Message
	outputChannel []chan Response
}

func (oracle Oracle) getInputChannels() []chan Message {
	return oracle.inputChannel
}

func (oracle Oracle) getMessageFromPlayer(playerID int) {
	if playerID > 0 && playerID < len(oracle.inputChannel) {
		select {
		case message, ok := <-oracle.getInputChannels()[playerID]:
			if ok {

			}
		}
	}
}

func generateRandom(maxValue int) int {
	return rand.IntN(maxValue)
}

func createInputChannels(size int) []chan Message {
	channels := make([]chan Message, size)
	for i := 0; i < size; i++ {
		channels[i] = make(chan Message)
	}
	return channels
}

func createOutputChannels(size int) []chan Response {
	channels := make([]chan Response, size)
	for i := 0; i < size; i++ {
		channels[i] = make(chan Response)
	}
	return channels
}

func spawnOracle(maxValue int, players int) Oracle {
	inputC := createInputChannels(players)
	outputC := createOutputChannels(players)
	oracle := Oracle{value: generateRandom(maxValue), inputChannel: inputC, outputChannel: outputC}
	return oracle
}
