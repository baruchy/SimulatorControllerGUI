package model;

import command.ConnectCommand;
import connect.SimulatorClient;
import java.util.Arrays;
import java.util.Observable;

public class Model extends Observable {
	private volatile boolean connectedToSimulator = false;

	public Model() {
		this.setChanged();
	}
	
	public void setElevator(double elevator) {
		if (!connectedToSimulator) {
			return;
		}
		SimulatorClient.getInstance().set("/controls/flight/elevator ", elevator);
	}
	
	public void setAileron(double aileron) {
		if (!connectedToSimulator) {
			return;
		}
		SimulatorClient.getInstance().set("/controls/flight/aileron ", aileron);
	}

	public void setRudder(double rudder) {
		if (!connectedToSimulator) {
			return;
		}
		SimulatorClient.getInstance().set("/controls/flight/rudder", rudder);
	}

	public void setThrottle(double throttle) {
		if (!connectedToSimulator) {
			return;
		}
		SimulatorClient.getInstance().set("/controls/engines/current-engine/throttle", throttle);
	}

	public void connectToSimulator(String ip, String port) {
		new ConnectCommand().doCommand(Arrays.asList(ip, port));
		connectedToSimulator = true;
	}
}
