package model;

import java.util.ArrayList;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.scene.image.Image;

@Id("testa")
public class Testa extends Casella {

	private ArrayList<Image> images;
	private Image imageCorrente;
	private Direction direction;

	public Testa(int row, int column) {
		super(row, column);
		images = new ArrayList<Image>();
		
		// da sostituire con le bitmap ruotate
		
		images.add(new Image("file:assets/snakeTransparent.png"));
		images.add(new Image("file:assets/snakeTransparent.png"));
		images.add(new Image("file:assets/snakeTransparent.png"));
		images.add(new Image("file:assets/snakeTransparent.png"));
		imageCorrente = new Image("file:assets/snakeTransparent.png");
		this.direction = Direction.RIGHT;
	}

	public Testa() {
		super();
	}

	

	public Image getImageCorrente() {
		return imageCorrente;
	}

//
//	public void setImage(Image image) {
//		this.image = image;
//	}
	public void setDirection(Direction dir) {
		direction = dir;
		if (dir == Direction.DOWN)
			imageCorrente = images.get(3);
		else if (dir == Direction.UP)
			imageCorrente = images.get(1);
		else if (dir == Direction.LEFT)
			imageCorrente = images.get(2);
		else
			imageCorrente = images.get(0);

	}

	public Direction getDirection() {
		return direction;
	}

}
