package Views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Presentation.Interfaces.MenuBarPresentationModel;
import Presentation.Interfaces.ResultPropositionPresentationModel;
import Presentation.ViewInterfaces.AkinatorResultProposistionView;
import Views.inc.FXMLMenuBarController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class FXMLResultProposistionController implements Initializable, AkinatorResultProposistionView {

	private ResultPropositionPresentationModel presentation;
	private MenuBarPresentationModel menuBarPresentation;
	
	@FXML
	private FXMLMenuBarController menuBarController;
	
    @FXML
    private Label title;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> targetList;
    
    public FXMLResultProposistionController(ResultPropositionPresentationModel presentation, MenuBarPresentationModel menuBarPresentation) {
    	this.presentation = presentation;
    	this.menuBarPresentation = menuBarPresentation;
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presentation.setView(this);
		menuBarController.setPresentation(menuBarPresentation);
	}

    @FXML
    void notInListPressed(ActionEvent event) {
    	presentation.targetNotInList();
    }

    @FXML
    void search(KeyEvent event) {
    	presentation.search(searchBar.getText());
    }

    @FXML
    void validPressed(ActionEvent event) {
    	presentation.targetWas(targetList.getSelectionModel().getSelectedItem());
    }
    
	@Override
	public void updateTargetList(List<String> targets) {
		targetList.setItems(FXCollections.observableList(targets));
	}

	@Override
	public void setTitle(String title) {
		this.title.setText(title);
	}

}
