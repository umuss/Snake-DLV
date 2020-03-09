package ai;

import it.unical.mat.embasp.base.Callback;
import it.unical.mat.embasp.base.Output;

public class MyCallback implements Callback {
	private int[][] matrixGame;
	private int dim;
	public MyCallback(int dim,int[][] matrix) {
		matrixGame=matrix;
		this.dim=dim;
	}
	@Override
	public void callback(Output arg0) {
		
	}

}
