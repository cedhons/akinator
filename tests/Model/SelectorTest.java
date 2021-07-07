package Model;

import static FakeData.FakeData.getTargets;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static FakeData.FakeData.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Data.TargetReader;
import FakeData.BobEpongeData;
import Model.Selector;
import Model.Target;
import Model.TargetsHandler;

class SelectorTest {

	//échantillon des différents cas distingu�s par l'algorithme :
	
	@Test
	void returnAllTheQuestionsFromAllTargetWhenDoNotKnow() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(2)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);
		
		Set<String> questions = new HashSet<String>();
		String questionToAdd;
		while((questionToAdd = selector.getNextQuestion()) != null) {
			selector.answerWas(Selector.DO_NOT_KNOW);
			questions.add(questionToAdd);
		}
		
		assertTrue(questions.containsAll(getQuestions1()) && questions.containsAll(getQuestions2()));
	}
	
	@Test
	void returnNullAfterAnswerWasFalseAndOneTargetRemain() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);
		
		selector.getNextQuestion();
		selector.answerWas(Selector.FALSE);
		assertNull(selector.getNextQuestion());
	}
	
	@Test
	void returnNullAfterAnswerWasTrueAndOneTargetRemain() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);
		
		selector.getNextQuestion();
		selector.answerWas(Selector.TRUE);
		assertNull(selector.getNextQuestion());
	}
	
	@Test
	void returnNullWhenOneTargetRemain() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Mockito.when(rep.getNextTarget()).thenReturn(getTargets()[0], new Target[] {null});
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		assertNull(selector.getNextQuestion());
	}
	
	@Test
	void returnNullWhenAllQuestionsFromAllTargetsWasReturnedAfterDoNotKnow() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		for(int i = 0; i < getQuestions1().size() + getQuestions2().size(); i++) {
			selector.answerWas(Selector.DO_NOT_KNOW);
			selector.getNextQuestion();
		}
		assertNull(selector.getNextQuestion());
	}
	
	@Test
	void returnNullWhenNoNameResult() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Mockito.when(rep.getNextTarget()).thenReturn(null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		assertNull(selector.getNameResult());
	}
	
	@Test
	void returnNullWhenNoImageResult() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Mockito.when(rep.getNextTarget()).thenReturn(null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		assertNull(selector.getImageResult());
	}
	
	@Test
	void getQuestionsTrueReturnAllTheQuestionsWasAnswerTrue() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = BobEpongeData.getBobEpongeTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		Set<String> questionsRecieved = new HashSet<String>();
		
		String currentQuestion;
		while((currentQuestion = selector.getNextQuestion()) != null) {
			questionsRecieved.add(currentQuestion);
			selector.answerWas(Selector.TRUE);
		}
		
		assertEquals(questionsRecieved, selector.getAnsweredTrueQuestions());
	}
	
	//Cas normal :
	
	@Test
	void returnARandomQuestionFromARandomTarget() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(2)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);
		
		assertTrue(targets[1].contains(selector.getNextQuestion()));
	}
	
	@Test
	void returnNameResultFromResultTarget() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(3)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		assertEquals(targets[1].getName(), selector.getNameResult());
	}
	
	@Test
	void returnImageResultFromResultTarget() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(3)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		assertEquals(targets[1].getImage(), selector.getImageResult());
	}
	
	@Test
	void completeScenarioFirstTargetIsCorrect() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(3)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[1], targets[2], targets[3], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		String resultName = selector.getNameResult();
		
		while(selector.getNextQuestion() != null) {
			selector.answerWas(Selector.TRUE);
		}
		
		assertEquals(resultName, selector.getNameResult());
	}
	
	@Test
	void completeScenarioSecondTargetIsCorrect() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[1], targets[2], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		String notResultName = selector.getNameResult();
		
		selector.getNextQuestion();
		selector.answerWas(Selector.FALSE);
		
		while(selector.getNextQuestion() != null) {
			selector.answerWas(Selector.TRUE);
		}
		
		assertNotEquals(notResultName, selector.getNameResult());
	}
	
	@Test
	void completeScenarioThirdTargetIsCorrect() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(4)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], targets[3], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		String currentQuestion;
		while((currentQuestion = selector.getNextQuestion()) != null) {
			if(targets[2].contains(currentQuestion)) {
				selector.answerWas(Selector.TRUE);
			}else {
				selector.answerWas(Selector.FALSE);
			}
		}
		
		assertEquals(targets[2].getName(), selector.getNameResult());
	}
	
	@Test
	void completeScenarioFourthTargetIsCorrect() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(4)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], targets[3], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		String currentQuestion;
		while((currentQuestion = selector.getNextQuestion()) != null) {
			if(targets[3].contains(currentQuestion)) {
				selector.answerWas(Selector.TRUE);
			}else {
				selector.answerWas(Selector.FALSE);
			}
		}
		
		assertEquals(targets[3].getName(), selector.getNameResult());
	}
	
	//Cas limites :
	
	@Test
	void returnNullWhenNoneTargetsRemain() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Mockito.when(rep.getNextTarget()).thenReturn(null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		assertNull(selector.getNextQuestion());
	}
	
	@Test
	void completeScenarioMostCompleteTargetIsCorrect() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(2)).thenReturn(0);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[1], targets[4], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		String currentQuestion;
		while((currentQuestion = selector.getNextQuestion()) != null) {
			if(targets[4].contains(currentQuestion)) {
				selector.answerWas(Selector.TRUE);
			}else {
				selector.answerWas(Selector.FALSE);
			}
		}
		
		assertEquals(targets[4].getName(), selector.getNameResult());
	}
	
	@Test
	void completeScenarioMostCompleteTargetBeforeInListIsCorrect() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(2)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[4], targets[1], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		String currentQuestion;
		while((currentQuestion = selector.getNextQuestion()) != null) {
			if(targets[4].contains(currentQuestion)) {
				selector.answerWas(Selector.TRUE);
			}else {
				selector.answerWas(Selector.FALSE);
			}
		}
		
		assertEquals(targets[4].getName(), selector.getNameResult());
	}
	
	@Test
	void completeScenarioLessCompleteTargetBeforeInListIsCorrect() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(2)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[1], targets[4], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		String currentQuestion;
		while((currentQuestion = selector.getNextQuestion()) != null) {
			if(targets[1].contains(currentQuestion)) {
				selector.answerWas(Selector.TRUE);
			}else {
				selector.answerWas(Selector.FALSE);
			}
		}
		
		assertEquals(targets[1].getName(), selector.getNameResult());
	}
	
	@Test
	void completeScenarioMostCompleteTargetSquilliamIsCorrect() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(10)).thenReturn(6);
		Mockito.when(random.nextInt(4)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = BobEpongeData.getBobEpongeTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(
				targets[0], targets[1], targets[2], targets[3], targets[4], 
				targets[5], targets[6], targets[7], targets[8], targets[9], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		String currentQuestion;
		while((currentQuestion = selector.getNextQuestion()) != null) {
			if(targets[8].contains(currentQuestion)) {
				selector.answerWas(Selector.TRUE);
			}else {
				selector.answerWas(Selector.FALSE);
			}
		}
		
		assertEquals(targets[8].getName(), selector.getNameResult());
	}
	
	//Cas d'erreurs reconnues et traitées par le programme :
	
	@Test
	void completeScenarioTwoIdenticalTargets() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(getTargets()[1], targets[1], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		Selector selector = new Selector(handler);

		String currentQuestion;
		while((currentQuestion = selector.getNextQuestion()) != null) {
			if(targets[1].contains(currentQuestion)) {
				selector.answerWas(Selector.TRUE);
			}else {
				selector.answerWas(Selector.FALSE);
			}
		}
		
		assertEquals(targets[1].getName(), selector.getNameResult());
	}
}
