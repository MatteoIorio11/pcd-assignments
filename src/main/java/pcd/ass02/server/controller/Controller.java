package pcd.ass02.server.controller;

import pcd.ass02.server.model.lib.CounterModality;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Controller {



    public List<CounterModality> getAlgorithms(){
        return Arrays.stream(CounterModality.values()).toList();
    }

    public void startSearch(final String url, final int depth, final String word, final CounterModality algorith){
        if(Objects.nonNull(url) &&
        Objects.nonNull(word) &&
        Objects.nonNull(algorith) &&
        depth >= 0){
            switch (algorith)  {
                case EVENT -> this.startEventLoop();
                case REACTIVE -> this.startReactive();
                case VIRTUAL -> this.startVirtual();
            }
        }else{
            throw new IllegalArgumentException("One of the input argument is not correct");
        }
    }

    private void startVirtual() {
    }

    private void startReactive() {
    }

    private void startEventLoop(){

    }
}
