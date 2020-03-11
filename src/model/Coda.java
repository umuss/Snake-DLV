package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.scene.image.Image;

@Id("testa")
public class Coda extends Casella {
	@Param(0)
	private int riga;
	@Param(1)
	private int col;
	private Image image;
	private Direction direction;

	public Coda(int row, int column) {
		super(row, column);
		riga = row;
		col = column;
		image = new Image("file:assets/tailLeft.png");
		this.direction = Direction.RIGHT;
	}

	public int getRiga() {
		return riga;
	}

	public void setRiga(int riga) {
		this.riga = riga;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
