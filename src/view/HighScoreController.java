package view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
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
		ObservableList<HighscoreEntry> list = tableClassifica.getItems();
		boolean maxScore = true;
		for (HighscoreEntry hse : list) {
			if (Integer.valueOf(hse.getScore()) > Integer.valueOf(entry.getScore())) {
				maxScore = false;
				break;
			}
		}
		
		if (maxScore) 
			JOptionPane.showMessageDialog(null, "Congratulazioni! Hai totalizzato il punteggio più alto fin'ora.");
		

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
		
		column1.prefWidthProperty().bind(tableClassifica.widthProperty().multiply(0.5));
        column2.prefWidthProperty().bind(tableClassifica.widthProperty().multiply(0.5));

		tableClassifica.getColumns().add(column1);
		tableClassifica.getColumns().add(column2);

	}

}
