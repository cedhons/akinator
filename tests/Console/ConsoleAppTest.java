package Console;

import java.util.Random;
import java.util.Scanner;

import org.mockito.Mockito;

import Data.TargetReader;
import FakeData.BobEpongeData;
import Model.Selector;
import Model.Target;
import Model.TargetsHandler;

public class ConsoleAppTest {
	
	public static void main(String[] args) {
		TargetReader rep = Mockito.mock(TargetReader.class);
		Target[] targets = BobEpongeData.getBobEpongeTargets();
		Mockito.when(rep.getNextTarget()).thenReturn(
				targets[0], targets[1], targets[2], targets[3], targets[4], 
				targets[5], targets[6], targets[7], targets[8], targets[9], 
				targets[10], targets[11], null
				);
		TargetsHandler handler = new TargetsHandler(rep, new Random());
		Selector selector = new Selector(handler);
		
		Scanner scanner = new Scanner(System.in);
		String currentQuestion;
		System.out.println("--------AKINATOR BOB L'EPONGE--------");
		while((currentQuestion = selector.getNextQuestion()) != null) {
			//System.out.println("Avec : " + selector.getNameResult());
			System.out.print(currentQuestion + " (o/n/?) ");
			String answer = scanner.nextLine();
			
			if(answer.equals("o")) {
				selector.answerWas(Selector.TRUE);
			}else if(answer.equals("n")) {
				selector.answerWas(Selector.FALSE);
			}else {
				selector.answerWas(Selector.DO_NOT_KNOW);
			}
		}
		
		String result = selector.getNameResult() != null ? selector.getNameResult() : "Je ne sais pas";
		
		System.out.print("Vous pensez à " + result);
		scanner.close();
	}
}
