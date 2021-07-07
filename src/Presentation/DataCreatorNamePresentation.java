package Presentation;

import java.io.IOException;

import Data.BankDirectory;
import Data.ParsingError;
import Data.TargetWriter;
import Event.EventChannel;
import Presentation.Interfaces.DataCreatorNamePresentationModel;
import Presentation.Interfaces.VisibilityNotifier;
import Presentation.ViewInterfaces.AkinatorDataCreatorNameView;
import Presentation.ViewInterfaces.ViewSwitcher;

public class DataCreatorNamePresentation implements DataCreatorNamePresentationModel, VisibilityNotifier {

	private AkinatorDataCreatorNameView view;
	private EventChannel<TargetWriter> eventChannel;
	private BankDirectory directory;
	private ViewSwitcher viewSwitcher;
	
	public DataCreatorNamePresentation(BankDirectory directory, ViewSwitcher viewSwitcher, EventChannel<TargetWriter> eventChannel) {
		this.eventChannel = eventChannel;
		this.directory = directory;
		this.viewSwitcher = viewSwitcher;
	}
	
	@Override
	public void creatBank(String name) {
		if(directory.contains(name)) {
			view.setMessage("Cette banque existe déjà");
		}else if(name.trim().equals("")) {
			view.setMessage("Ce nom est invalide");
		}else {
			try {
				directory.addBank(name);
				directory.loadBankFile(name);
				eventChannel.notifyAll(directory.getTargetWriter());
				viewSwitcher.switchTo("AkinatorDataCreatorView");
			} catch (IOException | ParsingError e) {
				view.setMessage("Une erreur indéterminée est survenue");
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void setView(AkinatorDataCreatorNameView view) {
		this.view = view;
	}

	@Override
	public void onVisible(String from) {
		view.setMessage("");
	}

}
