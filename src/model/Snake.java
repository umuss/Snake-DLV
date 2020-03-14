package model;

import java.util.ArrayList;

public class Snake {
	private Testa testa;
	private ArrayList<Coda> code;
	private Integer punteggio;
	
	public Snake() {
		//matrice 32x32
		testa = new Testa(22,1);
		code = new ArrayList<Coda>();
		code.add(new Coda(21,1));
		code.add(new Coda(20,1));
		punteggio = 0;
	
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
	
	public void segnaPunto() {
		punteggio++;
	}
	
	public Integer getPunteggio() {
		return punteggio;
	}
}
