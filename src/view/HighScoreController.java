package view;

import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.GestoreScene;

public class HighScoreController {

	@FXML
	private TableView tableClassifica;

	public void setScore(HighscoreEntry entry) {
		tableClassifica.getItems().add(entry);

	}

	@FXML
	void tornaAlMenu(ActionEvent event) {
		GestoreScene.getPrimaryStage().setScene(GestoreScene.getScenaMenu());
	}

	public void initView() {
		TableColumn<String, HighscoreEntry> column1 = new TableColumn<>("Nome");
		column1.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<String, HighscoreEntry> column2 = new TableColumn<>("Punteggio");
		column2.setCellValueFactory(new PropertyValueFactory<>("score"));

		tableClassifica.getColumns().add(column1);
		tableClassifica.getColumns().add(column2);

	}

}
