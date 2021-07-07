package FakeData;

import java.util.HashSet;
import java.util.Set;

import Model.Target;

public class FakeData {

	public static Set<String> getQuestions1() {
		Set<String> questions = new HashSet<String>();
		questions.add("question1");
		questions.add("question2");
		questions.add("question3");
		return questions;
	}
	
	public static Set<String> getQuestions2() {
		Set<String> questions = new HashSet<String>();
		questions.add("question4");
		questions.add("question5");
		questions.add("question6");
		return questions;
	}
	
	public static Set<String> getQuestionsContainsOneFrom2() {
		Set<String> questions = new HashSet<String>();
		questions.add("question7");
		questions.add("question8");
		questions.add("question4");
		return questions;
	}
	
	public static Set<String> getQuestionsContainsTwoFrom2() {
		Set<String> questions = new HashSet<String>();
		questions.add("question9");
		questions.add("question5");
		questions.add("question6");
		return questions;
	}
	
	public static Set<String> getQuestionsContainsAllFrom2And1More() {
		Set<String> questions = getQuestions2();
		questions.add("question10");
		return questions;
	}
	
	public static Target[] getTargets() {
		Target[] targets = {
			Target.create("a", "photoA.png", getQuestions1()),
			Target.create("b", "photoB.png", getQuestions2()),
			Target.create("c", "photoC.png", getQuestionsContainsOneFrom2()),
			Target.create("d", "photoD.png", getQuestionsContainsTwoFrom2()),
			Target.create("e", "photoE.png", getQuestionsContainsAllFrom2And1More())
		};
		return targets;
	}
}
