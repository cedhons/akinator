package Presentation;

import java.io.FileNotFoundException;
import java.io.IOException;

import Data.BankDirectory;
import Data.ParsingError;
import Data.TargetReader;
import Event.EventChannel;
import Model.Game;
import Model.GameFactory;
import Presentation.Interfaces.MenuPresentationModel;
import Presentation.Interfaces.VisibilityNotifier;
import Presentation.ViewInterfaces.AkinatorView;
import Presentation.ViewInterfaces.ViewSwitcher;

public class MenuPresentation implements MenuPresentationModel, VisibilityNotifier{

	private BankDirectory bankDirectory;
	private ViewSwitcher viewSwitcher;
	private AkinatorView akinatorView;
	private EventChannel<Game> gameChannel;
	private GameFactory gameFactory;
	
	public MenuPresentation(BankDirectory directory, GameFactory gameFactory, ViewSwitcher viewSwitcher, EventChannel<Game> gameChannel) {
		this.viewSwitcher = viewSwitcher;
		bankDirectory = directory;
		this.gameChannel = gameChannel;
		this.gameFactory = gameFactory;
	}
	
	@Override
	public void setView(AkinatorView akinatorView) {
		this.akinatorView = akinatorView;
	}

	@Override
	public void setBank(String value) {
		try {
			if(bankDirectory != null) {
				bankDirectory.loadBankFile(value);
				setMessage("");
			}
		} catch (FileNotFoundException ex) {
			setMessage("Cette banque de donnée n'existe pas.");
			//System.out.println(ex.getMessage());
		} catch (IOException ex) {
			setMessage("Cette banque de donnée n'a pas pu être chargée.");
			System.out.println(ex.getMessage());
		} catch (ParsingError ex) {
			setMessage("Cette banque de connaissance n'est pas compatible.");
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void startGame() {
		TargetReader rep;
		if(bankDirectory != null && (rep = bankDirectory.getTargetReader()) != null) {
			gameChannel.notifyAll(gameFactory.getGame(rep));
			viewSwitcher.switchTo("AkinatorQuestionsView");
		}else {
			setMessage("Tu ne peux pas jouer sans banque de connaissance.");
		}
	}
	
	private void setMessage(String message) {
		akinatorView.setMessage(message);
	}

	@Override
	public void onVisible(String from) {
		if(bankDirectory != null) {
			akinatorView.updateBankList(bankDirectory.listFileNames());
			setMessage("");
		}else {
			setMessage("Les banques de connaissances n'ont pas été trouvées.");
		}
	}

}
