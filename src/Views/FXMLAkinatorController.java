package Views;

import java.net.URL;
import java.util.ResourceBundle;

import Presentation.Interfaces.MenuBarPresentationModel;
import Presentation.Interfaces.MenuPresentationModel;
import Presentation.ViewInterfaces.AkinatorView;
import Views.inc.FXMLMenuBarController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class FXMLAkinatorController implements Initializable, AkinatorView {
	
	private MenuPresentationModel presentation;
	private MenuBarPresentationModel menuBarPresentation;
	
	@FXML
	private FXMLMenuBarController menuBarController;
	
    @FXML
    private Label message;
    
    @FXML
    private ComboBox<String> listBank;
    
    public FXMLAkinatorController(MenuPresentationModel presentation, MenuBarPresentationModel menuBarPresentation) {
    	this.presentation = presentation;
    	this.menuBarPresentation = menuBarPresentation;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuBarController.setPresentation(menuBarPresentation);
		presentation.setView(this);
	}
	
    @FXML
    public void startGame(ActionEvent event) {
    	presentation.startGame();
    }
    
    @FXML
    void bankChanged(ActionEvent event) {
    	presentation.setBank(listBank.getValue());
    }
    
    @Override
    public void setMessage(String message) {
    	this.message.setText(message);
    }

	@Override
	public void updateBankList(String[] bankList) {
		listBank.setItems(FXCollections.observableArrayList(bankList));
	}

}
