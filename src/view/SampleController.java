package view;

import java.util.ArrayList;
import java.util.Random;

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

// Matrice: dimensione 32x32

public class SampleController {

	@FXML
	private Canvas mainCanvas;
	private double frame = 5;
	boolean giaDisegnata = false;
	Snake snake = new Snake();
	Mela mela = new Mela(new Random().nextInt(32), new Random().nextInt(32));
	boolean hoDisegnato = false;

	@FXML
	private Label labelPunteggio;

	public void drawSnake() {
		AnimationTimer tm = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (frame >= 10) {
					hoDisegnato = true;
					verificaCollisioneMela();
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
					mainCanvas.getGraphicsContext2D().drawImage(mela.getImage(), mela.getPosX(), mela.getPosY());
					for (Coda c : snake.getCode()) {
						mainCanvas.getGraphicsContext2D().drawImage(c.getImage(), c.getPosX(), c.getPosY(), 25, 25);
					}
				}
				frame += 1;
			}
		};
		tm.start();
		GestoreScene.getScenaCorrente().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.DOWN && hoDisegnato && snake.getTesta().getDirection() != Direction.UP
						&& compreso()) {
					// System.out.println("DOWN");
					snake.getTesta().setDirection(Direction.DOWN);
					hoDisegnato = false;
				} else if (event.getCode() == KeyCode.RIGHT && hoDisegnato
						&& snake.getTesta().getDirection() != Direction.LEFT && compreso()) {
					snake.getTesta().setDirection(Direction.RIGHT);
					// System.out.println("right");
					hoDisegnato = false;
				} else if (event.getCode() == KeyCode.LEFT && hoDisegnato
						&& snake.getTesta().getDirection() != Direction.RIGHT && compreso()) {
					snake.getTesta().setDirection(Direction.LEFT);
					// System.out.println("left");
					hoDisegnato = false;
				} else if (event.getCode() == KeyCode.UP && hoDisegnato
						&& snake.getTesta().getDirection() != Direction.DOWN && compreso()) {
					snake.getTesta().setDirection(Direction.UP);
					// System.out.println("up");
					hoDisegnato = false;
				}

			}
		});
	}

	public boolean compreso() {
		if ((snake.getTesta().getCol() >= 0 && snake.getTesta().getCol() <= 31)
				&& (snake.getTesta().getRow() >= 0 && snake.getTesta().getRow() <= 31))
			return true;
		return false;
	}

	public void verificaProssimaCella(Direction dir) {
		ArrayList<Pair<Integer, Integer>> posizioniVecchie = new ArrayList<>();
		if (dir == Direction.RIGHT) {
			if (snake.getTesta().getCol() >= 32) {
				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
				for (Coda c : snake.getCode()) {
					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
				}
				snake.getTesta().setCol(0);
				for (int i = 0; i < snake.getCode().size(); i++) {
					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
				}
				// System.out.println("STO SFORANDO");
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
				snake.getTesta().setRow(31);
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
				snake.getTesta().setCol(31);
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
			if (snake.getTesta().getRow() >= 32) {
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

	public void verificaCollisioneMela() {
		if ((snake.getTesta().getRow() == mela.getRow() && snake.getTesta().getCol() == mela.getCol())) {
			System.out.println("prendo mela");
			snake.segnaPunto();
			labelPunteggio.setText(snake.getPunteggio().toString());
			mela.setCol(new Random().nextInt(32));
			mela.setRow(new Random().nextInt(32));
			snake.getCode().add(new Coda(snake.getCode().get(snake.getCode().size() - 1).getRow() - 1,
					snake.getCode().get(snake.getCode().size() - 1).getCol() - 1));
		}
	}

	public void verificaAutoCollisione() {
		for (Coda c : snake.getCode()) {

			if (c.getRow() == snake.getTesta().getRow() && c.getCol() == snake.getTesta().getCol()) {
				System.out.println("AUTOCOLLISIONE");
			}
		}
	}

}
