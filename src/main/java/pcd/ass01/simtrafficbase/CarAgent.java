package pcd.ass01.simtrafficbase;

import java.util.Optional;

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

	/* percept and action retrieved and submitted at each step */
	protected CarPercept currentPercept;
	protected Optional<Action> selectedAction;
	
	
	public CarAgent(String id, RoadsEnv env, Road road, 
			double initialPos, 
			double acc, 
			double dec,
			double vmax) {
		super(id);
		this.acceleration = acc;
		this.deceleration = dec;
		this.maxSpeed = vmax;
		env.registerNewCar(this, road, initialPos);
	}

	/**
	 * 
	 * Basic behaviour of a car agent structured into a sense/decide/act structure 
	 * 
	 */
	public void step(int dt) {

		/* sense */
		// access the environment one at the time, if a thread is in process to modify the environment the thread must stop here and wait
		AbstractEnvironment env = this.getEnv();		
		currentPercept = (CarPercept) env.getCurrentPercepts(getId());			

		/* decide */
		selectedAction = Optional.empty();

		// implement the Runnable interface and start the thread for the decision process, it can be done
		// without any lock
		decide(dt);

		// add barrier
		/* act */
		if (selectedAction.isPresent()) {
			// take the lock in order to modify the environment
			env.doAction(getId(), selectedAction.get());
			// release the lock
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
