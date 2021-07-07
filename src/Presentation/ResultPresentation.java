package Presentation;

import java.nio.file.Paths;

import Event.Subscriber;
import Model.Game;
import Presentation.Interfaces.ResultPresentationModel;
import Presentation.Interfaces.VisibilityNotifier;
import Presentation.ViewInterfaces.AkinatorResultView;
import Presentation.ViewInterfaces.ViewSwitcher;
import Program.PathSettings;

public class ResultPresentation implements ResultPresentationModel, Subscriber<Game>, VisibilityNotifier {

	private AkinatorResultView resultView;
	private ViewSwitcher viewSwitcher;
	private Game game;
	
	public ResultPresentation(ViewSwitcher viewSwitcher) {
		this.viewSwitcher = viewSwitcher;
	}
	
	@Override
	public void resultWasFalse() {
		viewSwitcher.switchTo("AkinatorFalseResultProposistionView");
	}

	@Override
	public void resultWasTrue() {
		viewSwitcher.switchTo("AkinatorView");
	}

	@Override
	public void setView(AkinatorResultView resultView) {
		this.resultView = resultView;
	}

	@Override
	public void onVisible(String from) {
		resultView.setResult("Tu penses à " + game.getNameResult() + " !");
		String imageName = game.getImageResult();
		String imagePath = null;
		if(imageName != null) imagePath = Paths.get(PathSettings.IMAGE_DIRECTORY, imageName).toUri().toString();
		resultView.setImage(imagePath);
	}

	@Override
	public void notify(Game message) {
		game = message;
	}

}
