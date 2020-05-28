package view;

import javafx.beans.property.SimpleStringProperty;

public class HighscoreEntry {

	private String name;
	private String score;

	public HighscoreEntry(String s1, String s2) {
		name = s1;
		score = s2;
	}

	public String getName() {
		return name;
	}

	public String getScore() {
		return score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScore(String score) {
		this.score = score;
	}

}