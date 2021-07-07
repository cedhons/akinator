package Presentation;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Data.BankDirectory;
import Data.ParsingError;
import Data.TargetReader;
import Event.EventChannel;
import Model.Game;
import Model.GameFactory;
import Presentation.ViewInterfaces.AkinatorView;
import Presentation.ViewInterfaces.ViewSwitcher;

class MenuPresentationTest {

	private BankDirectory bankDirectory;
	private ViewSwitcher viewSwitcher;
	private AkinatorView view;
	private GameFactory gameFactory;
	private Game game;
	private TargetReader targetReader;
	private EventChannel<Game> gameChannel;
	
	@BeforeEach
	private void beforeEach() {
		bankDirectory = Mockito.mock(BankDirectory.class);
		targetReader = Mockito.mock(TargetReader.class);
		gameChannel = Mockito.mock(EventChannel.class);
		viewSwitcher = Mockito.mock(ViewSwitcher.class);
		view = Mockito.mock(AkinatorView.class);
		gameFactory = Mockito.mock(GameFactory.class);
		game = Mockito.mock(Game.class);
		Mockito.when(gameFactory.getGame(targetReader)).thenReturn(game);
	}
	
	@Test
	void setBankLoadTheBank() throws IOException, ParsingError {
		var presentation = new MenuPresentation(bankDirectory, gameFactory, viewSwitcher, gameChannel);
		presentation.setView(view);
		presentation.setBank("bank");
		Mockito.verify(bankDirectory).loadBankFile("bank");
	}
	
	@Test
	void setBankIOExceptionSetMessage() throws IOException, ParsingError {
		Mockito.doThrow(IOException.class).when(bankDirectory).loadBankFile("bank");
		var presentation = new MenuPresentation(bankDirectory, gameFactory, viewSwitcher, gameChannel);
		presentation.setView(view);
		presentation.setBank("bank");
		Mockito.verify(view).setMessage("Cette banque de donnée n'a pas pu être chargée.");
	}
	
	@Test
	void setBankFileNotFoundExceptionSetMessage() throws IOException, ParsingError {
		Mockito.doThrow(FileNotFoundException.class).when(bankDirectory).loadBankFile("bank");
		var presentation = new MenuPresentation(bankDirectory, gameFactory, viewSwitcher, gameChannel);
		presentation.setView(view);
		presentation.setBank("bank");
		Mockito.verify(view).setMessage("Cette banque de donnée n'existe pas.");
	}
	
	@Test
	void setBankParsingErrorSetMessage() throws IOException, ParsingError {
		Mockito.doThrow(ParsingError.class).when(bankDirectory).loadBankFile("bank");
		var presentation = new MenuPresentation(bankDirectory, gameFactory, viewSwitcher, gameChannel);
		presentation.setView(view);
		presentation.setBank("bank");
		Mockito.verify(view).setMessage("Cette banque de connaissance n'est pas compatible.");
	}
	
	@Test
	void startGameNotifyGameAndSwitchView() throws IOException, ParsingError {
		Mockito.when(bankDirectory.getTargetReader()).thenReturn(targetReader);
		var presentation = new MenuPresentation(bankDirectory, gameFactory, viewSwitcher, gameChannel);
		presentation.setView(view);
		presentation.startGame();
		Mockito.verify(gameChannel).notifyAll(game);
		Mockito.verify(viewSwitcher).switchTo("AkinatorQuestionsView");
	}
	
	@Test
	void startGameNoBankSelectedSetMessage() throws IOException, ParsingError {
		Mockito.when(bankDirectory.getTargetReader()).thenReturn(null);
		var presentation = new MenuPresentation(bankDirectory, gameFactory, viewSwitcher, gameChannel);
		presentation.setView(view);
		presentation.startGame();
		Mockito.verify(view).setMessage("Tu ne peux pas jouer sans banque de connaissance.");
	}

	@Test
	void startGameNoBankFoundSetMessage() throws IOException, ParsingError {
		Mockito.when(bankDirectory.getTargetReader()).thenReturn(targetReader);
		var presentation = new MenuPresentation(null, gameFactory, viewSwitcher, gameChannel);
		presentation.setView(view);
		presentation.startGame();
		Mockito.verify(view).setMessage("Tu ne peux pas jouer sans banque de connaissance.");
	}
	
	@Test
	void onVisibleUpdateBankList() throws IOException, ParsingError {
		var presentation = new MenuPresentation(bankDirectory, gameFactory, viewSwitcher, gameChannel);
		presentation.setView(view);
		presentation.onVisible(null);
		Mockito.verify(view).updateBankList(null);
	}
	
	@Test
	void onVisibleWhenNoBankFoundSetMessage() throws IOException, ParsingError {
		var presentation = new MenuPresentation(null, gameFactory, viewSwitcher, gameChannel);
		presentation.setView(view);
		presentation.onVisible(null);
		Mockito.verify(view).setMessage("Les banques de connaissances n'ont pas été trouvées.");
	}
}
