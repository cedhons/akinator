package Views;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Presentation.Interfaces.BadResultTargetAdderPresentationModel;
import Presentation.Interfaces.MenuBarPresentationModel;
import Presentation.ViewInterfaces.AkinatorResultAddNewView;
import Views.inc.FXMLMenuBarController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLAkinatorResultAddNewController implements AkinatorResultAddNewView, Initializable {
   
	private BadResultTargetAdderPresentationModel presentation;
	private MenuBarPresentationModel menuBarPresentation;
	private FileChooser fileChooser;
	private File file;
	
	public FXMLAkinatorResultAddNewController(BadResultTargetAdderPresentationModel presentation, MenuBarPresentationModel menuBarPresentation) {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Charger une image");
		fileChooser.getExtensionFilters().addAll(                
				new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
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
    private ListView<String> questionList;

    @FXML
    private TextField target;

    @FXML
    private TextArea questions;
    
    @FXML
    private Label message;

    @FXML
    void addImagePressed(ActionEvent event) {
    	File file = fileChooser.showOpenDialog(new Stage());
    	if(file != null) {
    		this.file = file;
    	}
    }

    @FXML
    void validPressed(ActionEvent event) {
    	String[] questionsArray =  questions.getText().trim().split("\\n");
    	//if(questionsArray.length == 1 && questionsArray[0].equals("")) questionsArray = new String[0];
    	presentation.addTarget(target.getText(), file, questionsArray);
    }

	@Override
	public void updateQuestions(List<String> questions) {
		questionList.setItems(FXCollections.observableList(questions));
	}

	@Override
	public void setMessage(String message) {
		this.message.setText(message);
	}
}
