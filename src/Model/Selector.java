package Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Selector implements Game {
	
	private TargetsHandler handleTargets;
	private String currentQuestion;
	private Iterator<String> questionsIterator;
	private Map<String, Set<String>> answers;

	public Selector(TargetsHandler handler) {
		handleTargets = handler;
		handler.selectRandomTarget();
		answers = new HashMap<String, Set<String>>();
		answers.put("answeredTrue", new HashSet<String>());
		answers.put("answeredDoNotKnow", new HashSet<String>());
		if(handler.isCurrentTargetExisting()) questionsIterator = handler.getCurrentTargetIterator();
	}

	@Override
	public String getNextQuestion() {
		if(handleTargets.isCurrentTargetExisting() && !handleTargets.oneRemaining()) {
			return setNextQuestion();
		}
		return null;
	}

	private String setNextQuestion() {
		if(questionsIterator.hasNext()) {
			currentQuestion = questionsIterator.next();
		}else {
			whenAllTargetQuestionsAskedSetNextTarget();
		}
		return currentQuestion;
	}

	private void whenAllTargetQuestionsAskedSetNextTarget() {
		setNextTarget();
		if(questionsIterator.hasNext()) {
			currentQuestion = questionsIterator.next();
		}else {
			currentQuestion = null;
		}
	}

	@Override
	public void answerWas(byte answer) {
		if(answer == TRUE) {
			handleTargets.keepTargetsLike(t -> t.contains(currentQuestion));
			answers.get("answeredTrue").add(currentQuestion);
		}else if(answer == FALSE) {
			handleTargets.keepTargetsLike(t -> !t.contains(currentQuestion));
			setRandomTarget();
		}else {
			answers.get("answeredDoNotKnow").add(currentQuestion);
		}
	}
	
	private void setNextTarget() {
		Set<String> questionsToRemove = getSetWithAnsweredTrueAndDoNotKnow();
		handleTargets.selectNextTargetWithout(questionsToRemove);
		if(handleTargets.isCurrentTargetExisting()) {
			questionsIterator = handleTargets.getCurrentTargetIterator();
		}
	}

	private void setRandomTarget() {
		Set<String> questionsToRemove = getSetWithAnsweredTrueAndDoNotKnow();
		handleTargets.selectRandomTargetWithout(questionsToRemove);
		if(handleTargets.isCurrentTargetExisting()) {
			questionsIterator = handleTargets.getCurrentTargetIterator();
		}
	}
	
	private Set<String> getSetWithAnsweredTrueAndDoNotKnow() {
		Set<String> questionsToRemove = new HashSet<String>(answers.get("answeredTrue"));
		questionsToRemove.addAll(answers.get("answeredDoNotKnow"));
		return questionsToRemove;
	}

	@Override
	public String getNameResult() {
		if(handleTargets.isCurrentTargetExisting()) {
			return handleTargets.getCurrentTargetName();
		}
		return null;
	}

	@Override
	public String getImageResult() {
		if(handleTargets.isCurrentTargetExisting()) {
			return handleTargets.getCurrentTargetImage();
		}
		return null;
	}

	@Override
	public Set<String> getAnsweredTrueQuestions() {
		return answers.get("answeredTrue");
	}
}
