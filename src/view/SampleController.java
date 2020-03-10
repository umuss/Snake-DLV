package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Direction;
import model.GestoreScene;
import model.Snake;
import model.Testa;

public class SampleController {

	@FXML
	private Canvas mainCanvas;
	private double frame=5;
	Snake snake = new Snake();

	public void drawSnake() {
		AnimationTimer tm=new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(frame>=2) {
					if(snake.getTesta().getDirection()==Direction.RIGHT) {
						verificaProssimaCella(Direction.RIGHT);
						mainCanvas.getGraphicsContext2D().clearRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),snake.getTesta().getCol(),snake.getTesta().getRiga());
					}
					if(snake.getTesta().getDirection()==Direction.LEFT) {
						verificaProssimaCella(Direction.LEFT);
						mainCanvas.getGraphicsContext2D().clearRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),snake.getTesta().getCol(),snake.getTesta().getRiga());
					}
					if(snake.getTesta().getDirection()==Direction.DOWN) {
						verificaProssimaCella(Direction.DOWN);
						mainCanvas.getGraphicsContext2D().clearRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),snake.getTesta().getCol(),snake.getTesta().getRiga());
					}
					if(snake.getTesta().getDirection()==Direction.UP) {
						verificaProssimaCella(Direction.UP);
						mainCanvas.getGraphicsContext2D().clearRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),snake.getTesta().getCol(),snake.getTesta().getRiga());
					}
					frame=0;
				}
				frame+=1;
			}
		};
		tm.start();	
		GestoreScene.getScenaCorrente().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.DOWN) {
					System.out.println("DOWN");
					snake.getTesta().setDirection(Direction.DOWN);
				}
				else if(event.getCode()==KeyCode.RIGHT) {
					snake.getTesta().setDirection(Direction.RIGHT);
					System.out.println("right");
				}
				else if(event.getCode()==KeyCode.LEFT) {
					snake.getTesta().setDirection(Direction.LEFT);
					System.out.println("left");
				}
				else if(event.getCode()==KeyCode.UP) {
					snake.getTesta().setDirection(Direction.UP);
					System.out.println("up");
				}
				
			}
		});
	}
	public void verificaProssimaCella(Direction dir) {
		if(dir==Direction.RIGHT){
			if(snake.getTesta().getCol()>=mainCanvas.getHeight()-70) {
				snake.getTesta().setCol(-50);
			}
			else {
				snake.getTesta().setCol(snake.getTesta().getCol()+1);
			}
		}
		if(dir==Direction.UP){
			if(snake.getTesta().getRiga()<=-60) {
				snake.getTesta().setRiga((int)mainCanvas.getWidth()-260);
			}
			else {
				snake.getTesta().setRiga(snake.getTesta().getRiga()-1);
			}
		}
		if(dir==Direction.LEFT){
			if(snake.getTesta().getCol()<=-60) {
				snake.getTesta().setCol((int)mainCanvas.getHeight()-100);
			}
			else {
				snake.getTesta().setCol(snake.getTesta().getCol()-1);
			}
		}
		if(dir==Direction.DOWN){
			if(snake.getTesta().getRiga()>=mainCanvas.getWidth()-260) {
				snake.getTesta().setRiga(-80);
			}
			else {
				snake.getTesta().setRiga(snake.getTesta().getRiga()+1);
			}
		}
		
	}

}
