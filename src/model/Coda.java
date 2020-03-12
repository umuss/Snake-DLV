package model;

import it.unical.mat.embasp.languages.Id;
import javafx.scene.image.Image;

@Id("testa")
public class Coda extends Casella {
//	@Param(0)
//	private int riga;
//	@Param(1)
//	private int col;
	private Image image;
	private Direction direction;

	public Coda(int row, int column) {
		super(row, column);
		image = new Image("file:assets/tail.png");
		this.direction = Direction.RIGHT;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}


}
