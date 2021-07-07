package Views;

import java.util.HashMap;
import java.util.Map;


import Presentation.Interfaces.VisibilityNotifier;
import Presentation.ViewInterfaces.ViewSwitcher;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher implements ViewSwitcher {
	
	private Map<String, Scene> viewMap;
	private Stage stage;
	private String currentScene;

	public SceneSwitcher(Stage stage) {
		this.stage = stage;
		viewMap = new HashMap<>();
	}
	
	public void addView(String name, Scene scene) {
		viewMap.put(name, scene);
	}
	
	@Override
	public void switchTo(String name) {
		Scene scene;
		if ((scene = viewMap.get(name)) != null) {
			stage.setScene(scene);
			if(scene.getUserData() instanceof VisibilityNotifier) 
				((VisibilityNotifier)(scene.getUserData())).onVisible(currentScene);
			currentScene = name;
		}
	}
}
