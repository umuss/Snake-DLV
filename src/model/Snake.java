package model;

import java.util.ArrayList;

public class Snake {
	private Testa testa;
	private ArrayList<Coda> code;
	public Snake() {
		//matrice 32x32
		testa = new Testa(1,27);
		code = new ArrayList<Coda>();
		code.add(new Coda(1,26));
		code.add(new Coda(1,25));
	
	}
	public Testa getTesta() {
		return testa;
	}
	public void setTesta(Testa testa) {
		this.testa = testa;
	}
	public ArrayList<Coda> getCode() {
		return code;
	}
	public void setCode(ArrayList<Coda> code) {
		this.code = code;
	}
}
