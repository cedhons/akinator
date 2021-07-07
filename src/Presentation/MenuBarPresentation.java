package Presentation;

import Presentation.Interfaces.MenuBarPresentationModel;
import Presentation.ViewInterfaces.ViewSwitcher;

public class MenuBarPresentation implements MenuBarPresentationModel{

	private ViewSwitcher viewSwitcher;
	
	public MenuBarPresentation(ViewSwitcher viewSwitcher) {
		this.viewSwitcher = viewSwitcher;
	}
	
	@Override
	public void createDataBank() {
		viewSwitcher.switchTo("AkinatorDataCreatorNameView");
	}

	@Override
	public void backToMenu() {
		viewSwitcher.switchTo("AkinatorView");
	}

}
