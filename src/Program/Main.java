package Program;

import java.io.IOException;

import Data.BankDirectory;
import Data.DefaultBankDirectory;
import Data.TargetWriter;
import Event.EventChannel;
import Model.DefaultGameFactory;
import Model.Game;
import Presentation.MenuPresentation;
import Presentation.QuestionsPresentation;
import Presentation.BadResultTargetAdderPresentation;
import Presentation.DataCreatorNamePresentation;
import Presentation.MenuBarPresentation;
import Presentation.ResultPresentation;
import Presentation.ResultPropositionPresentation;
import Presentation.TargetAdderPresentation;
import Presentation.Interfaces.MenuBarPresentationModel;
import Views.FXMLAkinatorController;
import Views.FXMLAkinatorDataCreatorNameController;
import Views.FXMLAkinatorQuestionsController;
import Views.FXMLAkinatorResultAddNewController;
import Views.FXMLAkinatorResultController;
import Views.FXMLDataCreatorController;
import Views.FXMLResultProposistionController;
import Views.SceneSwitcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		DefaultBankDirectory bankDirectory = null;
		try {
			bankDirectory = DefaultBankDirectory.of(PathSettings.BANK_DIRECTORY, ".json");
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		SceneSwitcher sceneSwitcher = new SceneSwitcher(stage);
		
		EventChannel<Game> gameChannel = new EventChannel<Game>();
		EventChannel<TargetWriter> repChannel = new EventChannel<TargetWriter>();
		
		MenuBarPresentationModel menuBarPresentation = new MenuBarPresentation(sceneSwitcher);
		
		initAkinator(bankDirectory, sceneSwitcher, gameChannel, menuBarPresentation);
		initAkinatorQuestions(sceneSwitcher, gameChannel, menuBarPresentation);
		initAkinatorResult(sceneSwitcher, gameChannel, menuBarPresentation);
		initAkinatorFalseResultProposistion(bankDirectory, sceneSwitcher, gameChannel, menuBarPresentation);
		initAkinatorFalseResultAddNew(bankDirectory, sceneSwitcher, gameChannel, menuBarPresentation);
		initAkinatorDataCreatorNameView(bankDirectory, sceneSwitcher, repChannel, menuBarPresentation);
		initAkinatorDataCreatorView(sceneSwitcher, repChannel, menuBarPresentation);
		
		sceneSwitcher.switchTo("AkinatorView");
		//sceneSwitcher.switchTo("AkinatorDataCreatorNameView");
		stage.setTitle("Akinator");
		stage.show();
	}
	
	private void initAkinator(BankDirectory bankDirectory, SceneSwitcher sceneSwitcher, EventChannel<Game> gameChannel, MenuBarPresentationModel menuBarPresentation) throws IOException {
		MenuPresentation presentation = new MenuPresentation(bankDirectory, new DefaultGameFactory(), sceneSwitcher, gameChannel);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathSettings.AKINATOR_FXML));
		FXMLAkinatorController akinatorController = new FXMLAkinatorController(presentation, menuBarPresentation);
		fxmlLoader.setController(akinatorController);
		Scene scene = new Scene(fxmlLoader.load());
		scene.setUserData(presentation);
		sceneSwitcher.addView("AkinatorView", scene);
	}
	
	private void initAkinatorQuestions(SceneSwitcher sceneSwitcher, EventChannel<Game> gameChannel, MenuBarPresentationModel menuBarPresentation) throws IOException {
		QuestionsPresentation presenter = new QuestionsPresentation(sceneSwitcher);
		gameChannel.subscribe(presenter);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathSettings.AKINATOR_QUESTIONS_FXML));
		FXMLAkinatorQuestionsController akinatorController = new FXMLAkinatorQuestionsController(presenter, menuBarPresentation);
		fxmlLoader.setController(akinatorController);
		Scene scene = new Scene(fxmlLoader.load());
		scene.setUserData(presenter);
		sceneSwitcher.addView("AkinatorQuestionsView", scene);
	}
	
	private void initAkinatorResult(SceneSwitcher sceneSwitcher, EventChannel<Game> gameChannel, MenuBarPresentationModel menuBarPresentation) throws IOException {
		ResultPresentation presenter = new ResultPresentation(sceneSwitcher);
		gameChannel.subscribe(presenter);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathSettings.AKINATOR_RESULT_FXML));
		FXMLAkinatorResultController akinatorController = new FXMLAkinatorResultController(presenter, menuBarPresentation);
		fxmlLoader.setController(akinatorController);
		Scene scene = new Scene(fxmlLoader.load());
		scene.setUserData(presenter);
		sceneSwitcher.addView("AkinatorResultView", scene);
	}
	
	private void initAkinatorFalseResultProposistion(BankDirectory bankDirectory, SceneSwitcher sceneSwitcher, EventChannel<Game> gameChannel, MenuBarPresentationModel menuBarPresentation) throws IOException {
		ResultPropositionPresentation presenter = new ResultPropositionPresentation(bankDirectory, sceneSwitcher);
		gameChannel.subscribe(presenter);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathSettings.AKINATOR_PROPOSITION_FXML));
		FXMLResultProposistionController akinatorController = new FXMLResultProposistionController(presenter, menuBarPresentation);
		fxmlLoader.setController(akinatorController);
		Scene scene = new Scene(fxmlLoader.load());
		scene.setUserData(presenter);
		sceneSwitcher.addView("AkinatorFalseResultProposistionView", scene);
	}
	
	private void initAkinatorFalseResultAddNew(BankDirectory bankDirectory, SceneSwitcher sceneSwitcher, EventChannel<Game> gameChannel, MenuBarPresentationModel menuBarPresentation) throws IOException {
		BadResultTargetAdderPresentation presenter = new BadResultTargetAdderPresentation(bankDirectory, sceneSwitcher);
		gameChannel.subscribe(presenter);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathSettings.AKINATOR_FALSE_RESULT_ADD_NEW_FXML));
		FXMLAkinatorResultAddNewController akinatorController = new FXMLAkinatorResultAddNewController(presenter, menuBarPresentation);
		fxmlLoader.setController(akinatorController);
		Scene scene = new Scene(fxmlLoader.load());
		scene.setUserData(presenter);
		sceneSwitcher.addView("AkinatorFalseResultAddNewView", scene);
	}
	
	private void initAkinatorDataCreatorNameView(BankDirectory bankDirectory, SceneSwitcher sceneSwitcher, EventChannel<TargetWriter> repChannel, MenuBarPresentationModel menuBarPresentation) throws IOException {
		DataCreatorNamePresentation presenter = new DataCreatorNamePresentation(bankDirectory, sceneSwitcher, repChannel);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathSettings.AKINATOR_DATA_CREATOR_NAME_FXML));
		FXMLAkinatorDataCreatorNameController akinatorController = new FXMLAkinatorDataCreatorNameController(presenter, menuBarPresentation);
		fxmlLoader.setController(akinatorController);
		Scene scene = new Scene(fxmlLoader.load());
		scene.setUserData(presenter);
		sceneSwitcher.addView("AkinatorDataCreatorNameView", scene);
	}
	
	private void initAkinatorDataCreatorView(SceneSwitcher sceneSwitcher, EventChannel<TargetWriter> repChannel, MenuBarPresentationModel menuBarPresentation) throws IOException {
		TargetAdderPresentation presenter = new TargetAdderPresentation(sceneSwitcher);
		repChannel.subscribe(presenter);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathSettings.AKINATOR_DATA_CREATOR_FXML));
		FXMLDataCreatorController akinatorController = new FXMLDataCreatorController(presenter, menuBarPresentation);
		fxmlLoader.setController(akinatorController);
		Scene scene = new Scene(fxmlLoader.load());
		scene.setUserData(presenter);
		sceneSwitcher.addView("AkinatorDataCreatorView", scene);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
