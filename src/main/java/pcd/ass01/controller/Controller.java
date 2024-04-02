package pcd.ass01.controller;

import pcd.ass01.model.simengineseq.AbstractSimulation;
import pcd.ass01.simtrafficexamples.*;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private static final int N_CARS_MASSIVE = 5_000;
    private final Map<String, AbstractSimulation> simulationMap;
    private record Pair(AbstractSimulation simulation, Optional<RoadSimView> view) {}

    private Optional<Pair> runningSimulation;
    public Controller(){
        this.runningSimulation = Optional.empty();
        this.simulationMap = Stream.of(new TrafficSimulationSingleRoadSeveralCars(),
                new TrafficSimulationSingleRoadTwoCars(),
                new TrafficSimulationSingleRoadWithTrafficLightTwoCars(),
                new TrafficSimulationWithCrossRoads(),
                new TrafficSimulationSingleRoadMassiveNumberOfCars(N_CARS_MASSIVE))
                .collect(Collectors.toMap(cl -> cl.getClass().getName(),
                        cl -> cl));
    }

    public List<String> getSimulations(){
        return this.simulationMap.keySet().stream().toList();
    }

    public void run(final String simulation, final int steps) {
        final AbstractSimulation sim = this.simulationMap.get(simulation);
        Optional<RoadSimView> simView = Optional.empty();
        sim.setup();
        if(steps > 0){
            if(!(sim instanceof TrafficSimulationSingleRoadMassiveNumberOfCars)){
                final RoadSimStatistics stat = new RoadSimStatistics();
                final RoadSimView view = new RoadSimView();
                view.display();
                simView = Optional.of(view);
                sim.addSimulationListener(stat);
                sim.addSimulationListener(view);
            }
            final Thread simulationThread = new Thread(() -> {
                sim.run(steps);
            });

            this.runningSimulation = Optional.of(new Pair(sim, simView));
            simulationThread.start();
        }else {
            throw new IllegalArgumentException("The step number must be greater than zero.");
        }
    }

    public void stopSimulation(){
        if(this.runningSimulation.isPresent()){
                this.runningSimulation.ifPresent(simulation -> {
                    simulation.simulation.stopSimulation();
                    simulation.view.ifPresent(Window::dispose);
                });
        }
    }
}
