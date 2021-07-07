package Presentation;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import Data.BankDirectory;
import Data.TargetWriter;
import Event.Subscriber;
import Model.Game;
import Presentation.Interfaces.BadResultTargetAdderPresentationModel;
import Presentation.Interfaces.VisibilityNotifier;
import Presentation.ViewInterfaces.AkinatorResultAddNewView;
import Presentation.ViewInterfaces.ViewSwitcher;

public class BadResultTargetAdderPresentation implements BadResultTargetAdderPresentationModel, Subscriber<Game>, VisibilityNotifier {
	
	private BankDirectory bankDirectory;
	private Game game;
	private AkinatorResultAddNewView view;
	private ViewSwitcher viewSwitcher;

	public BadResultTargetAdderPresentation(BankDirectory bankDirectory, ViewSwitcher viewSwitcher) {
		this.bankDirectory = bankDirectory;
		this.viewSwitcher = viewSwitcher;
	}
	

	@Override
	public void setView(AkinatorResultAddNewView view) {
		this.view = view;
	}

	@Override
	public void addTarget(String name, File imageFile, String[] questions) {
		Set<String> questionsSet = new HashSet<String>(game.getAnsweredTrueQuestions());
		Set<String> newQuestions = new HashSet<String>();
		for (String question : questions) 
			if(!question.trim().equals("")) newQuestions.add(question);
		questionsSet.addAll(newQuestions);
		TargetWriter rep = bankDirectory.getTargetWriter();
		try {
			rep.add(name, imageFile, questionsSet);
			rep.saveTargets();
			viewSwitcher.switchTo("AkinatorView");
		} catch (IOException e) {
			view.setMessage("La cible n'a pas pu être ajoutée");
		} catch (IllegalArgumentException e) {
			view.setMessage("Complètez correctement les champs");
		}
	}

	@Override
	public void notify(Game message) {
		game = message;
	}

	@Override
	public void onVisible(String from) {
		view.updateQuestions(new LinkedList<String>(game.getAnsweredTrueQuestions()));
		view.setMessage("");
	}
}
