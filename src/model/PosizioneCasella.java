package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("posizioneCasella")
public class PosizioneCasella {

	@Param(0)
	private int row;
	@Param(1)
	private int col;

	public PosizioneCasella() {
		// TODO Auto-generated constructor stub
	}
	
	public PosizioneCasella(int r, int c) {
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
