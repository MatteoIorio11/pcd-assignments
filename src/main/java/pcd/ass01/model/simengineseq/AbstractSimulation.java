package pcd.ass01.model.simengineseq;

import pcd.ass01.model.simtrafficbase.AgentPoolWorker;

import java.util.*;

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
	private Map<String, Thread> map = new HashMap<>();

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
	private final int numProcessors = Runtime.getRuntime().availableProcessors();

	protected AbstractSimulation() {
		agents = new ArrayList<AbstractAgent>();
		listeners = new ArrayList<SimulationListener>();
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
		for (var a: agents) {
			a.init(env);
		}

		this.notifyReset(t, agents, env);
		
		long timePerStep = 0;
		int nSteps = 0;
		final List<AgentPoolWorker> workers = this.getWorkers();

		while (nSteps < this.numSteps) {

			currentWallTime = System.currentTimeMillis();
			/* make a step */
			env.step(dt);

			final List<Thread> runningWorkers = workers.stream().peek(w -> w.setDt(dt)).map(Thread::new).toList();
			runningWorkers.forEach(Thread::start);
			runningWorkers.forEach(w -> {
				try {
					w.join();
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			});

			t += dt;
			
			notifyNewStep(t, agents, env);

			nSteps++;			
			timePerStep += System.currentTimeMillis() - currentWallTime;
			
			if (toBeInSyncWithWallTime) {
				syncWithWallTime();
			}
		}	
		
		endWallTime = System.currentTimeMillis();
		this.averageTimePerStep = timePerStep / numSteps;
		
	}

	private List<AgentPoolWorker> getWorkers() {
		final int nWorkers = Math.min(this.agents.size(), numProcessors);
		final List<AgentPoolWorker> workers = new ArrayList<>(nWorkers);
		final Map<Integer, List<AbstractAgent>> map = new HashMap<>();

		for (int i = 0; i < nWorkers; i++) {
			final int agentsPerWorker = this.agents.size() / nWorkers;
			final int startIndex = i * agentsPerWorker;
			final int endIndex = Math.min((i + 1) * agentsPerWorker, this.agents.size());
			final List<AbstractAgent> agentsSubList = new ArrayList<>(this.agents.subList(startIndex, endIndex));
			map.put(i, agentsSubList);
		}

		// Handle possible leftovers.
		if (this.agents.size() % nWorkers != 0) {
			final int agentsSize = this.agents.size();
			for (int i = 0; i < agentsSize % nWorkers; i++) {
				for (int j = 0; j < nWorkers; j++) {
					map.get(j).add(this.agents.get(agentsSize - i - 1));
					i++;
				}
			}
		}

		final AgentSynchronizer synchronize = AgentSynchronizer.getInstance(nWorkers);
		workers.addAll(
				map.values().stream().map(agents -> new AgentPoolWorker(agents, synchronize)).toList()
		);
		return workers;
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
