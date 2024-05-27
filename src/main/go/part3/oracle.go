package main

type Oracle struct {
	value         int
	inputChannel  []chan Message
	outputChannel []chan Response
}

func (o Oracle) handleMessage(v int)

func createChannels[X any](size int, channelType X) []chan X {
	channels := make([]chan X, size)
	for i := 0; i < size; i++ {
		channels[i] = make(chan X)
	}
	return channels
}

func spawnOracle(maxValue int, players int) {
	oracle := Oracle{value: 10, inputChannel: nil, outputChannel: nil}
}
