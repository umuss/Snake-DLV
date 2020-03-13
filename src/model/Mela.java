package model;

import it.unical.mat.embasp.languages.Id;
import javafx.scene.image.Image;

@Id("mela")
public class Mela extends Casella {
	private Image image;

	public Mela(int row, int column) {
		super(row, column);

		image = new Image("file:assets/mela.png");
	}

	public Mela() {
		super();
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
