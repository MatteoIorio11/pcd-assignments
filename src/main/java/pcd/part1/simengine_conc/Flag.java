package pcd.part1.simengine_conc;

public class Flag {

	private boolean flag;
	
	public synchronized void reset() {
		flag = false;
	}
	
	public synchronized void set() {
		flag = true;
	}
	
	public synchronized boolean isSet() {
		return flag;
	}
}
