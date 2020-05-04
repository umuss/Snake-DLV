package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.scene.image.Image;

@Id("mela")
public class Mela {
	@Param(0)
	private int row;
	@Param(1)
	private int col;
	@Param(2)
	private int type;

	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	private Image image;
	private float posX;
	private float posY;
	private int passo;
	
	private boolean spawned;
	
	public boolean isSpawned() {
		return spawned;
	}
	
	public void setSpawned(boolean spawned) {
		this.spawned = spawned;
	}
	
	public static int TIPO_BLU = 2;
	public static int TIPO_ROSSO = 3;
	public static int TIPO_DORATO = 4;


	public Mela(int row, int column, int type) {
		this.row = row;
		this.col = column;
		
		this.type = type;
		this.spawned = true;
		
		if (type == TIPO_ROSSO) {
			image = new Image("file:assets/mela.png");			
		}
		if (type == TIPO_BLU) {
			image = new Image("file:assets/mela_blu.png");			
		}
		if (type == TIPO_DORATO) {
			image = new Image("file:assets/mela_dorata.png");			
		}

		
		this.passo = 25;
		this.posX = column * passo;
		this.posY = row * passo;

	}

	public Mela() {

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

}
