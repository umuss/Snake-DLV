package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("casella")
public class Casella {

	@Param(0)
	private int row;
	@Param(1)
	private int col;
	private float posX;
	private float posY;
	private int passo;

	public Casella(int r, int c) {
		this.row = r;
		this.col = c;
		this.passo = 25;
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
		if (this.row - row > 0) {
			this.posY -= passo;
			System.out.println("+=");
		} else {
			this.posY += passo;
		}
		this.row = row;
				
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		if (this.col - col > 0) {
			this.posX -= passo;
			System.out.println("+=");
		} else {
			this.posX += passo;
		}
		this.col = col;

	}
}
