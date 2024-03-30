package pcd.ass01.simtrafficbase;

import java.util.Optional;
import java.util.random.RandomGenerator;

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

	@Override
	public void senseStep() {
		AbstractEnvironment env = this.getEnv();
		currentPercept = (CarPercept) env.getCurrentPercepts(getId());
	}

	@Override
	public void decideStep(int dt) {
		selectedAction = Optional.empty();
		decide(dt);
	}

	@Override
	public void actStep() {
		if (this.selectedAction.isPresent()) {
			this.getEnv().doAction(this.getId(), selectedAction.get());
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
