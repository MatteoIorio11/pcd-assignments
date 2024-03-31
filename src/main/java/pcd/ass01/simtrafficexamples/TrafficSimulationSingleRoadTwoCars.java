package pcd.ass01.simtrafficexamples;

import pcd.ass01.model.simengineseq.AgentSynchronizer;
import pcd.ass01.model.simtrafficbase.RoadsEnv;
import pcd.ass01.model.simengineseq.AbstractSimulation;
import pcd.ass01.model.simtrafficbase.CarAgent;
import pcd.ass01.model.simtrafficbase.CarAgentBasic;
import pcd.ass01.model.simtrafficbase.P2d;
import pcd.ass01.model.simtrafficbase.Road;

/**
 * 
 * Traffic Simulation about 2 cars moving on a single road, no traffic lights
 * 
 */
public class TrafficSimulationSingleRoadTwoCars extends AbstractSimulation {

	public static final int N_WORKERS = 2;

	public TrafficSimulationSingleRoadTwoCars() {
		super();
	}
	
	public void setup() {
		
		int t0 = 0;
		int dt = 1;
		
		this.setupTimings(t0, dt);
		
		RoadsEnv env = new RoadsEnv();
		this.setupEnvironment(env);
		final AgentSynchronizer agentSynchronizer = AgentSynchronizer.getInstance(N_WORKERS);

		Road r = env.createRoad(new P2d(0,300), new P2d(1500,300));
		CarAgent car1 = new CarAgentBasic("car-1", env, r, 0, 0.1, 0.2, 8);
		this.addAgent(car1);		
		CarAgent car2 = new CarAgentBasic("car-2", env, r, 100, 0.1, 0.1, 7);
		this.addAgent(car2);
		
		/* sync with wall-time: 25 steps per sec */
		this.syncWithTime(25);
	}	
	
}
