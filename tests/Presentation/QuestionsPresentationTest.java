package Presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Model.Game;
import Presentation.ViewInterfaces.AkinatorQuestionsView;
import Presentation.ViewInterfaces.ViewSwitcher;

class QuestionsPresentationTest {
	
	private ViewSwitcher viewSwitcher;
	private AkinatorQuestionsView view;
	private Game game;
	
	@BeforeEach
	private void beforeEach() {
		viewSwitcher = Mockito.mock(ViewSwitcher.class);
		view = Mockito.mock(AkinatorQuestionsView.class);
		game = Mockito.mock(Game.class);
	}

	@Test
	void onVisibleSetQuestion() {
		Mockito.when(game.getNextQuestion()).thenReturn("question");
		QuestionsPresentation presentation = new QuestionsPresentation(viewSwitcher);
		presentation.setView(view);
		presentation.notify(game);
		presentation.onVisible(null);
		Mockito.verify(view).setQuestion("question");
	}
	
	@Test
	void answerTrueTellAnswerToGameAndSetNewQuestion() {
		Mockito.when(game.getNextQuestion()).thenReturn("question");
		QuestionsPresentation presentation = new QuestionsPresentation(viewSwitcher);
		presentation.setView(view);
		presentation.notify(game);
		presentation.answerTrue();
		Mockito.verify(game).answerWas(Game.TRUE);
		Mockito.verify(view).setQuestion("question");
	}
	
	@Test
	void answerFalseTellAnswerToGameAndSetNewQuestion() {
		Mockito.when(game.getNextQuestion()).thenReturn("question");
		QuestionsPresentation presentation = new QuestionsPresentation(viewSwitcher);
		presentation.setView(view);
		presentation.notify(game);
		presentation.answerFalse();
		Mockito.verify(game).answerWas(Game.FALSE);
		Mockito.verify(view).setQuestion("question");
	}
	
	@Test
	void answerDoNotKnowTellAnswerToGameAndSetNewQuestion() {
		Mockito.when(game.getNextQuestion()).thenReturn("question");
		QuestionsPresentation presentation = new QuestionsPresentation(viewSwitcher);
		presentation.setView(view);
		presentation.notify(game);
		presentation.answerDoNotKnow();
		Mockito.verify(game).answerWas(Game.DO_NOT_KNOW);
		Mockito.verify(view).setQuestion("question");
	}

}
