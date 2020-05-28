package view;

import java.io.IOException;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.GestoreScene;

public class Main extends Application {

	HashMap<String, Integer> scoreboard = new HashMap<>();

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setResizable(false);
			GestoreScene.setPrimaryStage(primaryStage);

			// Scena menu

			FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
			BorderPane menuRoot = (BorderPane) loaderMenu.load();
			Scene sceneMenu = new Scene(menuRoot, 600, 640);
			GestoreScene.setScenaMenu(sceneMenu);

			// Scena gioco

			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
			BorderPane root;
			root = (BorderPane) loader.load();
			BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
			BackgroundImage myBI = new BackgroundImage(new Image("file:assets/background.png"), BackgroundRepeat.REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
			root.setBackground(new Background(myBI));
			GameController gameController = loader.getController();
			Scene sceneGame = new Scene(root, 600, 640);
			sceneGame.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			GestoreScene.setScenaGioco(sceneGame);

			// Scena gioco human

			FXMLLoader loaderHuman = new FXMLLoader(getClass().getResource("GameViewHuman.fxml"));
			BorderPane rootHuman;
			rootHuman = (BorderPane) loaderHuman.load();
			rootHuman.setBackground(new Background(myBI));
			GameControllerHuman gameControllerHuman = loaderHuman.getController();
			Scene sceneGameHuman = new Scene(rootHuman, 600, 640);
			sceneGameHuman.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			GestoreScene.setScenaGiocoHuman(sceneGameHuman);

			// Scena scoreboard
			FXMLLoader scoreLoader = new FXMLLoader();
			scoreLoader.setLocation(getClass().getResource("HighScoreView.fxml"));
			GestoreScene.setScenaScoreboard(new Scene((BorderPane) scoreLoader.load(), 400, 400));
			GestoreScene.setHighScoreController(scoreLoader.getController());
			GestoreScene.getHighScoreController().initView();

			VBox menuButtons = (VBox) menuRoot.getCenter();
			Button playButton = (Button) menuButtons.getChildren().get(0);
			playButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					primaryStage.setScene(sceneGame);
					gameController.drawSnake();

				}
			});

			Button playHumanButton = (Button) menuButtons.getChildren().get(1);
			playHumanButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					primaryStage.setScene(sceneGameHuman);
					gameControllerHuman.drawSnake();
				}
			});

			primaryStage.setScene(sceneMenu);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
