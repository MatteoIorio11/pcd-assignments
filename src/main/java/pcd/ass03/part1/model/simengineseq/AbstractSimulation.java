package pcd.ass03.part1.model.simengineseq;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import pcd.ass03.part1.model.agent.AkkaAgent;
import pcd.ass03.part1.model.simtrafficbase.AgentTask;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * Base class for defining concrete simulations
 *  
 */
public abstract class AbstractSimulation {

	/* environment of the simulation */
	private AbstractEnvironment env;
	
	/* list of the agents */
	private final List<AbstractAgent> agents;
	
	/* simulation listeners */
	private final List<SimulationListener> listeners;

	/* logical time step */
	private int dt;
	
	/* initial logical time */
	private int t0;

	/* in the case of sync with wall-time */
	private boolean toBeInSyncWithWallTime;
	private int nStepsPerSec;
	
	/* for time statistics*/
	private long currentWallTime;
	private long startWallTime;
	private long endWallTime;
	private long averageTimePerStep;
	private volatile int numSteps;

	protected AbstractSimulation() {
		agents = new ArrayList<>();
		listeners = new ArrayList<>();
		toBeInSyncWithWallTime = false;
		this.numSteps = 0;
	}
	
	/**
	 * 
	 * Method used to configure the simulation, specifying env and agents
	 * 
	 */
	public abstract void setup();
	
	/**
	 * Method running the simulation for a number of steps,
	 * using a sequential approach
	 * 
	 * @param numSteps
	 */
	public void run(int numSteps) {
		this.numSteps = numSteps;
		startWallTime = System.currentTimeMillis();

		/* initialize the env and the agents inside */
		int t = t0;

		env.init();
		final List<ActorRef<AkkaAgent.AgentBehaviors>> akkaAgents = new LinkedList<>();
		for (var a: agents) {
			akkaAgents.add(ActorSystem.create(AkkaAgent.create(a), "a"));
			a.init(env);
		}

		this.notifyReset(t, agents, env);
		
		long timePerStep = 0;
		int nSteps = 0;

		final var service = ForkJoinPool.commonPool();
		final AgentSynchronizer agentSynchronizer = AgentSynchronizer.getInstance();

		while (nSteps < this.numSteps) {

			currentWallTime = System.currentTimeMillis();
			/* make a step */
			env.step(dt);

			var adt = new AkkaAgent.AgentBehaviors.UpdateAgent(dt);
			akkaAgents.forEach(agent -> {
				agent.tell(adt);
				agent.tell(new AkkaAgent.AgentBehaviors.SenseDecide());
				agent.tell(new AkkaAgent.AgentBehaviors.Act());
			});

			t += dt;
			
			notifyNewStep(t, agents, env);

			nSteps++;			
			timePerStep += System.currentTimeMillis() - currentWallTime;
			
			if (toBeInSyncWithWallTime) {
				syncWithWallTime();
			}
		}	

		service.close();
		endWallTime = System.currentTimeMillis();
		this.averageTimePerStep = timePerStep / numSteps;
		
	}

	public void stopSimulation(){
		this.numSteps = 0;
	}

	public long getSimulationDuration() {
		return endWallTime - startWallTime;
	}
	
	public long getAverageTimePerCycle() {
		return averageTimePerStep;
	}
	
	/* methods for configuring the simulation */
	protected void setupTimings(int t0, int dt) {
		this.dt = dt;
		this.t0 = t0;
	}
	
	protected void syncWithTime(int nCyclesPerSec) {
		this.toBeInSyncWithWallTime = true;
		this.nStepsPerSec = nCyclesPerSec;
	}
		
	protected void setupEnvironment(AbstractEnvironment env) {
		this.env = env;
	}

	protected void addAgent(AbstractAgent agent) {
		agents.add(agent);
	}
	
	/* methods for listeners */
	
	public void addSimulationListener(SimulationListener l) {
		this.listeners.add(l);
	}
	
	private void notifyReset(int t0, List<AbstractAgent> agents, AbstractEnvironment env) {
		for (var l: listeners) {
			l.notifyInit(t0, agents, env);
		}
	}

	private void notifyNewStep(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
		//env.notifyAll();
		for (var l: listeners) {
			l.notifyStepDone(t, agents, env);
		}
	}

	/* method to sync with wall time at a specified step rate */
	private void syncWithWallTime() {
		try {
			long newWallTime = System.currentTimeMillis();
			long delay = 1000 / this.nStepsPerSec;
			long wallTimeDT = newWallTime - currentWallTime;
			if (wallTimeDT < delay) {
				Thread.sleep(delay - wallTimeDT);
			}
		} catch (Exception ex) {}		
	}
}
