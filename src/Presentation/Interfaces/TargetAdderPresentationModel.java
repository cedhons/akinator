package Presentation.Interfaces;

import java.io.File;

import Presentation.ViewInterfaces.AkinatorDataCreatorView;

public interface TargetAdderPresentationModel {
	
	void setView(AkinatorDataCreatorView akinatorDataCreatorView);
	
	void addTarget(String name, File image, String[] questions);
	
	void save(String name, File image, String[] questions);
}
