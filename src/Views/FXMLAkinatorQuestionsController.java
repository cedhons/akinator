package Views;

import java.net.URL;
import java.util.ResourceBundle;

import Presentation.Interfaces.MenuBarPresentationModel;
import Presentation.Interfaces.QuestionsPresentationModel;
import Presentation.ViewInterfaces.AkinatorQuestionsView;
import Views.inc.FXMLMenuBarController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLAkinatorQuestionsController implements Initializable, AkinatorQuestionsView {

	private QuestionsPresentationModel presentation;
	private MenuBarPresentationModel menuBarPresentation;
	
	public FXMLAkinatorQuestionsController(QuestionsPresentationModel presentation, MenuBarPresentationModel menuBarPresentation) {
		this.presentation = presentation;
		this.menuBarPresentation = menuBarPresentation;
	}
	
    @FXML
    private Label question;
    
    @FXML
    private FXMLMenuBarController menuBarController;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		presentation.setView(this);
		menuBarController.setPresentation(menuBarPresentation);
	}

    @FXML
    void doNotKnowPressed(ActionEvent event) {
    	presentation.answerDoNotKnow();
    }

    @FXML
    void noPressed(ActionEvent event) {
    	presentation.answerFalse();
    }

    @FXML
    void yesPressed(ActionEvent event) {
    	presentation.answerTrue();
    }

	@Override
	public void setQuestion(String question) {
		this.question.setText(question);
	}
}
