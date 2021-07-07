package Views;

import java.net.URL;
import java.util.ResourceBundle;

import Presentation.Interfaces.DataCreatorNamePresentationModel;
import Presentation.Interfaces.MenuBarPresentationModel;
import Presentation.ViewInterfaces.AkinatorDataCreatorNameView;
import Views.inc.FXMLMenuBarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLAkinatorDataCreatorNameController implements AkinatorDataCreatorNameView, Initializable {

	private DataCreatorNamePresentationModel presentation;
	private MenuBarPresentationModel menuBarPresentation;
	
	public FXMLAkinatorDataCreatorNameController(DataCreatorNamePresentationModel presentation, MenuBarPresentationModel menuBarPresentation) {
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
    private TextField bankName;

    @FXML
    private Label message;

    @FXML
    void continuePressed(ActionEvent event) {
    	presentation.creatBank(bankName.getText());
    }

	@Override
	public void setMessage(String message) {
		this.message.setText(message);
	}
}
