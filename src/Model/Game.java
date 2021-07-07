package Model;

import java.util.Set;

public interface Game {
	
	public static final byte TRUE = 1;
	public static final byte FALSE = 0;
	public static final byte DO_NOT_KNOW = -1;

	String getNextQuestion();

	void answerWas(byte answer);

	String getNameResult();

	String getImageResult();

	Set<String> getAnsweredTrueQuestions();
}
