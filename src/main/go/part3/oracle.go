package main

type Oracle struct {
	value         int
	inputChannel  []chan Message
	outputChannel []chan Response
}

func (o Oracle) handleMessage(v int)

func spawnOracle(maxValue int) {
	oracle := Oracle{value: 10, inputChannel: nil, outputChannel: nil}
}
