package pcd.part1.simtrafficexamples.version0;

import pcd.part1.simengineseq.version0.AbstractSimulation;
import pcd.part1.simtrafficbase.version0.CarAgent;
import pcd.part1.simtrafficbase.version0.CarAgentBasic;
import pcd.part1.simtrafficbase.version0.P2d;
import pcd.part1.simtrafficbase.version0.Road;
import pcd.part1.simtrafficbase.version0.RoadsEnv;

/**
 * 
 * Traffic Simulation about 2 cars moving on a single road, no traffic lights
 * 
 */
public class TrafficSimulationSingleRoadTwoCars extends AbstractSimulation {

	public TrafficSimulationSingleRoadTwoCars() {
		super();
	}
	
	public void setup() {
		
		int t0 = 0;
		int dt = 1;
		
		this.setupTimings(t0, dt);
		
		RoadsEnv env = new RoadsEnv();
		this.setupEnvironment(env);
		
		Road r = env.createRoad(new P2d(0,300), new P2d(1500,300));
		CarAgent car1 = new CarAgentBasic("car-1", env, r, 0, 0.1, 0.2, 8);
		this.addAgent(car1);		
		CarAgent car2 = new CarAgentBasic("car-2", env, r, 100, 0.1, 0.1, 7);
		this.addAgent(car2);
		
		/* sync with wall-time: 25 steps per sec */
		this.syncWithTime(25);
	}	
	
}
