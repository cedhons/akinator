package Data;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.stream.JsonParsingException;

import Model.Target;
import Model.TargetBuilder;

import javax.json.*;

import Program.PathSettings;

public class JsonTargetFileHandler implements TargetReader, TargetWriter {
	
	private Iterator<Target> targetsIterator;
	private Map<String, Target> targets;
	private Path jsonPath;
	private TargetImageManager targetImageManager;
	
	public static JsonTargetFileHandler of(Path jsonPath) throws IOException, ParsingError {
		if(Files.exists(jsonPath)) {
			return new JsonTargetFileHandler(jsonPath, loadTargets(jsonPath));
		}else {
			return new JsonTargetFileHandler(jsonPath, new HashMap<String, Target>());
		}
	}
	
	private static Map<String, Target> loadTargets(Path jsonPath) throws IOException, ParsingError {
		try(Reader reader = Files.newBufferedReader(jsonPath, StandardCharsets.UTF_8); 
				JsonReader jsonReader = Json.createReader(reader)) {
				JsonObject jsonObject = jsonReader.readObject();
				return extractTargetsFromJsonArray(jsonObject);
		} catch(IOException ex) {
			throw new IOException("The Json file could not be load", ex);
		}catch(IllegalArgumentException | ClassCastException | NullPointerException | JsonParsingException ex) {
			throw new ParsingError("The Json file parsing failed", ex);
		}
	}
	
	private static Map<String, Target> extractTargetsFromJsonArray(JsonObject jsonObject) {
		Map<String, Target> targets = new HashMap<String, Target>();
		JsonArray targetsJson = jsonObject.getJsonArray("targets");
		for (JsonObject target : targetsJson.getValuesAs(JsonObject.class)) {
			TargetBuilder targetBuilder = new TargetBuilder();
			targetBuilder
				.withName(target.getString("name", null))
				.withImage(target.getString("image", null));
			
			for (JsonValue jsonValue : target.getJsonArray("questions")) {
				targetBuilder.addQuestion(jsonObject.getJsonArray("questions").getString(((JsonNumber)jsonValue).intValue()));
			}
			Target newTarget = targetBuilder.build();
			targets.put(newTarget.getName(), newTarget);
		}
		
		return targets;
	}

	private JsonTargetFileHandler(Path jsonPath, Map<String, Target> targets) {
		this.jsonPath = jsonPath;
		this.targets = targets;
		targetsIterator = targets.values().iterator();
		targetImageManager = new TargetImageManager(PathSettings.IMAGE_DIRECTORY);
	}

	@Override
	public Target getNextTarget() {
		if(targetsIterator.hasNext()) {
			return targetsIterator.next();
		}
		return null;
	}

	@Override
	public void saveTargets() throws IOException {
		try(Writer writer = Files.newBufferedWriter(jsonPath, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE); 
				JsonWriter jsonWriter = Json.createWriter(writer)) {
			jsonWriter.write(createJsonTargets());
		} catch(IOException ex) {
			throw new IOException("The Json file could not be write", ex);
		}
	}
	
	private JsonObject createJsonTargets() {
		JsonObjectBuilder json = Json.createObjectBuilder();
		
		List<String> questions = new LinkedList<String>();
		JsonArrayBuilder targetsArray = Json.createArrayBuilder();
		for (Target target : targets.values()) {
			JsonObjectBuilder targetJson = Json.createObjectBuilder();
			targetJson.add("name", target.getName());
			if(target.getImage() != null) {
				targetJson.add("image", target.getImage());
			}else {
				targetJson.addNull("image");
			}
			targetJson.add("questions", extractQuestions(questions, target));
			targetsArray.add(targetJson);
		}
		
		json.add("questions", Json.createArrayBuilder(questions));
		json.add("targets", targetsArray);
		
		return json.build();
	}

	private JsonArrayBuilder extractQuestions(List<String> questions, Target target) {
		JsonArrayBuilder questionsInTarget = Json.createArrayBuilder();
		for(String currentQuestion : target) {
			int indexOfQuestion = questions.indexOf(currentQuestion);
			if(indexOfQuestion < 0) {
				questionsInTarget.add(questions.size());
				questions.add(currentQuestion);
			}else {
				questionsInTarget.add(indexOfQuestion);
			}
		}
		return questionsInTarget;
	}

	@Override
	public void rewind() {
		targetsIterator = targets.values().iterator();
	}

	@Override
	public void add(String name, File image, Set<String> questions) {
		Target newTarget = Target.create(name, targetImageManager.copy(image), questions);
		if(targets.containsKey(name)) {
			targets.replace(name, targets.get(name).addAllQuestionsFrom(newTarget));
		}else {
			targets.put(name, newTarget);
		}
	}

	@Override
	public List<String> getTargetNames() {
		return new LinkedList<String>(targets.keySet());
	}
}
