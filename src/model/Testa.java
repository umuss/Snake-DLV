package model;

import java.util.ArrayList;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.scene.image.Image;

@Id("testa")
public class Testa extends Casella {
	@Param(0)
	private int riga;
	@Param(1)
	private int col;
	private ArrayList<Image> images;
	private Image imageCorrente;
	private Direction direction;
	
	public Testa(int row,int column) {
		riga=row;
		col=column;
		images=new ArrayList<Image>();
		images.add(new Image("file:assets/snakeRight.png"));
		images.add(new Image("file:assets/snake.png"));
		images.add(new Image("file:assets/snakeLeft.png"));
		images.add(new Image("file:assets/snakeDown.png"));
		imageCorrente=images.get(0);
		this.direction=Direction.RIGHT;
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

	public Image getImageCorrente() {
		return imageCorrente;
	}
//
//	public void setImage(Image image) {
//		this.image = image;
//	}
	public void setDirection(Direction dir) {
		direction=dir;
		if(dir==Direction.DOWN)
			imageCorrente=images.get(3);
		else if(dir==Direction.UP)
			imageCorrente=images.get(1);
		else if(dir==Direction.LEFT)
			imageCorrente=images.get(2);
		else
			imageCorrente=images.get(0);
			
		
	}
	public Direction getDirection() {
		return direction;
	}
	
}
