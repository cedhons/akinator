package Views;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import Presentation.Interfaces.MenuBarPresentationModel;
import Presentation.Interfaces.TargetAdderPresentationModel;
import Presentation.ViewInterfaces.AkinatorDataCreatorView;
import Views.inc.FXMLMenuBarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLDataCreatorController implements AkinatorDataCreatorView, Initializable {

	@FXML
	private FXMLMenuBarController menuBarController;
	
    @FXML
    private TextField name;

    @FXML
    private TextArea questions;

    @FXML
    private Label message;
    
    private TargetAdderPresentationModel presentation;
    private MenuBarPresentationModel menuBarPresentation;
    private FileChooser fileChooser;
    private File file;
    
    public FXMLDataCreatorController(TargetAdderPresentationModel presentation, MenuBarPresentationModel menuBarPresentation) {
    	this.presentation = presentation;
		fileChooser = new FileChooser();
		fileChooser.setTitle("Charger une image");
		fileChooser.getExtensionFilters().addAll(                
				new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
		this.menuBarPresentation = menuBarPresentation;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presentation.setView(this);
		menuBarController.setPresentation(menuBarPresentation);
	}

    @FXML
    void addImagePressed(ActionEvent event) {
    	File file = fileChooser.showOpenDialog(new Stage());
    	if(file != null) {
        	this.file = file;
    	}
    }

    @FXML
    void addTargetPressed(ActionEvent event) {
    	presentation.addTarget(name.getText(), file, questionsStringToTab());
    }

    @FXML
    void endPressed(ActionEvent event) {
    	presentation.save(name.getText(), file, questionsStringToTab());
    }
    

	private String[] questionsStringToTab() {
		String[] questionsArray =  questions.getText().trim().split("\\n");
    	//if(questionsArray.length == 1 && questionsArray[0].equals("")) questionsArray = new String[0];
		return questionsArray;
	}

	@Override
	public void setMessage(String message) {
		this.message.setText(message);
	}

	@Override
	public void resetForm() {
		this.name.setText("");
		this.questions.setText("");
		this.message.setText("");
		file = null;
	}
}
