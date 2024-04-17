package pcd.ass02.executors.simtrafficexamples;

import pcd.ass02.executors.model.simengineseq.AgentSynchronizer;
import pcd.ass02.executors.model.simtrafficbase.CarAgentExtended;
import pcd.ass02.executors.model.simtrafficbase.RoadsEnv;
import pcd.ass02.executors.model.simtrafficbase.TrafficLight;
import pcd.ass02.executors.model.simengineseq.AbstractSimulation;
import pcd.ass02.executors.model.simtrafficbase.CarAgent;
import pcd.ass02.executors.model.simtrafficbase.P2d;
import pcd.ass02.executors.model.simtrafficbase.Road;

/**
 * 
 * Traffic Simulation about 2 cars moving on a single road, with one semaphore
 * 
 */
public class TrafficSimulationSingleRoadWithTrafficLightTwoCars extends AbstractSimulation {

	public static final int N_WORKERS = 2;

	public TrafficSimulationSingleRoadWithTrafficLightTwoCars() {
		super();
	}
	
	public void setup() {

		this.setupTimings(0, 1);
		
		RoadsEnv env = new RoadsEnv();
		final AgentSynchronizer agentSynchronizer = AgentSynchronizer.getInstance();
		this.setupEnvironment(env);
				
		Road r = env.createRoad(new P2d(0,300), new P2d(1500,300));

		TrafficLight tl = env.createTrafficLight(new P2d(740,300), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
		r.addTrafficLight(tl, 740);
		
		CarAgent car1 = new CarAgentExtended("car-1", env, r, 0, 0.1, 0.3, 6);
		this.addAgent(car1);		
		CarAgent car2 = new CarAgentExtended("car-2", env, r, 100, 0.1, 0.3, 5);
		this.addAgent(car2);

		this.syncWithTime(25);
	}	
	
}
