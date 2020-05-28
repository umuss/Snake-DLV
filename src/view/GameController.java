package view;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

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
import model.GestoreScene;
import model.InFinalPath;
import model.Mela;
import model.PosizioneCasella;
import model.PosizioneMela;
import model.Snake;

// Matrice: dimensione 32x32

public class GameController {

	// TODO 1. Aggiustare fxml menu
	// TODO 2. Aggiustare fxml highscore
	// TODO 3. Aggiungere rimanenti handler

	
	
	
	@FXML
	private Canvas mainCanvas;
	private double frame = 5;
	boolean giaDisegnata = false;
	Snake snake = new Snake();
	ArrayList<PosizioneMela> posizioniRaggiungibili = new ArrayList<>();
	ArrayList<Casella> caselleAvvelenate = new ArrayList<>();

	Mela mela = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_ROSSO);
	Mela melaBlu = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_BLU);
	Mela melaDorata = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_DORATO);

	boolean hoDisegnato = false;
	private Handler handlerPath = null;
	private Handler handlerApple = null;
	private Handler handlerCasellaAvvelenata = null;
	private AnimationTimer tm = null;

	@FXML
	private Label labelPunteggio;

	@FXML
	public void interrompiGioco() {
		tm.stop();
		snake = new Snake();
		mela = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_ROSSO);
		melaBlu = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_BLU);
		melaDorata = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_DORATO);
		labelPunteggio.setText("0");
		GestoreScene.getPrimaryStage().setScene(GestoreScene.getScenaMenu());

	}
	
	public void registraPunteggio(Integer punteggioOttenuto) {

		GestoreScene.getPrimaryStage().setScene(GestoreScene.getScenaScoreboard());
		// stageHighScore.setResizable(false); va in loop per qualche assurdo motivo
		// stageHighScore.show();

//			TextInputDialog dialog = new TextInputDialog("Inserisci il tuo nome");
//			dialog.setTitle("Inserisci il nome");
//			dialog.setHeaderText("punteggio");
//			dialog.setContentText("ecc ecc ecc");
//
//			// Traditional way to get the response value.
//			Optional<String> result = Platform.runLater(dialog::showAndWait);
//			if (result.isPresent()) {
//				//System.out.println("Your name: " + result.get());
//			}

		String result = JOptionPane.showInputDialog(null, "Inserisci il tuo nome");

		if (result.equals("")) {
			result = "Anonimo";
		}

		GestoreScene.getHighScoreController().setScore(new HighscoreEntry(result, String.valueOf(punteggioOttenuto)));


	}

	public void drawSnake() {
		caselleAvvelenate.add(new Casella(0, 6, Casella.TIPO_AVVELENATO));
		caselleAvvelenate.add(new Casella(9, 8, Casella.TIPO_AVVELENATO));
		caselleAvvelenate.add(new Casella(10, 20, Casella.TIPO_AVVELENATO));
		caselleAvvelenate.add(new Casella(13, 1, Casella.TIPO_AVVELENATO));
		caselleAvvelenate.add(new Casella(22, 18, Casella.TIPO_AVVELENATO));

		tm = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (frame >= 5) {
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
					// //System.out.println((mela.getImage()));
					mainCanvas.getGraphicsContext2D().drawImage(mela.getImage(), mela.getPosX(), mela.getPosY());
					if (melaBlu.isSpawned())
						mainCanvas.getGraphicsContext2D().drawImage(melaBlu.getImage(), melaBlu.getPosX(),
								melaBlu.getPosY());
					if (melaDorata.isSpawned()) {
						mainCanvas.getGraphicsContext2D().drawImage(melaDorata.getImage(), melaDorata.getPosX(),
								melaDorata.getPosY());
					}

					for (Coda c : snake.getCode()) {
						mainCanvas.getGraphicsContext2D().drawImage(c.getImage(), c.getPosX(), c.getPosY(), 25, 25);
					}

					for (Casella c : caselleAvvelenate) {
						if (c.isSpawned())
							mainCanvas.getGraphicsContext2D().drawImage(c.getImage(), c.getPosX(), c.getPosY(), 25, 25);
					}

					verificaCollisioni();
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
//					// //System.out.println("DOWN");
//					snake.getTesta().setDirection(Direction.DOWN);
//					hoDisegnato = false;
//				} else if (event.getCode() == KeyCode.RIGHT && hoDisegnato
//						&& snake.getTesta().getDirection() != Direction.LEFT && compreso()) {
//					snake.getTesta().setDirection(Direction.RIGHT);
//					// //System.out.println("right");
//					hoDisegnato = false;
//				} else if (event.getCode() == KeyCode.LEFT && hoDisegnato
//						&& snake.getTesta().getDirection() != Direction.RIGHT && compreso()) {
//					snake.getTesta().setDirection(Direction.LEFT);
//					// //System.out.println("left");
//					hoDisegnato = false;
//				} else if (event.getCode() == KeyCode.UP && hoDisegnato
//						&& snake.getTesta().getDirection() != Direction.DOWN && compreso()) {
//					snake.getTesta().setDirection(Direction.UP);
//					// //System.out.println("up");
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
		// right
		if (rowTesta == rowStep && colStep == colTesta + 1)
			return true;
		// left
		if (rowTesta == rowStep && colStep == colTesta - 1)
			return true;
		// down
		if (colTesta == colStep && rowStep == rowTesta + 1)
			return true;
		// up
		if (colTesta == colStep && rowStep == rowTesta - 1)
			return true;
		// right toroidal
		if (rowTesta == rowStep && (colStep == 0 && colTesta == 23))
			return true;
		// left toroidal
		if (rowTesta == rowStep && (colStep == 23 && colTesta == 0))
			return true;
		// down toroidal
		if (colTesta == colStep && (rowStep == 0 && rowTesta == 23))
			return true;
		// up toroidal
		if (colTesta == colStep && (rowStep == 23 && rowTesta == 0))
			return true;
		return false;
//		boolean cond1 = ((rowStep == rowTesta - 1 || rowStep == rowTesta + 1) && colStep == colTesta);
//		boolean cond2 = ((colStep == colTesta - 1 || colStep == colTesta + 1) && rowStep == rowTesta);
//		boolean toroidalityRow = ((colStep == colTesta)
//				&& ((rowStep == 23 && rowTesta == 0) || (rowStep == 0 && rowTesta == 23)));
//		boolean toroidalityCol = ((rowStep == rowTesta)
//				&& ((colStep == 23 && colTesta == 0) || (colStep == 0 && colTesta == 23)));
//
//		return cond1 || cond2 || toroidalityCol || toroidalityRow;

	}

	public void verificaProssimaCella(Direction dir) {

		ArrayList<Pair<Integer, Integer>> posizioniVecchie = new ArrayList<>();
		try {
			ASPMapper.getInstance().registerClass(InFinalPath.class);
			ASPMapper.getInstance().registerClass(PosizioneMela.class);
			ASPMapper.getInstance().registerClass(PosizioneCasella.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (System.getProperty("os.name").contains("Linux")) {
			handlerPath = new DesktopHandler(new DLV2DesktopService("lib/dlv2_5"));
			handlerApple = new DesktopHandler(new DLV2DesktopService("lib/dlv2_5"));
			handlerCasellaAvvelenata = new DesktopHandler(new DLV2DesktopService("lib/dlv2_5"));

		} else {
			handlerPath = new DesktopHandler(new DLV2DesktopService("lib/dlv2_old.exe"));
			handlerApple = new DesktopHandler(new DLV2DesktopService("lib/dlv2_old.exe"));
			handlerCasellaAvvelenata = new DesktopHandler(new DLV2DesktopService("lib/dlv2_old.exe"));

		}
		// handler.addOption(new OptionDescriptor("--filter=inFinalPath/2 "));
		InputProgram facts = new ASPInputProgram();

		try {

			for (int i = 0; i < 24; i++) {
				for (int j = 0; j < 24; j++) {
					Casella c = new Casella(i, j, Casella.TIPO_NORMALE);
					if (caselleAvvelenate.contains(c))
						c.setType(Casella.TIPO_AVVELENATO);
					facts.addObjectInput(c);
				}
			}
			facts.addObjectInput(mela);
			if (melaBlu.isSpawned())
				facts.addObjectInput(melaBlu);
			if (melaDorata.isSpawned())
				facts.addObjectInput(melaDorata);

			facts.addObjectInput(snake.getTesta());
			for (Coda c : snake.getCode()) {
				facts.addObjectInput(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		handlerPath.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath("encodings/percorso");
		handlerPath.addProgram(encoding);

		Output o = handlerPath.startSync();
		// //System.out.println(o.getOutput());
		AnswerSets answers = (AnswerSets) o;
		boolean trovatoCasella = false;
		InFinalPath nextMove = new InFinalPath();
		if (answers.getAnswersets().size() == 0) {
			//System.out.println("answer set 0: HAI PERSO (calcolaProssimaCella)");
			tm.stop();
			registraPunteggio(snake.getPunteggio());
			snake = new Snake();
			mela = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_ROSSO);
			melaBlu = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_BLU);
			melaDorata = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_DORATO);
			labelPunteggio.setText("0");
			return;

			// System.exit(0);
		}

		for (AnswerSet a : answers.getAnswersets()) {
			// posizioniRaggiungibili.clear();
			if (trovatoCasella)
				break;
			try {
				for (Object obj : a.getAtoms()) {
					if (obj instanceof InFinalPath) {
						if (isValid((InFinalPath) obj)) {
							nextMove = (InFinalPath) obj;
							trovatoCasella = true;
							// //System.out.println("Prendo come finalPath " + nextMove.getRow() + "," +
							// nextMove.getCol());
							break;
						}
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
//				// //System.out.println("STO SFORANDO");
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

	public void calcolaPosizioneMelaDLV() {
		InputProgram facts = new ASPInputProgram();
		posizioniRaggiungibili.clear();
		try {
			for (int i = 0; i < 24; i++) {
				for (int j = 0; j < 24; j++) {

					Casella c = new Casella(i, j, Casella.TIPO_NORMALE);

					if (caselleAvvelenate.contains(c))
						c.setType(Casella.TIPO_AVVELENATO);

					facts.addObjectInput(c);
					if (i == mela.getRow() && j == mela.getCol())
						continue;
					if (i == melaDorata.getRow() && j == melaDorata.getCol() && melaDorata.isSpawned())
						continue;
					if (i == melaBlu.getRow() && j == melaBlu.getCol() && melaBlu.isSpawned())
						continue;
				}
			}
			facts.addObjectInput(snake.getTesta());
			for (Coda c : snake.getCode()) {
				facts.addObjectInput(c);
			}
			// //System.out.println(snake.getCode().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		handlerApple.addProgram(facts);
		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath("encodings/posizioneMela");
		handlerApple.addProgram(encoding);
		handlerApple.addOption(new OptionDescriptor("--filter=posizioneMela/2 "));
		// handlerApple.addOption(new OptionDescriptor("-n 0 "));
		Output o = handlerApple.startSync();
		AnswerSets answers = (AnswerSets) o;
		if (answers.getAnswersets().size() == 0) {
			//System.out.println("HAI PERSO");
			registraPunteggio(snake.getPunteggio());
			tm.stop();
			snake = new Snake();
			mela = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_ROSSO);
			melaBlu = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_BLU);
			melaDorata = new Mela(new Random().nextInt(24), new Random().nextInt(24), Mela.TIPO_DORATO);
			labelPunteggio.setText("0");
			return;
			// System.exit(0);
		}
		// //System.out.println(answers.getAnswersets().size());
		for (AnswerSet a : answers.getAnswersets()) {
			try {
				// //System.out.println(answers.getAnswerSetsString());
				for (Object obj : a.getAtoms()) {
					if (obj instanceof PosizioneMela) {
						posizioniRaggiungibili.add((PosizioneMela) obj);
						// //System.out.println("CIAO");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// //System.out.println(posizioniRaggiungibili.size());
		// //System.out.println(mela.getRow()+" "+mela.getCol());
		handlerApple.removeAll();
	}

	public void verificaCollisioni() {

		// Verifico se mi trovo su una casella avvelenata, in caso tolgo code

		// Verifico se ho preso una delle tre mele

		if ((snake.getTesta().getRow() == mela.getRow() && snake.getTesta().getCol() == mela.getCol())) {
			calcolaPosizioneMelaDLV();
			Pair<Integer, Integer> coordinata = getValidCoordinates();
			mela.setRow(coordinata.getKey());
			mela.setCol(coordinata.getValue());
			snake.segnaPunto();
			snake.getCode().add(new Coda(snake.getCode().get(snake.getCode().size() - 1).getRow() - 1,
					snake.getCode().get(snake.getCode().size() - 1).getCol() - 1));
			verificaSpawnMeleBonus();
			labelPunteggio.setText(snake.getPunteggio().toString());
		} else if ((snake.getTesta().getRow() == melaDorata.getRow() && snake.getTesta().getCol() == melaDorata.getCol()
				&& melaDorata.isSpawned())) {
			melaDorata.setSpawned(false);
			snake.segnaPunto();
			labelPunteggio.setText(snake.getPunteggio().toString());
			snake.getCode().remove(snake.getCode().size() - 1);
			verificaSpawnMeleBonus();

		} else if ((snake.getTesta().getRow() == melaBlu.getRow() && snake.getTesta().getCol() == melaBlu.getCol())) {
			snake.segnaPunto();
			labelPunteggio.setText(snake.getPunteggio().toString());
			melaBlu.setSpawned(false);
			snake.getCode().add(new Coda(snake.getCode().get(snake.getCode().size() - 1).getRow() - 1,
					snake.getCode().get(snake.getCode().size() - 1).getCol() - 1));
			verificaSpawnMeleBonus();
		} else {
			for (Casella c : caselleAvvelenate) {
				// //System.out.println("RIGA NERA:-->"+c.getRow()+"COLONNA NERA--->"+c.getCol());
				if (c.getRow() == snake.getTesta().getRow() && c.getCol() == snake.getTesta().getCol()
						&& c.isSpawned()) {
					c.setSpawned(false);
					snake.getCode().add(new Coda(snake.getCode().get(snake.getCode().size() - 1).getRow() - 1,
							snake.getCode().get(snake.getCode().size() - 1).getCol() - 1));
					// snake.getCode().add(new Coda(snake.getCode().get(snake.getCode().size() -
					// 1).getRow() - 1,
					// snake.getCode().get(snake.getCode().size() - 1).getCol() - 1));
				}
			}
		}
	}

	public void verificaSpawnMeleBonus() {
		if (snake.getPunteggio() % 10 == 0) {
			int numCaselleAvvelenateNotSpawned = 0;
			// //System.out.println("SONO ENTRATO");
			calcolaPosizioneMelaDLV();
			Casella casellaAvvelenataNuova = new Casella(0, 0, Casella.TIPO_AVVELENATO);
			casellaAvvelenataNuova.setSpawned(false);
			caselleAvvelenate.add(casellaAvvelenataNuova);
			Pair<Integer, Integer> coordinataDorata = getValidCoordinates();
			// //System.out.println(coordinataDorata.getKey());
			// //System.out.println(coordinataDorata.getValue());
			melaDorata.setRow(coordinataDorata.getKey());
			melaDorata.setCol(coordinataDorata.getValue());
			melaDorata.setSpawned(true);

			// e genero le caselle avvelenate anche
			InputProgram facts = new ASPInputProgram();
			try {
				for (int i = 0; i < 24; i++) {
					for (int j = 0; j < 24; j++) {
						Casella c = dammiCasellaAvvelenata(i, j);
						if (c == null)
							facts.addObjectInput(new Casella(i, j, Casella.TIPO_NORMALE));
						else {
							facts.addObjectInput(c);
							if (!c.isSpawned())
								numCaselleAvvelenateNotSpawned++;
						}

						// //System.out.println("AGGIUNGO");

					}
				}
				//System.out.println(numCaselleAvvelenateNotSpawned);
				facts.addObjectInput(mela);
				if (melaBlu.isSpawned())
					facts.addObjectInput(melaBlu);
				if (melaDorata.isSpawned())
					facts.addObjectInput(melaDorata);

				facts.addObjectInput(snake.getTesta());
				for (Coda c : snake.getCode()) {
					facts.addObjectInput(c);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			handlerCasellaAvvelenata.addProgram(facts);
			InputProgram encoding = new ASPInputProgram();
			encoding.addFilesPath("encodings/posizioneCasellaAvvelenata");
			handlerCasellaAvvelenata.addProgram(encoding);

			Output o = handlerCasellaAvvelenata.startSync();
			AnswerSets answers = (AnswerSets) o;
			boolean esci = false;
			// Prendo le prime due caselle?
			for (AnswerSet a : answers.getAnswersets()) {
				if (esci)
					break;
				try {
					for (Object obj : a.getAtoms()) {
						if (numCaselleAvvelenateNotSpawned == 0) {
							esci = true;
							break;
						}
						if (obj instanceof PosizioneCasella) {
							PosizioneCasella pos = (PosizioneCasella) obj;
							for (Casella c : caselleAvvelenate) {
								if (!c.isSpawned()) {
									numCaselleAvvelenateNotSpawned--;
									c.setRow(pos.getRow());
									c.setCol(pos.getCol());
									c.setSpawned(true);
									break;
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		if (snake.getPunteggio() % 5 == 0) {
			// //System.out.println("SONO ENTRATO");
			calcolaPosizioneMelaDLV();
			Pair<Integer, Integer> coordinataDorata = getValidCoordinates();
			// //System.out.println(coordinataDorata.getKey());
			// //System.out.println(coordinataDorata.getValue());
			melaBlu.setRow(coordinataDorata.getKey());
			melaBlu.setCol(coordinataDorata.getValue());
			melaBlu.setSpawned(true);
		}
	}

	public Casella dammiCasellaAvvelenata(int i, int j) {
		for (Casella c : caselleAvvelenate) {
			if (c.getRow() == i && c.getCol() == j) {
				return c;
			}
		}
		return null;
	}

	public void verificaAutoCollisione() {
		for (Coda c : snake.getCode()) {
			if (c.getRow() == snake.getTesta().getRow() && c.getCol() == snake.getTesta().getCol()) {
				// //System.out.println("AUTOCOLLISIONE");
			}
		}
	}

	public Pair<Integer, Integer> getValidCoordinates() {
		int best = Integer.MAX_VALUE;
		int cont = 0;
		PosizioneMela raggiunge = null;
		PosizioneMela bestRaggiunge = null;
		// //System.out.println("size posRaggiungibili: " +
		// posizioniRaggiungibili.size());
		for (int i = 0; i < posizioniRaggiungibili.size(); i++) {
			cont = 0;
			raggiunge = posizioniRaggiungibili.get(i);
			for (Coda c : snake.getCode()) {
				if (raggiunge.getRow() == c.getRow()
						&& (raggiunge.getCol() == c.getCol() - 1 || raggiunge.getCol() == c.getCol() - 2))
					cont++;
				if (raggiunge.getRow() == c.getRow()
						&& (raggiunge.getCol() == c.getCol() + 1 || raggiunge.getCol() == c.getCol() + 2))
					cont++;
				if (raggiunge.getCol() == c.getCol()
						&& (raggiunge.getRow() == c.getRow() + 1 || raggiunge.getRow() == c.getRow() + 2))
					cont++;
				if (raggiunge.getCol() == c.getCol()
						&& (raggiunge.getRow() == c.getRow() - 1 || raggiunge.getRow() == c.getRow() - 2))
					cont++;
				// soto a destra
				if (raggiunge.getRow() == c.getRow() + 1 && raggiunge.getCol() == c.getCol() + 1)
					cont++;
				// sopra a sinistra
				if (raggiunge.getRow() == c.getRow() - 1 && raggiunge.getCol() == c.getCol() - 1)
					cont++;
				// sopra a destra
				if (raggiunge.getRow() == c.getRow() - 1 && raggiunge.getCol() == c.getCol() + 1)
					cont++;
				// sotto a sinistra
				if (raggiunge.getRow() == c.getRow() + 1 && raggiunge.getCol() == c.getCol() - 1)
					cont++;
			}
			if (raggiunge.getCol() == snake.getTesta().getCol() && (raggiunge.getRow() == snake.getTesta().getRow() - 1
					|| raggiunge.getRow() == snake.getTesta().getRow() - 2))
				cont++;
			if (raggiunge.getRow() == snake.getTesta().getRow() && (raggiunge.getCol() == snake.getTesta().getCol() - 1
					|| raggiunge.getCol() == snake.getTesta().getCol() - 2))
				cont++;
			if (cont < best) {
				best = cont;
				bestRaggiunge = raggiunge;
			}
		}

		return new Pair<Integer, Integer>(bestRaggiunge.getRow(), bestRaggiunge.getCol());
	}

}
