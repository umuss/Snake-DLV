package model;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.HighScoreController;

public class GestoreScene {
	public static HashMap<String, Integer> scoreboard = new HashMap<>();

	private static Scene scenaMenu = null;
	private static Scene scenaGioco = null;
	private static Scene scenaGiocoHuman = null;
	private static Scene scenaScoreboard = null;
	private static Stage primaryStage = null;
	private static HighScoreController highScoreController = null;
	
	public static void setPrimaryStage(Stage stage) {
		primaryStage = stage;
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void setHighScoreController(HighScoreController highScoreController) {
		GestoreScene.highScoreController = highScoreController;
	}
	public static HighScoreController getHighScoreController() {
		return highScoreController;
	}
	
	public static void setScenaMenu(Scene scena) {
		scenaMenu = scena;
	}
	public static void setScenaGioco(Scene scena) {
		scenaGioco = scena;
	}
	public static void setScenaScoreboard(Scene scena) {
		scenaScoreboard = scena;
	}
	
	
	public static Scene getScenaGioco() {
		return scenaGioco;
	}
	public static Scene getScenaMenu() {
		return scenaMenu;
	}
	
	public static Scene getScenaScoreboard() {
		return scenaScoreboard;
	}

	public static void setScenaGiocoHuman(Scene sceneGame) {
		scenaGiocoHuman = sceneGame;
		
	}
	
	public static Scene getScenaGiocoHuman() {
		return scenaGiocoHuman;
	}
	
	

}
