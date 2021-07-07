package Model;

import java.util.HashSet;
import java.util.Set;

public class TargetBuilder {
	
	private String name;
	private String image;
	private Set<String> questions;
	
	public TargetBuilder() {
		questions = new HashSet<String>();
	}

	public TargetBuilder withName(String name) {
		this.name = name;		
		return this;
	}
	
	public TargetBuilder withImage(String image) {
		this.image = image;		
		return this;
	}
	
	public TargetBuilder addQuestion(String question) {
		this.questions.add(question);	
		return this;
	}
	
	public Target build() {
		return Target.create(name, image, questions);
	}
}
