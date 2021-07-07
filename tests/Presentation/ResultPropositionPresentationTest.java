package Presentation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Data.BankDirectory;
import Data.TargetReader;
import Data.TargetWriter;
import Model.Game;
import Presentation.ViewInterfaces.AkinatorResultProposistionView;
import Presentation.ViewInterfaces.ViewSwitcher;

class ResultPropositionPresentationTest {

	private ViewSwitcher viewSwitcher;
	private BankDirectory directory;
	private TargetWriter writer;
	private TargetReader reader;
	private AkinatorResultProposistionView view;
	private Game game;
	
	@BeforeEach
	private void beforeEach() {
		viewSwitcher = Mockito.mock(ViewSwitcher.class);
		directory = Mockito.mock(BankDirectory.class);
		writer = Mockito.mock(TargetWriter.class);
		reader = Mockito.mock(TargetReader.class);
		Mockito.when(directory.getTargetWriter()).thenReturn(writer);
		Mockito.when(directory.getTargetReader()).thenReturn(reader);
		view = Mockito.mock(AkinatorResultProposistionView.class);
		game = Mockito.mock(Game.class);
	}
	
	@Test
	void targetNotInListSwitchView() {
		var presentation = new ResultPropositionPresentation(directory, viewSwitcher);
		presentation.targetNotInList();
		Mockito.verify(viewSwitcher).switchTo("AkinatorFalseResultAddNewView");
	}

	@Test
	void targetWasAddQuestionsToTargetAndSwitchView() {
		Mockito.when(game.getAnsweredTrueQuestions()).thenReturn(new HashSet<String>());
		var presentation = new ResultPropositionPresentation(directory, viewSwitcher);
		presentation.notify(game);
		presentation.targetWas("Target");
		Mockito.verify(writer).add("Target", null, game.getAnsweredTrueQuestions());
		Mockito.verify(viewSwitcher).switchTo("AkinatorView");
	}
	
	@Test
	void searchUpdateNameList() {
		List<String> allNames = new ArrayList<String>();
		allNames.add("Luc");
		allNames.add("Jean");
		allNames.add("Lucie");
		List<String> remainNames = new ArrayList<String>(allNames);
		remainNames.remove(1);
		
		Mockito.when(reader.getTargetNames()).thenReturn(allNames);
		var presentation = new ResultPropositionPresentation(directory, viewSwitcher);
		presentation.setView(view);
		presentation.onVisible("");
		presentation.search("lU");
		Mockito.verify(view).updateTargetList(remainNames);
	}
	
	@Test
	void onVisibleViewSetMessageAndUpdateNameList() {
		Mockito.when(reader.getTargetNames()).thenReturn(new ArrayList<String>());
		var presentation = new ResultPropositionPresentation(directory, viewSwitcher);
		presentation.setView(view);
		presentation.onVisible("");
		Mockito.verify(view).setTitle("Ton personnage est-il dans la liste ?");
		Mockito.verify(view).updateTargetList(new ArrayList<String>());
	}
	
	@Test
	void onVisibleFromQuestionViewSetMessageAndUpdateNameList() {
		Mockito.when(reader.getTargetNames()).thenReturn(new ArrayList<String>());
		var presentation = new ResultPropositionPresentation(directory, viewSwitcher);
		presentation.setView(view);
		presentation.onVisible("AkinatorQuestionsView");
		Mockito.verify(view).setTitle("Je n'ai pas trouvé à qui tu pense. Ton personnage est-il dans la liste ?");
		Mockito.verify(view).updateTargetList(new ArrayList<String>());
	}
}
