package Model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Target implements Iterable<String> {

	private final String name;
	private final String image;
	private final Set<String> questions;
	//private Iterator<String> questionsIterator;
	
	public static Target create(String name, String image, Set<String> questions) {
		if(name == null || name.trim().isEmpty() || questions == null || questions.isEmpty() || (image != null && image.trim().isEmpty())) {
			throw new IllegalArgumentException("The parameters are incorrect for target");
		}
		
		return new Target(name, image, questions);
	}

	private Target(String name, String image, Set<String> questions) {
		this.name = name;
		this.image = image;
		this.questions = questions;
		//questionsIterator = questions.iterator();
	}

	public String getName() {
		return name;
	}

	public String getImage() {
		return image;
	}

	public boolean contains(String questionsToCompare) {
		return questions.contains(questionsToCompare);
	}

	public Target minus(Set<String> questionsToRemove) {
		Set<String> newQuestionsSet = new HashSet<String>(questions);
		newQuestionsSet.removeAll(questionsToRemove);
		return new Target(name, image, newQuestionsSet);
	}
	
	public Target addAll(Set<String> questionsToAdd) {
		Set<String> newQuestionsSet = new HashSet<String>(questions);
		newQuestionsSet.addAll(questionsToAdd);
		return new Target(name, image, newQuestionsSet);
	}
	/*
	public String getNextQuestion() {
		if(questionsIterator.hasNext()) {
			return questionsIterator.next();
		}
		return null;
	}*/
	

	@Override
	public Iterator<String> iterator() {
		return questions.iterator();
	}
	
	/*
	public Set<String> getQuestions() {
		return new HashSet<String>(questions);
	}*/
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Target) {
			return ((Target)obj).name == this.name;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public Target addAllQuestionsFrom(Target target) {
		return addAll(target.questions);
	}
/*
	public Target removeAllQuestionsFrom(Target target) {
		Set<String> newQuestionsSet = new HashSet<String>(questions);
		newQuestionsSet.removeAll(target.questions);
		return new Target(name, image, newQuestionsSet);
	}
*/
}
