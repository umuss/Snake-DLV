package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("inFinalPath")
public class InFinalPath {
	@Param(0)
	private int row;
	@Param(1)
	private int col;
	public InFinalPath() {
		super();
	}

	public InFinalPath(int row, int col) {
		this.row=row;
		this.col=col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
