package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import view_model.ViewModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	public static String dataServerPort;
	public static String dataServerPace;
	@Override
	public void start(Stage primaryStage) {
		try {
			ViewModel vm = new ViewModel(new Model());
			FXMLLoader fxl = new FXMLLoader();
			BorderPane root = fxl.load(getClass().getResource("MainWindow.fxml").openStream());
			MainWindowController mwc = fxl.getController();
			mwc.setViewModel(vm);
			Scene scene = new Scene(root, 800, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest((e) -> vm.disconnect());
			primaryStage.show();
			vm.openDataServer(dataServerPort, dataServerPace);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		dataServerPort = args[0];
		dataServerPace = args[1];
		launch(args);
	}
}
