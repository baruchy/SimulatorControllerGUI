package view_model;

import java.util.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.Model;

public class ViewModel extends Observable {
	private Model model;
	public DoubleProperty joyStickX;
	public DoubleProperty joyStickY;
	public DoubleProperty throttle;
	public DoubleProperty rudder;

	public ViewModel(Model model) {
		this.model = model;
		joyStickY = new SimpleDoubleProperty();
		joyStickX = new SimpleDoubleProperty();
		throttle = new SimpleDoubleProperty();
		rudder = new SimpleDoubleProperty();

		// when these values change, change the model values as well.
		throttle.addListener((o, old, nw) -> model.setThrottle(nw.doubleValue()));
		rudder.addListener((o, old, nw) -> model.setRudder(nw.doubleValue()));
		joyStickX.addListener((o, old, nw) -> model.setAileron(calculateJoystickValue(nw.doubleValue())));
		joyStickY.addListener((o, old, nw) -> model.setElevator(calculateJoystickValue(-nw.doubleValue())));
	}

	public void connectToSimVM(String ip, String port) {
		model.connectToSimulator(ip, port);
	}

	private double calculateJoystickValue(double value) {
		double newVal = value * 1.5875D / 100D;
		return (newVal > 1) ? 1 : (newVal < -1) ? -1 : newVal;
	}
}
