package model;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("casella")
public class Casella {


	@Param(0)
	private int row;
	@Param(1)
	private int column;
	private float posX;
	private float posY;
	private int passo;
	
	public Casella(int r,int c){
		this.row=r;
		this.column=c;
		this.passo=40;
		this.posX=c*passo;
		this.posY=r*passo;
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
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
