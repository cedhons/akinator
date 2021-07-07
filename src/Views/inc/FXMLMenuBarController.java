package Views.inc;

import Presentation.Interfaces.MenuBarPresentationModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class FXMLMenuBarController {
	
	private MenuBarPresentationModel presentation;
	
	public void setPresentation(MenuBarPresentationModel presentation) {
		this.presentation = presentation;
	}
	
    @FXML
    void backToMenu(ActionEvent event) {
    	presentation.backToMenu();
    }

    @FXML
    void createBank(ActionEvent event) {
    	presentation.createDataBank();
    }

    @FXML
    void exit(ActionEvent event) {
    	Platform.exit();
        System.exit(0);
    }

}
