package Presentation.Interfaces;

import java.io.File;

import Presentation.ViewInterfaces.AkinatorResultAddNewView;

public interface BadResultTargetAdderPresentationModel {

	void setView(AkinatorResultAddNewView akinatorResultAddNewView);

	void addTarget(String name, File imageFile, String[] questions);

}
