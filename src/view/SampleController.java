package view;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.util.Pair;
import model.Casella;
import model.Coda;
import model.Direction;
import model.InFinalPath;
import model.Mela;
import model.Snake;

// Matrice: dimensione 32x32

public class SampleController {

	@FXML
	private Canvas mainCanvas;
	private double frame = 5;
	boolean giaDisegnata = false;
	Snake snake = new Snake();
	Mela mela = new Mela(new Random().nextInt(32), new Random().nextInt(32));
	boolean hoDisegnato = false;
	private Handler handler = null;

	@FXML
	private Label labelPunteggio;

	public void drawSnake() {
		AnimationTimer tm = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (frame >= 10) {
					hoDisegnato = true;

					if (snake.getTesta().getDirection() == Direction.RIGHT) {
						verificaProssimaCella(Direction.RIGHT);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY(), 25, 25);
					}
					if (snake.getTesta().getDirection() == Direction.LEFT) {
						verificaProssimaCella(Direction.LEFT);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY(), 25, 25);
					}
					if (snake.getTesta().getDirection() == Direction.DOWN) {
						verificaProssimaCella(Direction.DOWN);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY(), 25, 25);
					}
					if (snake.getTesta().getDirection() == Direction.UP) {
						verificaProssimaCella(Direction.UP);
						mainCanvas.getGraphicsContext2D().clearRect(0, 0, mainCanvas.getWidth(),
								mainCanvas.getHeight());
						mainCanvas.getGraphicsContext2D().drawImage(snake.getTesta().getImageCorrente(),
								snake.getTesta().getPosX(), snake.getTesta().getPosY(), 25, 25);
					}
					frame = 0;
					mainCanvas.getGraphicsContext2D().drawImage(mela.getImage(), mela.getPosX(), mela.getPosY());
					for (Coda c : snake.getCode()) {
						mainCanvas.getGraphicsContext2D().drawImage(c.getImage(), c.getPosX(), c.getPosY(), 25, 25);
					}
					verificaCollisioneMela();
					verificaAutoCollisione();
				}
				frame += 1;
			}
		};
		tm.start();
//		GestoreScene.getScenaCorrente().setOnKeyPressed(new EventHandler<KeyEvent>() {
//			@Override
//			public void handle(KeyEvent event) {
//
//				if (event.getCode() == KeyCode.DOWN && hoDisegnato && snake.getTesta().getDirection() != Direction.UP
//						&& compreso()) {
//					// System.out.println("DOWN");
//					snake.getTesta().setDirection(Direction.DOWN);
//					hoDisegnato = false;
//				} else if (event.getCode() == KeyCode.RIGHT && hoDisegnato
//						&& snake.getTesta().getDirection() != Direction.LEFT && compreso()) {
//					snake.getTesta().setDirection(Direction.RIGHT);
//					// System.out.println("right");
//					hoDisegnato = false;
//				} else if (event.getCode() == KeyCode.LEFT && hoDisegnato
//						&& snake.getTesta().getDirection() != Direction.RIGHT && compreso()) {
//					snake.getTesta().setDirection(Direction.LEFT);
//					// System.out.println("left");
//					hoDisegnato = false;
//				} else if (event.getCode() == KeyCode.UP && hoDisegnato
//						&& snake.getTesta().getDirection() != Direction.DOWN && compreso()) {
//					snake.getTesta().setDirection(Direction.UP);
//					// System.out.println("up");
//					hoDisegnato = false;
//				}
//
//			}
//		});
	}

	public boolean compreso() {
		if ((snake.getTesta().getCol() >= 0 && snake.getTesta().getCol() <= 31)
				&& (snake.getTesta().getRow() >= 0 && snake.getTesta().getRow() <= 31))
			return true;
		return false;
	}

	public void verificaProssimaCella(Direction dir) {
		ArrayList<Pair<Integer, Integer>> posizioniVecchie = new ArrayList<>();

		handler = new DesktopHandler(new DLV2DesktopService("lib" + File.separator + "dlv2.exe"));
		handler.addOption(new OptionDescriptor("--filter=outFinalPath/2 "));
		InputProgram facts = new ASPInputProgram();
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 32; j++) {
				try {
					facts.addObjectInput(new Casella(i, j));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		try {
			facts.addObjectInput(mela);
			facts.addObjectInput(snake.getTesta());
			for (Coda c : snake.getCode())
				facts.addObjectInput(c);
		} catch (Exception e) {
			e.printStackTrace();
		}

		handler.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath("encodings" + File.separator + "percorso");
		handler.addProgram(encoding);

		Output o = handler.startSync();
		AnswerSets answers = (AnswerSets) o;
		boolean trovatoCasella = false;
		InFinalPath nextMove = new InFinalPath(3, 3);
		for (AnswerSet a : answers.getAnswersets()) {
			System.out.println("Inizio Answer Set");
			try {
				System.out.println("Size Answer Set: " + a.getAtoms().size());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (trovatoCasella) {
				break;
			}
			try {
				for (Object obj : a.getAtoms()) {
					// Ma ci serve una classe FinalPath??
					// if ((obj instanceof InFinalPath)) {
					System.out.println("Istanza di finalPath");
					// System.out.println("Classe Atomo: " + obj.getClass());
					// trovatoCasella = true;
					nextMove = (InFinalPath) obj;
					// System.out.println("riga: " + nextMove.getRow() + " colonna: " +
					// nextMove.getCol());
					// break;
					// }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Finito Answer Set");
		}

		posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
		for (Coda c : snake.getCode()) {
			posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
		}
		snake.getTesta().setRow(nextMove.getRow());
		snake.getTesta().setCol(nextMove.getCol());
		for (int i = 0; i < snake.getCode().size(); i++) {
			snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
			snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
		}

//		if (dir == Direction.RIGHT) {
//			if (snake.getTesta().getCol() >= 32) {
//				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
//				for (Coda c : snake.getCode()) {
//					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
//				}
//				snake.getTesta().setCol(0);
//				for (int i = 0; i < snake.getCode().size(); i++) {
//					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
//					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
//				}
//				// System.out.println("STO SFORANDO");
//				// Caso non di sforamento
//			} else {
//				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
//				for (Coda c : snake.getCode()) {
//					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
//				}
//				snake.getTesta().setCol(snake.getTesta().getCol() + 1);
//				for (int i = 0; i < snake.getCode().size(); i++) {
//					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
//					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
//				}
//			}
//		}
//		if (dir == Direction.UP) {
//			if (snake.getTesta().getRow() <= 0) {
//				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
//				for (Coda c : snake.getCode()) {
//					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
//				}
//				snake.getTesta().setRow(31);
//				for (int i = 0; i < snake.getCode().size(); i++) {
//					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
//					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
//				}
//			} else {
//				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
//				for (Coda c : snake.getCode()) {
//					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
//				}
//				snake.getTesta().setRow(snake.getTesta().getRow() - 1);
//				for (int i = 0; i < snake.getCode().size(); i++) {
//					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
//					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
//				}
//			}
//		}
//		if (dir == Direction.LEFT) {
//			if (snake.getTesta().getCol() <= 0) {
//				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
//				for (Coda c : snake.getCode()) {
//					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
//				}
//				snake.getTesta().setCol(31);
//				for (int i = 0; i < snake.getCode().size(); i++) {
//					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
//					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
//				}
//			} else {
//				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
//				for (Coda c : snake.getCode()) {
//					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
//				}
//				snake.getTesta().setCol(snake.getTesta().getCol() - 1);
//				for (int i = 0; i < snake.getCode().size(); i++) {
//					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
//					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
//				}
//			}
//		}
//		if (dir == Direction.DOWN) {
//			if (snake.getTesta().getRow() >= 32) {
//				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
//				for (Coda c : snake.getCode()) {
//					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
//				}
//				snake.getTesta().setRow(0);
//				for (int i = 0; i < snake.getCode().size(); i++) {
//					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
//					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
//				}
//			} else {
//				posizioniVecchie.add(new Pair<Integer, Integer>(snake.getTesta().getCol(), snake.getTesta().getRow()));
//				for (Coda c : snake.getCode()) {
//					posizioniVecchie.add(new Pair<Integer, Integer>(c.getCol(), c.getRow()));
//				}
//				snake.getTesta().setRow(snake.getTesta().getRow() + 1);
//				for (int i = 0; i < snake.getCode().size(); i++) {
//					snake.getCode().get(i).setCol(posizioniVecchie.get(i).getKey());
//					snake.getCode().get(i).setRow(posizioniVecchie.get(i).getValue());
//				}
//			}
//		}
	}

	public void verificaCollisioneMela() {
		if ((snake.getTesta().getRow() == mela.getRow() && snake.getTesta().getCol() == mela.getCol())) {
			System.out.println("prendo mela");
			snake.segnaPunto();
			labelPunteggio.setText(snake.getPunteggio().toString());
			mela.setCol(new Random().nextInt(32));
			mela.setRow(new Random().nextInt(32));
			snake.getCode().add(new Coda(snake.getCode().get(snake.getCode().size() - 1).getRow() - 1,
					snake.getCode().get(snake.getCode().size() - 1).getCol() - 1));
		}
	}

	public void verificaAutoCollisione() {
		for (Coda c : snake.getCode()) {

			if (c.getRow() == snake.getTesta().getRow() && c.getCol() == snake.getTesta().getCol()) {
				System.out.println("AUTOCOLLISIONE");
			}
		}
	}

}
