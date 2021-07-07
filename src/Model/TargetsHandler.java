package Model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import Data.TargetReader;

public class TargetsHandler {
	
	private List<Target> targets;
	private Random random;
	private Target currentTarget;

	public TargetsHandler(TargetReader rep, Random random) {
		targets = new LinkedList<Target>();
		Target target;
		rep.rewind();
		while((target = rep.getNextTarget()) != null) {
			targets.add(target);
		}
		this.random = random;
	}

	public void selectRandomTarget() {
		if(targets.size() > 0) {
			currentTarget = targets.get(random.nextInt(targets.size()));
		}else {
			currentTarget = null;
		}
	}
	
	public void selectRandomTargetWithout(Set<String> questionsToRemove) {
		if(targets.size() > 0) {
			selectRandomTarget();
			currentTarget = currentTarget.minus(questionsToRemove);
		}else {
			currentTarget = null;
		}
	}
	
	public void selectNextTargetWithout(Set<String> questionsToRemove) {
		if(targets.size() > 0) {
			int index = (targets.indexOf(currentTarget) + 1) % targets.size();
			currentTarget = targets.get(index).minus(questionsToRemove);
		}else {
			currentTarget = null;
		}
	}

	public void keepTargetsLike(Predicate<Target> predicate) {
		targets.removeIf(predicate.negate());
	}

	public boolean oneRemaining() {
		return targets.size() == 1;
	}
	
	public boolean isCurrentTargetExisting() {
		return currentTarget != null;
	}

	public Iterator<String> getCurrentTargetIterator() {
		return currentTarget.iterator();
	}
	
	public String getCurrentTargetName() {
		return currentTarget.getName();
	}
	
	public String getCurrentTargetImage() {
		return currentTarget.getImage();
	}
	/*
	public void removeRedundantQuestions() {
		List<Target> newTargets = new LinkedList<Target>();
		for(Target t1 : targets) {
			Target currentTarget = t1;
			for (Target t2 : targets) {
				if(!t1.equals(t2))
					currentTarget = currentTarget.removeAllQuestionsFrom(t2);
			}
			if(this.currentTarget.equals(t1)) this.currentTarget = currentTarget;
			newTargets.add(currentTarget);
		}
		targets = newTargets;
	}*/
}
