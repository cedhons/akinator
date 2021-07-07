package Presentation.Interfaces;

import Presentation.ViewInterfaces.AkinatorResultView;

public interface ResultPresentationModel {

	void resultWasFalse();

	void resultWasTrue();

	void setView(AkinatorResultView resultView);

}
