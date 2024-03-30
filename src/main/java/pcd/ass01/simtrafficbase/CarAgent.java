package pcd.ass01.simtrafficbase;

import java.util.Objects;
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
		this.selectedAction = Optional.empty();
	}

	/**
	 * 
	 * Basic behaviour of a car agent structured into a sense/decide/act structure 
	 * 
	 */
	public void step(final int dt) {
		this.deltaTime = dt;
	}

	@Override
	public void run() {
		final AbstractEnvironment env = this.getEnv();
		this.currentPercept = (CarPercept) env.getCurrentPercepts(this.getId());

		/* decide */
		this.selectedAction = Optional.empty();
		decide(this.deltaTime);
	}


	@Override
	public boolean hasToWork(){
		return this.selectedAction.isPresent();
	}

	@Override
	public void act(){
		if (Objects.nonNull(this.selectedAction) && this.selectedAction.isPresent()) {
			this.agentSynchronizer.executeCriticalSection((action) -> this.getEnv().doAction(this.getId(), action), selectedAction.get());
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
