package Presentation;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import Data.BankDirectory;
import Data.TargetReader;
import Data.TargetWriter;
import Event.Subscriber;
import Model.Game;
import Presentation.Interfaces.ResultPropositionPresentationModel;
import Presentation.Interfaces.VisibilityNotifier;
import Presentation.ViewInterfaces.AkinatorResultProposistionView;
import Presentation.ViewInterfaces.ViewSwitcher;

public class ResultPropositionPresentation implements ResultPropositionPresentationModel, Subscriber<Game>, VisibilityNotifier {

	private BankDirectory bankDirectory;
	private AkinatorResultProposistionView resultProposistionView;
	private ViewSwitcher viewSwitcher;
	private List<String> targetNames;
	private Game game;
	
	public ResultPropositionPresentation(BankDirectory directory, ViewSwitcher viewSwitcher) {
		bankDirectory = directory;
		this.viewSwitcher = viewSwitcher;
	}
	
	@Override
	public void setView(AkinatorResultProposistionView resultProposistionView) {
		this.resultProposistionView = resultProposistionView;
	}

	@Override
	public void targetNotInList() {
		viewSwitcher.switchTo("AkinatorFalseResultAddNewView");
	}

	@Override
	public void targetWas(String targetName) {
		if(targetName != null) addQuestionsTo(targetName, bankDirectory.getTargetWriter());
		viewSwitcher.switchTo("AkinatorView");
	}

	private void addQuestionsTo(String targetName, TargetWriter repository) {
		repository.add(targetName, null, game.getAnsweredTrueQuestions());
		try {
			repository.saveTargets();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void search(String textToSearch) {
		List<String> names = new LinkedList<String>(targetNames);
		names.removeIf(n -> !n.toUpperCase().contains(textToSearch.toUpperCase().trim()));
		updateNameListInView(names);
	}

	@Override
	public void onVisible(String from) {
		if(from.equals("AkinatorQuestionsView")) {
			resultProposistionView.setTitle("Je n'ai pas trouvé à qui tu pense. Ton personnage est-il dans la liste ?");
		}else {
			resultProposistionView.setTitle("Ton personnage est-il dans la liste ?");
		}
		targetNames = extractTargetNamesFrom(bankDirectory.getTargetReader());
		updateNameListInView(targetNames);
	}

	private List<String> extractTargetNamesFrom(TargetReader repository) {
		return repository.getTargetNames();
	}
	
	private void updateNameListInView(List<String> targetNames){
		resultProposistionView.updateTargetList(targetNames);
	}

	@Override
	public void notify(Game message) {
		game = message;
	}
}
