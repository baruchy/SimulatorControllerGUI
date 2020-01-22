package view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import view.MainWindowController;
import view_model.ViewModel;

public class MainWindowController {
	@FXML
	public Slider throttle;
	@FXML
	public Slider rudder;
	@FXML
	public TextArea textBox;
	@FXML
	Button connect;
	@FXML
	Circle frame;
	@FXML
	Circle joystick;
	boolean isConnected = false;
	ViewModel viewModel;

	public void setViewModel(ViewModel vm) {
		viewModel = vm;
		vm.joyStickX.bind(joystick.centerXProperty());
		vm.joyStickY.bind(joystick.centerYProperty());
		vm.throttle.bind(throttle.valueProperty());
		vm.rudder.bind(rudder.valueProperty());
	}

	public void connect() {
		Stage popup = new Stage();
		VBox box = new VBox(20.0D);
		Label ipLabel = new Label("IP:");
		Label portLabel = new Label("PORT:");
		TextField ipUserInput = new TextField();
		TextField portUserInput = new TextField();
		Button submit = new Button("Submit");
		box.getChildren().addAll(new Node[] { ipLabel, ipUserInput, portLabel, portUserInput, submit });
		popup.setScene(new Scene(box, 350.0D, 250.0D));
		popup.setTitle("Connect to FlightGear");
		popup.show();
		submit.setOnAction((e) -> {
			String ip = ipUserInput.getText();
			String port = portUserInput.getText();
			viewModel.connectToSimVM(ip, port);
			popup.close();
			System.out.println("connected to FlightGear...");
			isConnected = true;
		});
	}

	public void popuper(String name) {
		Stage popup = new Stage();
		VBox box = new VBox(10.0D);
		Label msg = new Label(name);
		Button ok = new Button("ok");
		box.getChildren().addAll(new Node[] { msg, ok });
		popup.setScene(new Scene(box, 250.0D, 100.0D));
		popup.setTitle("Massage");
		popup.show();
		ok.setOnAction((e) -> {
			if (name.contains("connect")) {
				connect();
			}
			popup.close();
		});
	}

	@FXML
	private void onJoystickDragged(MouseEvent event) {
		double x = event.getX(), y = event.getY();
		event.consume();
		double radiusLimit = frame.getRadius() - joystick.getRadius();
		double vectorLength = Math.sqrt(Math.pow(x, 2.0D) + Math.pow(y, 2.0D));
		if (vectorLength <= radiusLimit) {
			joystick.setCenterX(x);
			joystick.setCenterY(y);
		} else {
			joystick.setCenterX(x * radiusLimit / vectorLength);
			joystick.setCenterY(y * radiusLimit / vectorLength);
		}
	}

	@FXML
	private void onJoystickReleased() {
		joystick.setCenterX(0.0D);
		joystick.setCenterY(0.0D);
	}
}
