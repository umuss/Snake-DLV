package view;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import model.Testa;

public class SampleController {

	@FXML
	private Canvas mainCanvas;
	private int frame=5;
	Testa t = new Testa(4, 4);

	public void drawHead() {
		AnimationTimer tm=new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(frame>=2) {
					mainCanvas.getGraphicsContext2D().clearRect(0,0,mainCanvas.getWidth(),mainCanvas.getHeight());
					mainCanvas.getGraphicsContext2D().drawImage(t.getImage(), t.getColumn(), t.getRiga());
					t.setColumn(t.getColumn() + 1);
					frame=0;
				}
				frame+=1;
				
				
				
				
			}
		};
		tm.start();	
	}

}
