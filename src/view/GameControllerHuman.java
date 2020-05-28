package view;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Pair;
import model.Coda;
import model.Direction;
import model.GestoreScene;
import model.Mela;
import model.Snake;

public class GameControllerHuman {
	@FXML
	private Canvas mainCanvas;
	private double frame = 5;
	boolean giaDisegnata = false;
	Snake snake = new Snake();
	Mela melaRossa = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_ROSSO);
	Mela melaBlu = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_BLU);
	Mela melaDorata = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_DORATO);
	boolean hoDisegnato = false;
	AnimationTimer tm = null;

	@FXML
	private Label labelPunteggio;

	@FXML
	public void interrompiGiocoHuman() {
		tm.stop();
		snake = new Snake();
		melaRossa = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_ROSSO);
		melaBlu = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_BLU);
		melaDorata = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_DORATO);
		labelPunteggio.setText("0");
		GestoreScene.getPrimaryStage().setScene(GestoreScene.getScenaMenu());
		GestoreScene.getPrimaryStage().sizeToScene();


	}
	
	
	public void drawSnake() {
		tm = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (frame >= 10) {
					hoDisegnato = true;
					verificaCollisionemelaRossa();
					verificaAutoCollisione();
					if (snake.getTesta().getDirection() == Direction.RIGHT) {
						verificaProssimaCella(Direction.RIGHT);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY(), 25, 25);
					}
					if (snake.getTesta().getDirection() == Direction.LEFT) {
						verificaProssimaCella(Direction.LEFT);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY(), 25, 25);
					}
					if (snake.getTesta().getDirection() == Direction.DOWN) {
						verificaProssimaCella(Direction.DOWN);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY(), 25, 25);
					}
					if (snake.getTesta().getDirection() == Direction.UP) {
						verificaProssimaCella(Direction.UP);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY(), 25, 25);
					}
					frame = 0;
					mainCanvas.getGraphicsContext2D().drawImage(melaRossa.getImage(), melaRossa.getPosX(),
							melaRossa.getPosY());
					if (melaBlu.isSpawned())
						mainCanvas.getGraphicsContext2D().drawImage(melaBlu.getImage(), melaBlu.getPosX(),
								melaBlu.getPosY());
					if (melaDorata.isSpawned())
						mainCanvas.getGraphicsContext2D().drawImage(melaDorata.getImage(), melaDorata.getPosX(),
								melaDorata.getPosY());
					for (Coda c : snake.getCode()) {
						mainCanvas.getGraphicsContext2D().drawImage(c.getImage(), c.getPosX(), c.getPosY(), 25, 25);
					}
				}
				frame += 1;
			}
		};
		tm.start();
		GestoreScene.getScenaGiocoHuman().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.DOWN && hoDisegnato && snake.getTesta().getDirection() != Direction.UP
						&& compreso()) {
					//System.out.println("down");
					// //System.out.println("DOWN");
					snake.getTesta().setDirection(Direction.DOWN);
					hoDisegnato = false;
				} else if (event.getCode() == KeyCode.RIGHT && hoDisegnato
						&& snake.getTesta().getDirection() != Direction.LEFT && compreso()) {
					snake.getTesta().setDirection(Direction.RIGHT);
					// //System.out.println("right");
					hoDisegnato = false;
				} else if (event.getCode() == KeyCode.LEFT && hoDisegnato
						&& snake.getTesta().getDirection() != Direction.RIGHT && compreso()) {
					snake.getTesta().setDirection(Direction.LEFT);
					// //System.out.println("left");
					hoDisegnato = false;
				} else if (event.getCode() == KeyCode.UP && hoDisegnato
						&& snake.getTesta().getDirection() != Direction.DOWN && compreso()) {
					snake.getTesta().setDirection(Direction.UP);
					// //System.out.println("up");
					hoDisegnato = false;
				}

			}
		});
	}

	public boolean compreso() {
		if ((snake.getTesta().getCol() >= 0 && snake.getTesta().getCol() <= 23)
				&& (snake.getTesta().getRow() >= 0 && snake.getTesta().getRow() <= 23))
			return true;
		return false;
	}

	public void verificaProssimaCella(Direction dir) {
		ArrayList<Pair<Integer, Integer>> posizioniVecchie = new ArrayList<>();
		if (dir == Direction.RIGHT) {
			if (snake.getTesta().getCol() >= 24) {
				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
				for (Coda c : snake.getCode()) {
					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
				}
				snake.getTesta().setCol(0);
				for (int i = 0; i < snake.getCode().size(); i++) {
					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
				}
				// //System.out.println("STO SFORANDO");
				// Caso non di sforamento
			} else {
				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
				for (Coda c : snake.getCode()) {
					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
				}
				snake.getTesta().setCol(snake.getTesta().getCol() + 1);
				for (int i = 0; i < snake.getCode().size(); i++) {
					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
				}
			}
		}
		if (dir == Direction.UP) {
			if (snake.getTesta().getRow() <= 0) {
				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
				for (Coda c : snake.getCode()) {
					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
				}
				snake.getTesta().setRow(23);
				for (int i = 0; i < snake.getCode().size(); i++) {
					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
				}
			} else {
				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
				for (Coda c : snake.getCode()) {
					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
				}
				snake.getTesta().setRow(snake.getTesta().getRow() - 1);
				for (int i = 0; i < snake.getCode().size(); i++) {
					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
				}
			}
		}
		if (dir == Direction.LEFT) {
			if (snake.getTesta().getCol() <= 0) {
				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
				for (Coda c : snake.getCode()) {
					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
				}
				snake.getTesta().setCol(23);
				for (int i = 0; i < snake.getCode().size(); i++) {
					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
				}
			} else {
				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
				for (Coda c : snake.getCode()) {
					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
				}
				snake.getTesta().setCol(snake.getTesta().getCol() - 1);
				for (int i = 0; i < snake.getCode().size(); i++) {
					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
				}
			}
		}
		if (dir == Direction.DOWN) {
			if (snake.getTesta().getRow() >= 24) {
				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
				for (Coda c : snake.getCode()) {
					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
				}
				snake.getTesta().setRow(0);
				for (int i = 0; i < snake.getCode().size(); i++) {
					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
				}
			} else {
				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
				for (Coda c : snake.getCode()) {
					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
				}
				snake.getTesta().setRow(snake.getTesta().getRow() + 1);
				for (int i = 0; i < snake.getCode().size(); i++) {
					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
				}
			}
		}

	}

	public void verificaCollisionemelaRossa() {
		if ((snake.getTesta().getRow() == melaRossa.getRow() && snake.getTesta().getCol() == melaRossa.getCol())) {
			//System.out.println("prendo melaRossa");
			snake.segnaPunto();
			labelPunteggio.setText(snake.getPunteggio().toString());
			melaRossa.setCol(new Random().nextInt(24));
			melaRossa.setRow(new Random().nextInt(24));
			snake.getCode().add(new Coda(snake.getCode().get(snake.getCode().size() - 1).getRow() - 1,
					snake.getCode().get(snake.getCode().size() - 1).getCol() - 1));
		} else if ((snake.getTesta().getRow() == melaBlu.getRow() && snake.getTesta().getCol() == melaBlu.getCol()
				&& melaBlu.isSpawned())) {
			//System.out.println("prendo melaBlu");
			snake.segnaPunto();
			snake.segnaPunto();
			labelPunteggio.setText(snake.getPunteggio().toString());
			melaBlu.setCol(new Random().nextInt(24));
			melaBlu.setRow(new Random().nextInt(24));
			snake.getCode().add(new Coda(snake.getCode().get(snake.getCode().size() - 1).getRow() - 1,
					snake.getCode().get(snake.getCode().size() - 1).getCol() - 1));
			melaBlu.setSpawned(false);
		} else if ((snake.getTesta().getRow() == melaDorata.getRow() && snake.getTesta().getCol() == melaDorata.getCol()
				&& melaDorata.isSpawned())) {
			//System.out.println("prendo melaDorata");
			snake.segnaPunto();
			labelPunteggio.setText(snake.getPunteggio().toString());
			melaDorata.setCol(new Random().nextInt(24));
			melaDorata.setRow(new Random().nextInt(24));
			snake.getCode().remove(snake.getCode().size() - 1);
			melaDorata.setSpawned(false);
		}
		if (snake.getPunteggio() % 10 == 0) {
			melaDorata.setSpawned(true);
		}
		if (snake.getPunteggio() % 5 == 0) {
			melaBlu.setSpawned(true);
		}
	}

	public void registraPunteggio(Integer punteggioOttenuto) {

		GestoreScene.getPrimaryStage().setScene(GestoreScene.getScenaScoreboard());
		GestoreScene.getPrimaryStage().sizeToScene();


		String result = JOptionPane.showInputDialog(null, "Inserisci il tuo nome");

		if (result.equals("")) {
			result = "Anonimo";
		}
		
		GestoreScene.getHighScoreController().setScore(new HighscoreEntry(result, String.valueOf(punteggioOttenuto)));

		if (GestoreScene.scoreboard.isEmpty()) {
			// ...
		}
	}

	public void verificaAutoCollisione() {
		for (Coda c : snake.getCode()) {
			if (c.getRow() == snake.getTesta().getRow() && c.getCol() == snake.getTesta().getCol()) {
				tm.stop();
				registraPunteggio(snake.getPunteggio());
				snake = new Snake();
				melaRossa = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_ROSSO);
				melaBlu = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_BLU);
				melaDorata = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_DORATO);
				labelPunteggio.setText("0");
				return;
			}
		}
	}

}
