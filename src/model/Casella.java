package model;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("casella")
public class Casella {


	@Param(0)
	private int row;
	@Param(1)
	private int column;

	
	public Casella(int r,int c,int v){
		this.row=r;
		this.column=c;
	}
	
	public Casella() {
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
