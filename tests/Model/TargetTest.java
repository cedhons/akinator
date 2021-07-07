package Model;

import static FakeData.FakeData.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import Model.Target;

class TargetTest {

	private static final String name = "Ced";
	private static final String photo = "photo.png";
	
	@Test
	void getAttributesOnTargetCreation() {
		Target target = Target.create(name, photo, getQuestions1());
		
		assertEquals(target.getName(), "Ced");
		assertEquals(target.getImage(), photo);
	}
	
	@Test
	void getAttributesOnTargetCreationWithNullPhoto() {
		Target target = Target.create(name, null, getQuestions1());
		
		assertEquals(target.getName(), "Ced");
		assertEquals(target.getImage(), null);
	}
	
	@Test
	void throwErrorOnBadNameTargetCreation() {
		assertThrows(IllegalArgumentException.class, ()->Target.create(null, photo, getQuestions1()));
		assertThrows(IllegalArgumentException.class, ()->Target.create("   ", photo, getQuestions1()));
	}
	
	@Test
	void throwErrorOnBadPhotoTargetCreation() {
		assertThrows(IllegalArgumentException.class, ()->Target.create(name, "  ", getQuestions1()));
	}
	
	@Test
	void throwErrorOnBadQuestionTargetCreation() {
		assertThrows(IllegalArgumentException.class, ()->Target.create(name, photo, null));
		assertThrows(IllegalArgumentException.class, ()->Target.create(name, photo, new HashSet<String>()));
	}
	
	@Test
	void returnTrueWhenAskForContainsQuestion() {
		Target target = Target.create(name, photo, getQuestions1());
		
		for (String question : getQuestions1()) {
			assertTrue(target.contains(question));
		}
	}
	
	@Test
	void returnFalseWhenAskForContainsQuestion() {
		Target target = Target.create(name, photo, getQuestions1());
		
		for (String question : getQuestions2()) {
			assertFalse(target.contains(question));
		}
	}
	
	@Test
	void removeQuestionsFromATargetAndOneIsRemaining() {
		Target target = Target.create(name, photo, getQuestions2());
		Target targetNoCommonQuestions = target.minus(getQuestionsContainsTwoFrom2());
		
		assertEquals("question4", targetNoCommonQuestions.iterator().next());
	}
	
	@Test
	void addQuestionsToATarget() {
		Target target = Target.create(name, photo, getQuestions2());
		target = target.addAll(getQuestions1());
		
		Set<String> allQuestions = new HashSet<String>(getQuestions2());
		allQuestions.addAll(getQuestions1());
		
		for (String question : allQuestions) {
			assertTrue(target.contains(question));
		}
	}
	
	@Test
	void removeQuestionsFromATargetAndNoneIsRemaining() {
		Target target = Target.create(name, photo, getQuestions2());
		Target targetNoCommonQuestions = target.minus(getQuestions2());
		
		assertFalse(targetNoCommonQuestions.iterator().hasNext());
	}
	/*
	@Test
	void returnAllQuestionsWhenAskForGetQuestions() {
		Target target = Target.create(name, photo, getQuestions2());
		
		Set<String> questions = new HashSet<String>();
		for(String question : target.getQuestions()) {
			questions.add(question);
		}
		
		assertEquals(getQuestions2(), questions);
	}*/
	
	@Test
	void returnAllQuestionsWhenIterateOverTarget() {
		Set<String> questions = getQuestions2();
		Target target = Target.create(name, photo, questions);
		
		for (String question : target) {
			questions.add(question);
		}
		
		assertEquals(getQuestions2(), questions);
	}
	/*
	@Test
	void canBeRewind() {
		Target target = Target.create(name, photo, getQuestions2());
		
		String question;
		Set<String> questions = new HashSet<String>();
		while((question = target.getNextQuestion()) != null) {}
		
		target.rewind();
		while((question = target.getNextQuestion()) != null) {
			questions.add(question);
		}
		
		assertEquals(getQuestions2(), questions);
	}*/
	
	@Test
	void returnTrueWhenAskForEqualityBetweenTwoIdenticalTargets() {
		Target target1 = Target.create("Ced", "photo1", getQuestions1());
		Target target2 = Target.create("Ced", "photo2", getQuestions2());

		assertEquals(target1, target2);
	}
	
	@Test
	void returnFalseWhenAskForEqualityBetweenTwoNotIdenticalTargets() {
		Target target1 = Target.create("Ced", "photo1", getQuestions1());
		Target target2 = Target.create("Luc", "photo2", getQuestions2());

		assertNotEquals(target1, target2);
	}
	
	@Test
	void returnFalseWhenAskForEqualityBetweenTwoDifferentObjects() {
		Target target1 = Target.create("Ced", "photo1", getQuestions1());

		assertNotEquals(target1, new Object());
	}
	
	@Test
	void returnSameHashCodeWhenAskHashCodeFromTwoIdenticalTargets() {
		Target target1 = Target.create("Ced", "photo1", getQuestions1());
		Target target2 = Target.create("Ced", "photo2", getQuestions2());
		
		assertEquals(target1.hashCode(), target2.hashCode());
	}
	
	@Test
	void returnSameHashCodeWhenAskHashCodeFromTwoNotIdenticalTargets() {
		Target target1 = Target.create("Ced", "photo1", getQuestions1());
		Target target2 = Target.create("Luc", "photo2", getQuestions2());
		
		assertNotEquals(target1.hashCode(), target2.hashCode());
	}
}
