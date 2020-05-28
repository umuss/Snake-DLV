package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.GestoreScene;

public class HighScoreController {

	@FXML
	private TableView tableClassifica;

    @FXML
    private Label theLabel;
	
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
			JOptionPane.showMessageDialog(null, "Congratulazioni! Hai totalizzato il punteggio pi� alto fin'ora.");
		

		tableClassifica.getItems().add(entry);

	}

	@FXML
	void tornaAlMenu(ActionEvent event) {
		GestoreScene.getPrimaryStage().setScene(GestoreScene.getScenaMenu());
		GestoreScene.getPrimaryStage().sizeToScene();

	}

	public void initView() {
		try {
			theLabel.setFont(Font.loadFont(new FileInputStream(new File("assets/font.ttf")), 41));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
