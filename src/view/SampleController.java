package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Coda;
import model.Direction;
import model.GestoreScene;
import model.Mela;
import model.Snake;

public class SampleController {

	@FXML
	private Canvas mainCanvas;
	private double frame = 5;
	boolean giaDisegnata = false;
	Snake snake = new Snake();

	public void drawMela(Mela mela) {

		AnimationTimer tm = new AnimationTimer() {
			@Override
			public void handle(long now) {
				//if (!giaDisegnata) {
					mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
					mainCanvas.getGraphicsContext2D().drawImage(mela.getImage(), mela.getPosX(), mela.getPosY());
					giaDisegnata = true;
				//}
			}
		};
		tm.start();
	}

	public void drawSnake(Mela mela) {
		AnimationTimer tm = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (frame >= 4) {
					if (snake.getTesta().getDirection() == Direction.RIGHT) {
						verificaProssimaCella(Direction.RIGHT);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY());
					}
					if (snake.getTesta().getDirection() == Direction.LEFT) {
						verificaProssimaCella(Direction.LEFT);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY());
					}
					if (snake.getTesta().getDirection() == Direction.DOWN) {
						verificaProssimaCella(Direction.DOWN);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY());
					}
					if (snake.getTesta().getDirection() == Direction.UP) {
						verificaProssimaCella(Direction.UP);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY());
					}
					frame = 0;
					mainCanvas.getGraphicsContext2D().drawImage(mela.getImage(), mela.getPosX(), mela.getPosY());
					mainCanvas.getGraphicsContext2D().drawImage(snake.getCode().get(0).getImage(), snake.getCode().get(0).getPosX(), snake.getCode().get(0).getPosY());
						
				}
				frame += 1;
			}
		};
		tm.start();
		GestoreScene.getScenaCorrente().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.DOWN) {
					System.out.println("DOWN");
					snake.getTesta().setDirection(Direction.DOWN);
				} else if (event.getCode() == KeyCode.RIGHT) {
					snake.getTesta().setDirection(Direction.RIGHT);
					System.out.println("right");
				} else if (event.getCode() == KeyCode.LEFT) {
					snake.getTesta().setDirection(Direction.LEFT);
					System.out.println("left");
				} else if (event.getCode() == KeyCode.UP) {
					snake.getTesta().setDirection(Direction.UP);
					System.out.println("up");
				}

			}
		});
	}

	public void verificaProssimaCella(Direction dir) {
		if (dir == Direction.RIGHT) {
			if (snake.getTesta().getPosX() >= mainCanvas.getWidth() - 30) {
				snake.getTesta().setPosX(-50);
				for (Coda c : snake.getCode()) {
					c.setPosX(-50);
				}
			} else {
				for (Coda c : snake.getCode()) {
					c.setPosX(c.getPosX() + snake.getTesta().getPasso());
				}
				snake.getTesta().setPosX(snake.getTesta().getPosX() + snake.getTesta().getPasso());
			}
		}
		if (dir == Direction.UP) {
			if (snake.getTesta().getPosY() <= -60) {
				snake.getTesta().setPosY((int) mainCanvas.getWidth() - 80);
			} else {
				snake.getTesta().setPosY(snake.getTesta().getPosY() - snake.getTesta().getPasso());
			}
		}
		if (dir == Direction.LEFT) {
			if (snake.getTesta().getPosX() <= -60) {
				snake.getTesta().setPosX((int) mainCanvas.getHeight() - 50);
			} else {
				snake.getTesta().setPosX(snake.getTesta().getPosX() - snake.getTesta().getPasso());
			}
		}
		if (dir == Direction.DOWN) {
			if (snake.getTesta().getPosY() >= mainCanvas.getWidth() - 80) {
				snake.getTesta().setPosY(-80);
			} else {
				snake.getTesta().setPosY(snake.getTesta().getPosY() + snake.getTesta().getPasso());
			}
		}

	}

}
