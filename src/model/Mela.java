package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.scene.image.Image;

@Id("mela")
public class Mela extends Casella {
	@Param(0)
	private int riga;
	@Param(1)
	private int col;
	private Image image;
	
	public Mela(int row,int column) {
		super(row,column);
		riga=row;
		col=column;
		image=new Image("assets/mela.png");
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
