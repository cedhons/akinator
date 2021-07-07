package Presentation;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Data.BankDirectory;
import Data.TargetWriter;
import Model.Game;
import Presentation.ViewInterfaces.AkinatorResultAddNewView;
import Presentation.ViewInterfaces.ViewSwitcher;

class BadResultTargetAdderPresentationTest {
	
	private BankDirectory bankDirectory;
	private ViewSwitcher viewSwitcher;
	private AkinatorResultAddNewView view;
	private Game game;
	private TargetWriter targetWriter;
	
	@BeforeEach
	private void beforeEach() {
		bankDirectory = Mockito.mock(BankDirectory.class);
		targetWriter = Mockito.mock(TargetWriter.class);
		Mockito.when(bankDirectory.getTargetWriter()).thenReturn(targetWriter);
		viewSwitcher = Mockito.mock(ViewSwitcher.class);
		view = Mockito.mock(AkinatorResultAddNewView.class);
		game = Mockito.mock(Game.class);
		Mockito.when(game.getAnsweredTrueQuestions()).thenReturn(new HashSet<String>());
	}

	@Test
	void addTargetSavesTargetsAndSwitchView() throws IOException {
		var presentation = new BadResultTargetAdderPresentation(bankDirectory, viewSwitcher);
		presentation.notify(game);
		presentation.addTarget("Test", null, new String[] {"Question1"});
		
		Mockito.verify(targetWriter).add("Test", null, new HashSet<String>() {{add("Question1");}});
		Mockito.verify(targetWriter).saveTargets();
		Mockito.verify(viewSwitcher).switchTo("AkinatorView");
	}

	@Test
	void addTargetSavesTargetsFailedSetMessage() throws IOException {
		var presentation = new BadResultTargetAdderPresentation(bankDirectory, viewSwitcher);
		Mockito.doThrow(IOException.class).when(targetWriter).saveTargets();
		presentation.notify(game);
		presentation.setView(view);
		presentation.addTarget("Test", null, new String[] {"Question1"});
		
		Mockito.verify(view).setMessage("La cible n'a pas pu être ajoutée");
	}
	
	@Test
	void onVisibleUpdateQuestionsAndMessage() throws IOException {
		var presentation = new BadResultTargetAdderPresentation(bankDirectory, viewSwitcher);
		presentation.notify(game);
		presentation.setView(view);
		presentation.onVisible(null);
		
		Mockito.verify(view).updateQuestions(new LinkedList<String>());
		Mockito.verify(view).setMessage("");
	}
}
