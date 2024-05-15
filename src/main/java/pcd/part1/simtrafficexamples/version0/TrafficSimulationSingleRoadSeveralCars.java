package pcd.part1.simtrafficexamples.version0;

import pcd.part1.simengineseq.version0.AbstractSimulation;
import pcd.part1.simtrafficbase.version0.CarAgent;
import pcd.part1.simtrafficbase.version0.CarAgentBasic;
import pcd.part1.simtrafficbase.version0.P2d;
import pcd.part1.simtrafficbase.version0.Road;
import pcd.part1.simtrafficbase.version0.RoadsEnv;

/**
 * 
 * Traffic Simulation about a number of cars 
 * moving on a single road, no traffic lights
 * 
 */
public class TrafficSimulationSingleRoadSeveralCars extends AbstractSimulation {

	public TrafficSimulationSingleRoadSeveralCars() {
		super();
	}
	
	public void setup() {

		this.setupTimings(0, 1);

		RoadsEnv env = new RoadsEnv();
		this.setupEnvironment(env);
		
		Road road = env.createRoad(new P2d(0,300), new P2d(1500,300));

		int nCars = 30;

		for (int i = 0; i < nCars; i++) {
			
			String carId = "car-" + i;
			// double initialPos = i*30;
			double initialPos = i*10;
			
			double carAcceleration = 1; //  + gen.nextDouble()/2;
			double carDeceleration = 0.3; //  + gen.nextDouble()/2;
			double carMaxSpeed = 7; // 4 + gen.nextDouble();
						
			CarAgent car = new CarAgentBasic(carId, env, 
									road,
									initialPos, 
									carAcceleration, 
									carDeceleration,
									carMaxSpeed);
			this.addAgent(car);
		}
		
		this.syncWithTime(25);
	}	
}
	