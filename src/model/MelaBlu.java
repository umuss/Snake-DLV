package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.scene.image.Image;

@Id("melaBlu")
public class MelaBlu {
	@Param(0)
	private int row;
	@Param(1)
	private int col;

	
	
	private Image image;
	private float posX;
	private float posY;
	private int passo;
	private boolean isSpawned;
	
	public static int TIPO_BLU = 1;
	public static int TIPO_ROSSO = 2;
	public static int TIPO_DORATO = 0;


	public MelaBlu(int row, int column) {
		this.row = row;
		this.col = column;
		this.isSpawned=true;
		image = new Image("file:assets/mela_blu.png");

		this.passo = 25;
		this.posX = column * passo;
		this.posY = row * passo;

	}

	public MelaBlu() {

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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setSpawned(boolean b) {
		isSpawned=b;
	}
	public boolean isSpawned() {
		return this.isSpawned;
	}

}