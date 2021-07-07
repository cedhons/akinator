package Presentation.Interfaces;

import Presentation.ViewInterfaces.AkinatorResultProposistionView;

public interface ResultPropositionPresentationModel {

	void setView(AkinatorResultProposistionView ResultProposistionView);

	void targetNotInList();

	void targetWas(String selectedItem);

	void search(String textToSearch);

}
