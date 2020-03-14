package view;

import java.util.ArrayList;
import java.util.Random;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
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
import model.Testa;

// Matrice: dimensione 32x32

public class GameController {

	@FXML
	private Canvas mainCanvas;
	private double frame = 5;
	boolean giaDisegnata = false;
	Snake snake = new Snake();
	Mela mela = new Mela(1, 1);
	boolean hoDisegnato = false;
	private Handler handler = null;

	@FXML
	private Label labelPunteggio;

	public void drawSnake() {
		AnimationTimer tm = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (frame >= 50) {
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
//	public boolean compreso() {
//		if ((snake.getTesta().getCol() >= 0 && snake.getTesta().getCol() <= 31)
//				&& (snake.getTesta().getRow() >= 0 && snake.getTesta().getRow() <= 31))
//			return true;
//		return false;
//	}

	public boolean isValid(InFinalPath step) {
		int rowTesta = snake.getTesta().getRow();
		int colTesta = snake.getTesta().getCol();
		int rowStep = step.getRow();
		int colStep = step.getCol();

		boolean cond1 = ((rowStep == rowTesta - 1 || rowStep == rowTesta + 1) && colStep == colTesta);
		boolean cond2 = ((colStep == colTesta - 1 || colStep == colTesta + 1) && rowStep == rowTesta);
		boolean toroidalityRow = ((colStep == colTesta)
				&& ((rowStep == 23 && rowTesta == 0) || (rowStep == 0 && rowTesta == 23)));
		boolean toroidalityCol = ((rowStep == rowTesta)
				&& ((colStep == 23 && colTesta == 0) || (colStep == 0 && colTesta == 23)));

		return cond1 || cond2 || toroidalityCol || toroidalityRow;

	}

	public void verificaProssimaCella(Direction dir) {

		ArrayList<Pair<Integer, Integer>> posizioniVecchie = new ArrayList<>();
		try {
			ASPMapper.getInstance().registerClass(InFinalPath.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (System.getProperty("os.name").contains("Linux")) {
			handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));
		} else
			handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2.exe"));
		handler.addOption(new OptionDescriptor("--filter=inFinalPath/2 "));
		InputProgram facts = new ASPInputProgram();

		try {
			for (int i = 0; i < 24; i++)
				for (int j = 0; j < 24; j++)
					facts.addObjectInput(new Casella(i, j));
			facts.addObjectInput(new Mela(mela.getRow(), mela.getCol()));
			facts.addObjectInput(new Testa(snake.getTesta().getRow(), snake.getTesta().getCol()));
			for (Coda c : snake.getCode()) {
				facts.addObjectInput(new Coda(c.getRow(), c.getCol()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		handler.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath("encodings/percorso");
		handler.addProgram(encoding);

		Output o = handler.startSync();
		AnswerSets answers = (AnswerSets) o;
		boolean trovatoCasella = false;
		InFinalPath nextMove = new InFinalPath();
		for (AnswerSet a : answers.getAnswersets()) {
			if (trovatoCasella)
				break;
			try {
				for (Object obj : a.getAtoms()) {
					if (isValid((InFinalPath) obj)) {
						nextMove = (InFinalPath) obj;
						trovatoCasella = true;
						System.out.println("Prendo come finalPath " + nextMove.getRow() + "," + nextMove.getCol());
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			mela.setCol(new Random().nextInt(24));
			mela.setRow(new Random().nextInt(24));
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
