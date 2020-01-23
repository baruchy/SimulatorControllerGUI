package view;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import utils.StringUtils;
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
    Button loadScript;
    @FXML
    Button runScript;
    @FXML
    RadioButton autoPilot;
    @FXML
    RadioButton manual;
	@FXML
	Circle frame;
	@FXML
	Circle joystick;
	boolean isConnected;
	boolean isScriptLoaded;
	ViewModel viewModel;
	
	public MainWindowController() {
		isConnected = false;
		isScriptLoaded = false;
	}

	public void setViewModel(ViewModel vm) {
		viewModel = vm;
		vm.joyStickX.bind(joystick.centerXProperty());
		vm.joyStickY.bind(joystick.centerYProperty());
		vm.throttle.bind(throttle.valueProperty());
		vm.rudder.bind(rudder.valueProperty());
	}

	@FXML
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
			if (ip == null || port == null || "".equalsIgnoreCase(ip) || "".equalsIgnoreCase(port)) {
				popuper("Please fill in all the feilds");
			} else if (!StringUtils.isDouble(port)) {
				popuper("Please enter a valid port number");
			} else {
				viewModel.connectToSimVM(ip, port);
				popup.close();
				System.out.println("connected to FlightGear...");
				isConnected = true;
			}
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
	public void loadScript() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose script");
		fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("txt", new String[] { "*.txt" }));
		final File chosenFile = fileChooser.showOpenDialog((Window) null);
		try (final Scanner s = new Scanner(new FileReader(chosenFile)).useDelimiter("\n");) {
			this.isScriptLoaded = true;
			while (s.hasNext()) {
				textBox.appendText(String.valueOf(s.next()) + "\n");
			}
		} catch (Exception ex) {
			isScriptLoaded = false;
		}
	}

	@FXML
	public void runScript() {
		if (isConnected && isScriptLoaded) {
			viewModel.runScript(textBox.getText());
		} else {
			if (!isConnected) {
				popuper("Please connect first");
			}
			if (!isScriptLoaded) {
				popuper("Please load script first");
			}
		}
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
	
	@FXML
	public void manualMode() {
        autoPilot.disarm();
        setManualComponentsDisable(false);
        setAutoPilotComponentsDisable(true);
    }
	
	@FXML
	public void autoPilotMode() {
        manual.disarm();
        setManualComponentsDisable(true);
        setAutoPilotComponentsDisable(false);
    }
	
	private void setManualComponentsDisable(boolean isDisabled) {
		manual.setSelected(!isDisabled);
		rudder.setDisable(isDisabled);
        throttle.setDisable(isDisabled);
        joystick.setDisable(isDisabled);
	}
	
	private void setAutoPilotComponentsDisable(boolean isDisabled) {
		autoPilot.setSelected(!isDisabled);
		loadScript.setDisable(isDisabled);
        runScript.setDisable(isDisabled);
        textBox.setDisable(isDisabled);
	}
}
