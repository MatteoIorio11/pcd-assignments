package pcd.part1.simengine_conc.gui;

import pcd.part1.simtraffic_conc_examples.*;

/**
 * 
 * Main class to create and run a simulation - with GUI
 * 
 */
public class RunTrafficSimulation {

	private static final int DEFAULT_STEPS = 10000;
	
	public static void main(String[] args) {		

		int nWorkers = Runtime.getRuntime().availableProcessors() + 1;

		// var simulation = new TrafficSimulationSingleRoadTwoCars();
		// var simulation = new TrafficSimulationSingleRoadSeveralCars();
		// var simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
		
		var simulation = new TrafficSimulationWithCrossRoads();
		
		simulation.configureNumWorkers(nWorkers);
        SimulationGUI gui = new SimulationGUI(DEFAULT_STEPS);

		SimulationController controller = new SimulationController(simulation);
		controller.attach(gui);
        gui.display();
				
	}
}
