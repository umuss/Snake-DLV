package model;

import java.util.ArrayList;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.scene.image.Image;

@Id("testa")
public class Testa {
	
	@Param(0)
	private int row;
	@Param(1)
	private int col;
	private float posX;
	private float posY;
	private ArrayList<Image> images;
	private Image imageCorrente;
	private Direction direction;
	private int passo;

	public Testa(int row, int column) {
		this.row=row;
		this.col=column;
		images = new ArrayList<Image>();
		
		// da sostituire con le bitmap ruotate
		
		images.add(new Image("file:assets/snakeTransparent.png"));
		images.add(new Image("file:assets/snakeTransparent.png"));
		images.add(new Image("file:assets/snakeTransparent.png"));
		images.add(new Image("file:assets/snakeTransparent.png"));
		imageCorrente = new Image("file:assets/snakeTransparent.png");
		this.direction = Direction.RIGHT;
		this.passo=25;
		this.posX=passo*column;
		this.posY=passo*row;
	}

	public Testa() {
		super();
	}

	

	public int getPasso() {
		return passo;
	}

	public void setPasso(int passo) {
		this.passo = passo;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.posY = row * passo;
		this.row = row;

	}

	public int getCol() {
		return col;
	}

	public void incrementaCol() {
		if (col == 31) {
			setCol(0);
		} else {
			col++;
		}
	}

	public void decrementaRow() {
		if (row == 0) {
			setRow(31);
		} else {
			row--;
		}
	}

	public void decrementaCol() {
		if (col == 0) {
			setCol(31);
		} else {
			col--;
		}
	}

	public void setCol(int col) {
		this.posX = col * passo;
		this.col = col;

	}
	public Image getImageCorrente() {
		return imageCorrente;
	}

	public void setImageCorrente(Image imageCorrente) {
		this.imageCorrente = imageCorrente;
	}

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
