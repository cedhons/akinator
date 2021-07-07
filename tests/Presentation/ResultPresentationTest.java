package Presentation;

import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Model.Game;
import Presentation.ViewInterfaces.AkinatorResultView;
import Presentation.ViewInterfaces.ViewSwitcher;
import Program.PathSettings;

class ResultPresentationTest {

	private ViewSwitcher viewSwitcher;
	private AkinatorResultView view;
	private Game game;
	
	@BeforeEach
	private void beforeEach() {
		viewSwitcher = Mockito.mock(ViewSwitcher.class);
		view = Mockito.mock(AkinatorResultView.class);
		game = Mockito.mock(Game.class);
	}
	
	@Test
	void onVisibleGetAndSetResult() {
		ResultPresentation presentation = new ResultPresentation(viewSwitcher);
		Mockito.when(game.getNameResult()).thenReturn("targetName");
		Mockito.when(game.getImageResult()).thenReturn("targetImage");
		presentation.setView(view);
		presentation.notify(game);
		presentation.onVisible(null);
		Mockito.verify(game).getImageResult();
		Mockito.verify(game).getNameResult();
		Mockito.verify(view).setResult("Tu penses à targetName !");
		Mockito.verify(view).setImage(Paths.get(PathSettings.IMAGE_DIRECTORY, "targetImage").toUri().toString());
	}

	@Test
	void resultWasFalseSwitchView() {
		ResultPresentation presentation = new ResultPresentation(viewSwitcher);
		presentation.resultWasFalse();
		Mockito.verify(viewSwitcher).switchTo("AkinatorFalseResultProposistionView");
	}
	
	@Test
	void resultWasTrueSwitchView() {
		ResultPresentation presentation = new ResultPresentation(viewSwitcher);
		presentation.resultWasTrue();
		Mockito.verify(viewSwitcher).switchTo("AkinatorView");
	}
}
