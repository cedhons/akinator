package Presentation;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import Data.TargetWriter;
import Event.Subscriber;
import Presentation.Interfaces.TargetAdderPresentationModel;
import Presentation.Interfaces.VisibilityNotifier;
import Presentation.ViewInterfaces.AkinatorDataCreatorView;
import Presentation.ViewInterfaces.ViewSwitcher;

public class TargetAdderPresentation implements TargetAdderPresentationModel, Subscriber<TargetWriter>, VisibilityNotifier {

	private AkinatorDataCreatorView view;
	private TargetWriter rep;
	private ViewSwitcher viewSwitcher;
	
	public TargetAdderPresentation(ViewSwitcher viewSwitcher) {
		this.viewSwitcher = viewSwitcher;
	}
	
	@Override
	public void setView(AkinatorDataCreatorView akinatorDataCreatorView) {
		view = akinatorDataCreatorView;
	}

	@Override
	public void addTarget(String name, File image, String[] questions) {
		addTargetToRep(name, image, questions);
	}
	
	@Override
	public void save(String name, File image, String[] questions) {
		try {
			if(addTargetToRep(name, image, questions)) {
				rep.saveTargets();
				viewSwitcher.switchTo("AkinatorView");
			}
		} catch (IOException e) {
			view.setMessage("La banque n'a pas pu être enregistrée");
		}
	}
	
	private boolean addTargetToRep(String name, File image, String[] questions) {
		try {
			Set<String> newQuestions = new HashSet<String>();
			for (String question : questions) 
				if(!question.trim().equals("")) newQuestions.add(question);
			rep.add(name, image, new HashSet<String>(newQuestions));
			view.resetForm();
			return true;
		} catch (IllegalArgumentException e) {
			view.setMessage("Complètez correctement les champs");
			return false;
		}
	}
	
	@Override
	public void notify(TargetWriter message) {
		rep = message;
	}

	@Override
	public void onVisible(String from) {
		view.setMessage("");
	}
}
