package pcd.ass01.simtrafficbase;

import pcd.ass01.simengineseq.AgentSynchronizer;

import java.util.Optional;

/**
 * 
 * Extended Car behaviour, considering near cars and semaphores
 * 
 * If there is a car and it is near, slow down.
 * If there are no cars or the car is far, accelerate up to a constant speed
 * 
 */
public class CarAgentExtended extends CarAgent {

	private static final int CAR_NEAR_DIST = 15;
	private static final int CAR_FAR_ENOUGH_DIST = 20;
	private static final int MAX_WAITING_TIME = 2;
	private static final int SEM_NEAR_DIST = 100;

	private enum CarAgentState { STOPPED, ACCELERATING, 
				DECELERATING_BECAUSE_OF_A_CAR, 
				DECELERATING_BECAUSE_OF_A_NOT_GREEN_SEM, 
				WAITING_FOR_GREEN_SEM,
				WAIT_A_BIT, MOVING_CONSTANT_SPEED}	
	
	private CarAgentState state;
	
	private int waitingTime;
	
	public CarAgentExtended(String id, RoadsEnv env, Road road,
							double initialPos,
							double acc,
							double dec,
							double vmax,
							final AgentSynchronizer agentSynchronizer) {
		super(id, env, road, initialPos, acc, dec, vmax, agentSynchronizer);
		state = CarAgentState.STOPPED;
	}
	
	
	@Override
	public void decide(int dt) {
		switch (state) {
		case CarAgentState.STOPPED:
			if (!detectedNearCar()) {
				state = CarAgentState.ACCELERATING;
			}
			break;
		case CarAgentState.ACCELERATING:
			if (detectedNearCar()) {
				state = CarAgentState.DECELERATING_BECAUSE_OF_A_CAR;
			} else if (detectedRedOrOrgangeSemNear()) {
				state = CarAgentState.DECELERATING_BECAUSE_OF_A_NOT_GREEN_SEM;
			} else {
				this.currentSpeed += acceleration * dt;
				if (currentSpeed >= maxSpeed) {
					state = CarAgentState.MOVING_CONSTANT_SPEED;
				}			
			}
			break;
		case CarAgentState.MOVING_CONSTANT_SPEED:
			if (detectedNearCar()) {
				state = CarAgentState.DECELERATING_BECAUSE_OF_A_CAR;
			} else if (detectedRedOrOrgangeSemNear()) {
				state = CarAgentState.DECELERATING_BECAUSE_OF_A_NOT_GREEN_SEM;
			}
			break;
		case CarAgentState.DECELERATING_BECAUSE_OF_A_CAR:
			this.currentSpeed -= deceleration * dt;
			if (this.currentSpeed <= 0) {
				state =  CarAgentState.STOPPED;
			} else if (this.carFarEnough()) {
				state = CarAgentState.WAIT_A_BIT;
				waitingTime = 0;
			}
			break;
		case CarAgentState.DECELERATING_BECAUSE_OF_A_NOT_GREEN_SEM:
			this.currentSpeed -= deceleration * dt;
			if (this.currentSpeed <= 0) {
				state =  CarAgentState.WAITING_FOR_GREEN_SEM;
			} else if (!detectedRedOrOrgangeSemNear()) {
				state = CarAgentState.ACCELERATING;
			}
			break;
		case CarAgentState.WAIT_A_BIT:
			waitingTime += dt;
			if (waitingTime > MAX_WAITING_TIME) {
				state = CarAgentState.ACCELERATING;
			}
			break;
		case CarAgentState.WAITING_FOR_GREEN_SEM:
			if (detectedGreenSem()) {
				state = CarAgentState.ACCELERATING;
			}
			break;		
		}
		
		if (currentSpeed > 0) {
			selectedAction = Optional.of(new MoveForward(currentSpeed * dt));
		}

	}
		
	private boolean detectedNearCar() {
		Optional<CarAgentInfo> car = currentPercept.nearestCarInFront();
		if (car.isEmpty()) {
			return false;
		} else {
			double dist = car.get().getPos() - currentPercept.roadPos();
			return dist < CAR_NEAR_DIST;
		}
	}
	
	private boolean detectedRedOrOrgangeSemNear() {
		Optional<TrafficLightInfo> sem = currentPercept.nearestSem();
		if (sem.isEmpty() || sem.get().sem().isGreen()) {
			return false;
		} else {
			double dist = sem.get().roadPos() - currentPercept.roadPos();
			return dist > 0 && dist < SEM_NEAR_DIST;
		}
	}


	private boolean detectedGreenSem() {
		Optional<TrafficLightInfo> sem = currentPercept.nearestSem();
		return (!sem.isEmpty() && sem.get().sem().isGreen());
	}
	
	private boolean carFarEnough() {
		Optional<CarAgentInfo> car = currentPercept.nearestCarInFront();
		if (car.isEmpty()) {
			return true;
		} else {
			double dist = car.get().getPos() - currentPercept.roadPos();
			return dist > CAR_FAR_ENOUGH_DIST;
		}
	}

}
