package pcd.ass02.part1.model.simengineseq;

/**
 * 
 * Base  class for defining types of agents taking part to the simulation
 * 
 */
public abstract class AbstractAgent {
	
	protected String myId;
	private AbstractEnvironment env;
	protected volatile boolean flag = false;
	
	/**
	 * Each agent has an identifier
	 * 
	 * @param id
	 */
	protected AbstractAgent(String id) {
		this.myId = id;
	}

	/**
	 * This method is called at the beginning of the simulation
	 * 
	 * @param env
	 */
	public void init(AbstractEnvironment env) {
		this.env = env;
	}
	
	abstract public void senseStep();
	abstract public void decideStep(int dt);
	abstract public void actStep();

	protected AbstractEnvironment getEnv() {
		return this.env;
	}

	public String getId(){
		return this.myId;
	}
}
