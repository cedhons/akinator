package Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Model.Target;
import Model.TargetBuilder;

class TargetBuilderTest {

	@Test
	void addQuestionsProvideTargetWithTheseQuestions() {
		TargetBuilder builder = new TargetBuilder();
		Target target = builder
				.withName("target")
				.withImage("image")
				.addQuestion("question1")
				.addQuestion("question2")
				.build();
		
		assertTrue(target.contains("question1"));
		assertTrue(target.contains("question2"));
	}
	
	@Test
	void withNameProvideTargetWithCorrectName() {
		TargetBuilder builder = new TargetBuilder();
		Target target = builder
				.withName("target")
				.withImage("image")
				.addQuestion("question1")
				.addQuestion("question2")
				.build();
		
		assertEquals("target", target.getName());
	}
	
	@Test
	void withImageProvideTargetWithCorrectName() {
		TargetBuilder builder = new TargetBuilder();
		Target target = builder
				.withName("target")
				.withImage("image")
				.addQuestion("question1")
				.addQuestion("question2")
				.build();
		
		assertEquals("image", target.getImage());
	}
	
	@Test
	void noImageProvideTargetWithImageAsNull() {
		TargetBuilder builder = new TargetBuilder();
		Target target = builder
				.withName("target")
				.addQuestion("question1")
				.addQuestion("question2")
				.build();
		
		assertEquals(null, target.getImage());
	}
}
