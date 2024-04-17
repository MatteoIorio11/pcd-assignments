package pcd.ass02.executors.simtrafficexamples;

import java.util.Random;

import pcd.ass02.executors.model.simengineseq.AgentSynchronizer;
import pcd.ass02.executors.model.simtrafficbase.CarAgentExtended;
import pcd.ass02.executors.model.simtrafficbase.RoadsEnv;
import pcd.ass02.executors.model.simtrafficbase.TrafficLight;
import pcd.ass02.executors.model.simengineseq.AbstractSimulation;
import pcd.ass02.executors.model.simtrafficbase.CarAgent;
import pcd.ass02.executors.model.simtrafficbase.P2d;
import pcd.ass02.executors.model.simtrafficbase.Road;

public class TrafficSimulationWithCrossRoads extends AbstractSimulation {

	public static final int N_WORKERS = 4;

	public TrafficSimulationWithCrossRoads() {
		super();
	}
	
	public void setup() {

		Random  gen = new Random(1);
		final AgentSynchronizer agentSynchronizer = AgentSynchronizer.getInstance(N_WORKERS);

		this.setupTimings(0, 1);
		
		RoadsEnv env = new RoadsEnv();
		this.setupEnvironment(env);
				
		TrafficLight tl1 = env.createTrafficLight(new P2d(740,300), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
		
		Road r1 = env.createRoad(new P2d(0,300), new P2d(1500,300));
		r1.addTrafficLight(tl1, 740);
		
		CarAgent car1 = new CarAgentExtended("car-1", env, r1, 0, 0.1, 0.3, 6);
		this.addAgent(car1);		
		CarAgent car2 = new CarAgentExtended("car-2", env, r1, 100, 0.1, 0.3, 5);
		this.addAgent(car2);		
		
		TrafficLight tl2 = env.createTrafficLight(new P2d(750,290),  TrafficLight.TrafficLightState.RED, 75, 25, 100);

		Road r2 = env.createRoad(new P2d(750,0), new P2d(750,600));
		r2.addTrafficLight(tl2, 290);

		CarAgent car3 = new CarAgentExtended("car-3", env, r2, 0, 0.1, 0.2, 5);
		this.addAgent(car3);		
		CarAgent car4 = new CarAgentExtended("car-4", env, r2, 100, 0.1, 0.1, 4);
		this.addAgent(car4);
		
		
		this.syncWithTime(25);
	}	
	
}
