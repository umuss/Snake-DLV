package snake;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class SampleController {

    @FXML
    private Canvas mainCanvas;

    public void drawBackground() {

    	Image img = new Image("file:assets/background.png");
		mainCanvas.getGraphicsContext2D().drawImage(img, 0, 0);

    }
    
}
