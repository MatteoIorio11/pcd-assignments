package main

type Oracle struct {
	value         int
	inputChannel  []chan Message
	outputChannel []chan Response
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
	oracle := Oracle{value: 10, inputChannel: inputC, outputChannel: outputC}
	return oracle
}
