package pcd.ass02.server.controller;

import pcd.ass02.server.model.lib.CounterModality;

import java.util.Arrays;
import java.util.List;

public class Controller {

    public List<CounterModality> getAlgorithms(){
        return Arrays.stream(CounterModality.values()).toList();
    }

    public void startSearch(final String url, final int depth, final String word){

    }
}
