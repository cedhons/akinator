package Presentation.Interfaces;

import Presentation.ViewInterfaces.AkinatorQuestionsView;

public interface QuestionsPresentationModel {

	void setView(AkinatorQuestionsView akinatorQuestionsView);
	
	void answerTrue();
	
	void answerFalse();
	
	void answerDoNotKnow();

}
