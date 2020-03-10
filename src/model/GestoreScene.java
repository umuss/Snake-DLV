package model;

import javafx.scene.Scene;

public class GestoreScene {
	
	public static Scene scenaCorrente;
	public GestoreScene(Scene scene) {
		scenaCorrente=scene;
	}
	public static Scene getScenaCorrente() {
		return scenaCorrente;
	}
	public static void setScenaCorrente(Scene scena) {
		scenaCorrente = scena;
	}
	

}
