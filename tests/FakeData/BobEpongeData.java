package FakeData;

import Model.Target;
import Model.TargetBuilder;

public class BobEpongeData {

	public static Target getBob() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target bob = targetBuilder
				.withName("Bob")
				.addQuestion("Est-il jaune ?")
				.addQuestion("Il a un nez ?")
				.addQuestion("A-t-il une cravatte ?")
				.addQuestion("A-t-il un pantalon ?")
				.addQuestion("Est-il petit ?")
				.addQuestion("Est-il une éponge ?")
				.addQuestion("Est-il cuisinier ?").build();
		return bob;
	}
	
	public static Target getCarlo() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target carlo = targetBuilder
				.withName("Carlo")
				.addQuestion("Est-il gris bleu ?")
				.addQuestion("Il a un nez ?")
				.addQuestion("A-t-il un gros nez ?")
				.addQuestion("Joue un instrument ?")
				.addQuestion("A-t-il des tentacules ?").build();
		return carlo;
	}
	
	public static Target getSquilliam() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target squilliam = targetBuilder
				.withName("Squilliam")
				.addQuestion("Est-il gris bleu ?")
				.addQuestion("Il a un nez ?")
				.addQuestion("A-t-il un gros nez ?")
				.addQuestion("Joue un instrument ?")
				.addQuestion("A-t-il des tentacules ?")
				.addQuestion("A-t-il un monosourcil ?").build();
		return squilliam;
	}
	
	public static Target getPatrick() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target patrick = targetBuilder
				.withName("Patrick")
				.addQuestion("Est-il rose ?")
				.addQuestion("A-t-il un pantalon ?")
				.addQuestion("Est-il une étoile de mer ?")
				.addQuestion("Vit-il sous une pierre ?").build();
		return patrick;
	}
	
	public static Target getKrabs() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target krabs = targetBuilder
				.withName("Krabs")
				.addQuestion("Est-il rouge ?")
				.addQuestion("Il a un nez ?")
				.addQuestion("A-t-il un pantalon ?")
				.addQuestion("Est-il radin ?")
				.addQuestion("A-t-il des pinces ?")
				.addQuestion("Est-il chef ?").build();
		return krabs;
	}
	
	public static Target getLary() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target lary = targetBuilder
				.withName("Lary")
				.addQuestion("Est-il rouge ?")
				.addQuestion("Il a un nez ?")
				.addQuestion("A-t-il un pantalon ?")
				.addQuestion("Est-il musclé ?")
				.addQuestion("A-t-il des pinces ?")
				.addQuestion("Est-il charismatique ?").build();
		return lary;
	}
	
	public static Target getPlankton() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target plankton = targetBuilder
				.withName("Plankton")
				.addQuestion("Est-il vert ?")
				.addQuestion("Est-il petit ?")
				.addQuestion("Est-il un voleur ?")
				.addQuestion("Est-il l'ennemi de Krabs ?").build();
		return plankton;
	}
	
	public static Target getGary() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target gary = targetBuilder
				.withName("Gary")
				.addQuestion("Est-il rose ?")
				.addQuestion("Est-il petit ?")
				.addQuestion("Est-il un animal de compagnie ?")
				.addQuestion("Est-il un escargot ?").build();
		return gary;
	}
	
	public static Target getSandy() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target sandy = targetBuilder
				.withName("Sandy")
				.addQuestion("Est-il brun ?")
				.addQuestion("A-t-il des poils ?")
				.addQuestion("Est-elle une fille ?")
				.addQuestion("Il a un nez ?")
				.addQuestion("A-t-il un pantalon ?")
				.addQuestion("Porte-t-il un scafandre ?")
				.addQuestion("Est-il un écureuil ?").build();
		return sandy;
	}
	
	public static Target getPearl() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target pearl = targetBuilder
				.withName("Pearl")
				.addQuestion("Est-il gris bleu ?")
				.addQuestion("Est-elle une fille ?")
				.addQuestion("Est-elle la fille de Krabs ?")
				.addQuestion("Est-il une baleine ?").build();
		return pearl;
	}
	
	public static Target getPuff() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target puff = targetBuilder
				.withName("Puff")
				.addQuestion("Est-elle une fille ?")
				.addQuestion("Est-elle prof d'auto-école ?")
				.addQuestion("Grossit-elle souvent ?").build();
		return puff;
	}
	
	private static Target getKaren() {
		TargetBuilder targetBuilder = new TargetBuilder();
		Target karen = targetBuilder
				.withName("Karen")
				.addQuestion("Est-elle une fille ?")
				.addQuestion("Est-il un robot ?")
				.addQuestion("Est-elle la femme de Plankton ?").build();
		return karen;
	}
	
	public static Target[] getBobEpongeTargets() {
		return new Target[] {
				getBob(),getPatrick(),getCarlo(),getKrabs(),getPlankton(),getGary(),
				getSandy(),getPearl(),getSquilliam(),getPuff(),getLary(),getKaren()
		};
	}
}
