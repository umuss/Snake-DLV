package model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("raggiunge")
public class Raggiunge {

	@Param(0)
	private int row;
	@Param(1)
	private int col;

	public Raggiunge() {
		// TODO Auto-generated constructor stub
	}
	
	public Raggiunge(int r, int c) {
		this.row = r;
		this.col = c;
	}

	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
}
