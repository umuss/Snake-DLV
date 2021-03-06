package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.GestoreScene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// BorderPane root =
			// (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			BorderPane root = (BorderPane) loader.load();
			BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
			BackgroundImage myBI = new BackgroundImage(new Image("file:assets/background.png"), BackgroundRepeat.REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
			root.setBackground(new Background(myBI));
			SampleController sampleController = loader.getController();
			Scene scene = new Scene(root, 800, 840);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			GestoreScene.setScenaCorrente(scene);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);

			// Andrebbe presa da DLV tipo
			// sampleController.drawMela(mela);
			sampleController.drawSnake();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
