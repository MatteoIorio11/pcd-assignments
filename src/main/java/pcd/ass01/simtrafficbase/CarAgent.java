package pcd.ass01.simtrafficbase;

import java.util.Optional;
import java.util.concurrent.BrokenBarrierException;

import pcd.ass01.simengineseq.*;

/**
 * 
 * Base class modeling the skeleton of an agent modeling a car in the traffic environment
 * 
 */
public abstract class CarAgent extends AbstractAgent {

	/* car model */
	protected double maxSpeed;		
	protected double currentSpeed;  
	protected double acceleration;
	protected double deceleration;
	private int deltaTime;

	/* percept and action retrieved and submitted at each step */
	protected CarPercept currentPercept;
	protected Optional<Action> selectedAction;
	protected AgentSynchronizer agentSynchronizer;
	
	
	public CarAgent(String id, RoadsEnv env, Road road, 
			double initialPos, 
			double acc, 
			double dec,
			double vmax, final AgentSynchronizer agentSynchronizer) {
		super(id);
		this.deltaTime = 0;
		this.acceleration = acc;
		this.deceleration = dec;
		this.maxSpeed = vmax;
		env.registerNewCar(this, road, initialPos);
		this.agentSynchronizer = agentSynchronizer;
	}

	/**
	 * 
	 * Basic behaviour of a car agent structured into a sense/decide/act structure 
	 * 
	 */
	public void step(int dt) {
		this.deltaTime = dt;
	}

	@Override
	public void run() {
		AbstractEnvironment env = this.getEnv();
		currentPercept = (CarPercept) env.getCurrentPercepts(this.getId());

		/* decide */
		selectedAction = Optional.empty();

		// implement the Runnable interface and start the thread for the decision process, it can be done
		// without any lock
		decide(this.deltaTime);

		// add barrier <-
		try {
			/* act */
            // take the lock in order to modify the environment
			this.agentSynchronizer.awaitBarrier();
			if (this.selectedAction.isPresent()) {
				this.agentSynchronizer.executeCriticalSection((action) -> env.doAction(this.getId(), action), selectedAction.get());
			}
		} catch (BrokenBarrierException | InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Base method to define the behaviour strategy of the car
	 * 
	 * @param dt
	 */
	protected abstract void decide(int dt);
	
	public double getCurrentSpeed() {
		return currentSpeed;
	}
	
	protected void log(String msg) {
		System.out.println("[CAR " + this.getId() + "] " + msg);
	}

	
}
