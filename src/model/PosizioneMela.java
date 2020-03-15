package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("posizioneMela")
public class PosizioneMela {

	@Param(0)
	private int row;
	@Param(1)
	private int col;

	public PosizioneMela() {
		// TODO Auto-generated constructor stub
	}
	
	public PosizioneMela(int r, int c) {
		this.row = r;
		this.col = c;
	}

	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public void setRow(int r) {
		row=r;
	}
	public void setCol(int c) {
		col=c;
	}
	
}
