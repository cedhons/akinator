package Presentation;

import Event.Subscriber;
import Model.Game;
import Presentation.Interfaces.QuestionsPresentationModel;
import Presentation.Interfaces.VisibilityNotifier;
import Presentation.ViewInterfaces.AkinatorQuestionsView;
import Presentation.ViewInterfaces.ViewSwitcher;

public class QuestionsPresentation implements QuestionsPresentationModel, Subscriber<Game>, VisibilityNotifier {

	private ViewSwitcher viewSwitcher;
	private Game game;
	private AkinatorQuestionsView akinatorQuestionsView;
	
	public QuestionsPresentation(ViewSwitcher viewSwitcher) {
		this.viewSwitcher = viewSwitcher;
	}
	
	@Override
	public void setView(AkinatorQuestionsView akinatorQuestionsView) {
		this.akinatorQuestionsView = akinatorQuestionsView;
	}

	@Override
	public void answerTrue() {
		game.answerWas(Game.TRUE);
		setQuestion();
	}

	@Override
	public void answerFalse() {
		game.answerWas(Game.FALSE);
		setQuestion();
	}

	@Override
	public void answerDoNotKnow() {
		game.answerWas(Game.DO_NOT_KNOW);
		setQuestion();
	}

	@Override
	public void notify(Game message) {
		game = message;
	}
	
	private void setQuestion() {
		String question;
		if((question = game.getNextQuestion()) != null) {
			akinatorQuestionsView.setQuestion(question);
		}else if(game.getNameResult() != null) {
			viewSwitcher.switchTo("AkinatorResultView");
		}else {
			viewSwitcher.switchTo("AkinatorFalseResultProposistionView");
		}
	}

	@Override
	public void onVisible(String from) {
		setQuestion();
	}
}
