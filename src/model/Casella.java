package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import javafx.scene.image.Image;

@Id("casella")
public class Casella {

	@Param(0)
	private int row;
	@Param(1)
	private int col;
	@Param(2)
	private int type;
	
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
	
	
	private Image image;
	
	public static int TIPO_NORMALE = 0;
	public static int TIPO_AVVELENATO = 1;
	
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	
	public Casella() {
		// TODO Auto-generated constructor stub
	}
	
	public Image getImage() {
		return image;
	}
	
	public Casella(int r, int c, int type) {
		this.row = r;
		this.col = c;
		this.passo = 25;
		
		if (type == TIPO_NORMALE) {
			image = null;
		}
		else {
			image = new Image("file:assets/casella_avvelenata.png");
		}
		
		this.type = type;
		// passo 15: matrice 40x40 con risoluzione 600x600 (600/15 = 40) (troppo
		// piccolo!!)
		// passo 25: matrice 32x32, siccome abbiamo risoluzione di 800x800 ed 800/25 =
		// 32.
		// passo 25 (con risoluzione 600x600): matrice 24x24.
		this.posX = c * passo;
		this.posY = r * passo;
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

	@Override
	public boolean equals(Object obj) {
		Casella c = null;
		if (obj instanceof Casella) {
			c = (Casella) obj;
		}
		return c.getPosX() == this.posX && c.getPosY() == this.posY;
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
}
