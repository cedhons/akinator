package Model;

import static FakeData.FakeData.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import Data.TargetReader;
import Model.Target;
import Model.TargetsHandler;

class HandleTargetsTest {
	
	
	@Test
	void returnARandomTargetExtractedFromTheRepository() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(2)).thenReturn(0);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		
		handler.selectRandomTarget();
		assertTrue(handler.getCurrentTargetName().equals(targets[0].getName()));
		
		handler.selectRandomTargetWithout(new HashSet<String>());
		assertTrue(handler.getCurrentTargetName().equals(targets[0].getName()));
	}
	
	@Test
	void returnNextTargetFromRandomTargetExtractedFromTheRepository() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(3)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		
		handler.selectRandomTarget();
		handler.selectNextTargetWithout(new HashSet<String>());
		assertEquals(targets[2].getName(), handler.getCurrentTargetName());
	}
	
	@Test
	void returnFirstTargetWhenAskNextTargetFromNoOther() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(3)).thenReturn(1);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], null);
		TargetsHandler handler = new TargetsHandler(rep, random);

		handler.selectNextTargetWithout(new HashSet<String>());
		assertEquals(targets[0].getName(), handler.getCurrentTargetName());
	}
	
	@Test
	void returnFirstTargetWhenAskNextTargetFromLastTarget() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(3)).thenReturn(2);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		
		handler.selectRandomTarget();
		handler.selectNextTargetWithout(new HashSet<String>());
		assertEquals(targets[0].getName(), handler.getCurrentTargetName());
	}
	
	@Test
	void returnNextTargetAfterFilter() {
		Random random = Mockito.mock(Random.class);
		Mockito.when(random.nextInt(4)).thenReturn(2);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], targets[3], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		
		handler.selectRandomTarget();
		handler.keepTargetsLike(t -> !t.contains("question1"));
		handler.selectNextTargetWithout(new HashSet<String>());
		assertEquals(targets[3].getName(), handler.getCurrentTargetName());
	}
	
	@Test
	void returnNullAfterAskedNextTargetWhenEmptyRepository() {
		Random random = Mockito.mock(Random.class);
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Mockito.when(rep.getNextTarget()).thenReturn(null);
		TargetsHandler handler = new TargetsHandler(rep, random);

		handler.selectNextTargetWithout(new HashSet<String>());
		assertFalse(handler.isCurrentTargetExisting());
	}
	
	@Test
	void returnTheExpectedTargetAfterKeepingSpecificTargets() {
		Random random = new Random();
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		
		handler.keepTargetsLike(t -> !t.contains("question4"));
		
		handler.selectRandomTarget();
		assertEquals(targets[0].getName(), handler.getCurrentTargetName());
		
		handler.selectRandomTargetWithout(new HashSet<String>());
		assertEquals(targets[0].getName(), handler.getCurrentTargetName());
	}
	
	@Test
	void returnNullAfterKeepingAllTargets() {
		Random random = new Random();
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[1], targets[2], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		
		handler.keepTargetsLike(t -> !t.contains("question4"));
		
		handler.selectRandomTarget();
		assertFalse(handler.isCurrentTargetExisting());
		
		handler.selectRandomTargetWithout(new HashSet<String>());
		assertFalse(handler.isCurrentTargetExisting());
	}
	
	@Test
	void returnTargetWithoutGivenQuestions() {
		Random random = new Random();
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[1], new Target[] {null});
		TargetsHandler handler = new TargetsHandler(rep, random);
		
		handler.selectRandomTargetWithout(getQuestionsContainsTwoFrom2());
		Iterator<String> questionsIterator = handler.getCurrentTargetIterator();
		assertEquals("question4", questionsIterator.next());
		assertFalse(questionsIterator.hasNext());
	}
	
	@Test
	void returnTrueWhenAskingOneRemaining() {
		Random random = new Random();
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		
		handler.keepTargetsLike(t -> !t.contains("question4"));
		assertTrue(handler.oneRemaining());
	}
	
	@Test
	void returnFalseWhenAskingOneRemaining() {
		Random random = new Random();
		
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = getTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(targets[0], targets[1], targets[2], targets[3], null);
		TargetsHandler handler = new TargetsHandler(rep, random);
		
		assertFalse(handler.oneRemaining());
	}
	
}
