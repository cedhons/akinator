package Views;

import java.net.URL;
import java.util.ResourceBundle;

import Presentation.Interfaces.MenuBarPresentationModel;
import Presentation.Interfaces.ResultPresentationModel;
import Presentation.ViewInterfaces.AkinatorResultView;
import Views.inc.FXMLMenuBarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLAkinatorResultController implements Initializable, AkinatorResultView {
	
	private ResultPresentationModel presentation;
	private MenuBarPresentationModel menuBarPresentation;
	
	public FXMLAkinatorResultController(ResultPresentationModel presentation, MenuBarPresentationModel menuBarPresentation) {
		this.presentation = presentation;
		this.menuBarPresentation = menuBarPresentation;
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presentation.setView(this);
		menuBarController.setPresentation(menuBarPresentation);
		
	}

	@FXML
	private FXMLMenuBarController menuBarController;
	
    @FXML
    private ImageView image;

    @FXML
    private Label result;

    @FXML
    void noPressed(ActionEvent event) {
    	presentation.resultWasFalse();
    }

    @FXML
    void yesPressed(ActionEvent event) {
    	presentation.resultWasTrue();
    }


	@Override
	public void setImage(String url) {
		try {
			image.setImage(new Image(url));
		} catch(NullPointerException | IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			image.setImage(new Image("/DefaultTargetImage/avatar.gif"));
		}
	}


	@Override
	public void setResult(String result) {
		this.result.setText(result);
	}
    
}
