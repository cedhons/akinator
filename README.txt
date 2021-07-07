	
	----------------Configuration du projet----------------
	
	Si les images des personnages ou les banques de connaissances ne s'affichent pas,
	il est possible de modifier le chemin relatif des images ou des banques
	en chemin absolu en allant dans la classe PathSettings du package Program.
	
	Ainsi :
	public static final String IMAGE_DIRECTORY = "TargetImages";
	public static final String BANK_DIRECTORY = "KnowledgeBank";
	
	peut devenir par exemple :
	public static final String IMAGE_DIRECTORY = "D:/Akinator/TargetImages";
	public static final String BANK_DIRECTORY = "D:/Akinator/KnowledgeBank";



	----------------Optimisation des classes métier et choix des collections----------------

	1.  Choix de types de collections :
	
	-	Chaque personnages contient ces questions vrai dans une Set. 
		Grâce à la Set demander à un personnage si il contient une question, 
		est une opération réalisable en O(1). La Set est aussi adaptée aux opérations ensemblistes que j'effectue. 
		Par exemple faire la différence entre les questions du personnage et les question déjà posées, 
		ou encore faire une union entre les questions du personnage et des questions qui devrais y être ajouté. 
		Grâce à la Set je peux également éviter les doublons et ainsi ne pas avoir deux fois la même question 
		dans un personnage.
	-	J'ai mémorisé les personnages dans une List car j'ai besoin d'y accéder par index. 
		A chaque fois qu'on répond non à une question d'un personnage, 
		un nouveau personnage est choisi avec un index aléatoire dans la List. 
		Je n'utilise pas un tableau car je supprime au fur et à mesure les personnages qui ne 
		correspondent pas aux questions répondues.
	-	Je mémorise les réponses aux questions données par l'utilisateur dans une Map de Set. 
		J'ai une clé answeredTrue dans la Map pour conserver dans une Set les questions auxquelles on a 
		répondu oui et une clé answeredDoNotKnow pour conserver dans une Set les questions auxquelles on a 
		répondu je ne sais pas. J'ai choisis des Set pour pouvoir facilement effectuer les opérations ensemblistes 
		avec les questions contenues dans les personnages et également pour éviter les doublons.
	
	2.	Choix des implémentations de ces collections :
	
	-	Les questions sont enregistré dans les personnages grâce à une HashSet car les questions 
		n'ont pas besoin d'être triées et la HashSet est plus performante que la TreeSet. 
		La HashSet est performante pour les opérations ensembliste et pour vérifier si une question est contenue 
		ou non.
	-	Mes personnages sont mémorisés dans une LinkedList car cette List est plus performante pour 
		l'opération remove que l'ArrayList. Après chaque questions posées, je filtre les personnages et 
		supprime ceux qui ne correspondent pas. Je parcoure donc toute la liste, mais grâce à la LinkedList 
		lorsque je supprime un personnage, j'évite que l'opération de décalage soit coûteuse car elle s'effectue 
		en O(1). 
		Bien-sur la LinkedList est moins performante en ce qui concerne l'accès par index, 
		mais je l'ai quand-même préféré à l'ArrayList car je supprime plus souvent des personnages que je n'en 
		choisi par index.
	-	La Map des réponses aux questions données par l'utilisateur est une HashMap qui contient deux HashSet. 
		J'ai choisi la HashMap pour ses performances pour trouver une clé et car je n'ai pas besoin de trier. 
		J'ai choisi la HashSet pour ses performances et car les questions n'ont pas besoin d'être triées.
	
	3.	Utilisation correcte des collections et de leurs méthodes :
	
	-	Pour les questions enregistrée dans la Set j'utilise la méthodes contains pour vérifier si une question est 
		bien présente ou non. Pour supprimer les questions déjà posé j'utilise la méthode removeAll et pour ajouter 
		des questions en plus à ce personnage j'utilise ma méthode addAll. 
		J'utilise aussi l'Iterator pour poser les questions d'un personnage.
	-	Pour les personnages enregistré dans la List, j'utilise la méthode get pour récupèrer un personnage 
		par son index et la méthode removeIf pour supprimer tous les personnage qui 
		correspondent au prédicat passé en argument.
	-	Pour les réponses aux questions données par l'utilisateur, j'utilise les métodes put et get de 
		la map et pour la Set des questions j'utilise la méthode add pour en ajouter, 
		sinon je l'utilise aussi beaucoup comme paramètre pour les opérations ensemblistes.
	
	4.	Evaluation de la CTT des principaux algorithmes :
	
	-	Pour la partie concernant le choix d'une question à poser au joueur, 
		la complexité temporelle théorique est dans le pire des cas en O(N) par rapport à la liste des personnages. 
		D'abord on choisi un index au hasard pour récupérer un personnage grâce à get qui est dans la LinkedList 
		en O(N), 
		puis on renvoie une question contenu dans le personnage. 
		Il peut aussi être dans le meilleur des cas en O(1) lorsque le personnage ne doit pas être changé car 
		il suffit de poser la question suivante dans le personnage.
	-	Pour la partie prise en compte de la réponse, la complexité temporelle théorique est en O(N) ou N est 
		le nombre 
		d'éléments dans la liste des personnages. Je parcoure tous les personnage pour voir si ils contiennent 
		ou non une 
		question et en fonction de la réponse de l'utilisateur, si la réponse était oui je supprime tout 
		les personnage qui ne contiennent pas la question, si la réponse était non je supprime tous les 
		personnages qui contiennent la question. 
		O(N) pour le parcour de tous les personnages multiplié par O(1) pour l'opération remove de la LinkedList, 
		ce qui fait O(N).
	-	L'ajout d'un personnage dans la Map qui se trouve dans la classe JsonTargetFileHandler 
		du package Data s'effectue 
		en O(1) dans le meilleur des cas car il suffit de l'ajouter dans la map. Dans le pire des cas 
		il sera en O(N) où N est le nombre de personnages dans la Map si en fonction du Hashcode plusieurs 
		personnage sont mémorisé dans la même List à une case de la HashMap. 
		Dans ce cas il y a d'abord un parcourt de toute cette List 
		pour vérifier si la nouvelle clé n'est pas déjà présente.
		
	5. 	Dans les classes qui contiennent des collections, celles-ci sont adéquatement utilisées:
	
	-	déclaration d'interfaces et non de classes concrètes :

			Atrribut de la classe Target (représente le personnage) : 
				private final Set<String> questions;
			
			Signature de la méthode de fabrique de la classe Target : 
				public static Target create(String name, String image, Set<String> questions);
			
			Attribut de la classe TargetsHandler (gère toutes les Targets dans une List) : 
				private List<Target> targets;
	
	-	pas de reprogrammation de méthodes existantes :

		public void selectRandomTarget() {
			if(targets.size() > 0) {
				currentTarget = targets.get(random.nextInt(targets.size()));
			}else {
				currentTarget = null;
			}
		}
		
		Je n'ai pas trouvé de méthodes permettant de récupérer un élément au hasard dans la List
		
		public Target getNextTargetWithout(Set<String> questionsToRemove) {
		  if(targets.size() > 0) {
		    int index = (targets.indexOf(LastTargetReturned) + 1) % targets.size();
		    LastTargetReturned = targets.get(index);
		    return LastTargetReturned.minus(questionsToRemove);
		  }
		  return null;
		}
		
		Je n'ai pas trouvé de méthodes permettant de récupérer un élément suivant un autre dans la List
		
		public boolean oneRemaining() {
		  return targets.size() == 1;
		}
		
		Je n'ai pas trouvé de méthodes permettant de savoir si un élément est restant



 	----------------BUILDER----------------
 	
 	Le problème identifié :

	Pour construire un objet de type Target (représente un personnage, un objet, ... à deviner) 
	il faut d'abord construire une Set dans laquelle on ajoute des questions, 
	ensuite on doit la passer au constructeur de la Target en plus de son nom et de l'url de son image. 
	Le code de création de la Target n'est donc pas forcément explicite ni agréable à lire et écrire. 
	Je pense en particulier pour les tests dans lesquels il faut créer des Targets de tests. 
	Dans les tests je ne passe pas d'images aux Targets lorsque je n'en ai pas besoin, 
	je laisse donc le paramètre dans le constructeur à null, ce qui n'est pas très séduisant, 
	je pourrais bien entendu surcharger le constructeur, 
	mais je n'aime pas rajouter des éléments dans mes classes métier uniquement pour les tests.
	
	Les principes du design pattern choisi :
	
	Le Builder permet de créer un objet étapes par étapes en séparant sa construction de sa représentation. 
	Il est surtout utile lorsque les objets sont complexe à construire 
	(si ils ont besoin de beaucoup de données ou de structures de données complexes). 
	Il permet de décomposer et de simplifier la construction d'un objet grâce à des méthodes 
	et des nom de méthodes bien choisis. Le builder est créé avec une interface qui décrit la manière de créer 
	un objet, une classe concrète implémente l'interface pour construire cet objet et le retourner. 
	L'interface du builder est souvent une "Fluent interface" 
	qui permet le chainage de méthodes car chaque méthodes de construction renvoie la référence de 
	l'objet à partir duquel elle est appelée. On peut aussi ignorer l'appel de certaine fonction du 
	builder lorsqu'on en a pas besoin pour construire notre objet final.
	
	En quoi il permet bien de résoudre le problème en question :
	
	Grâce au builder la construction d'une Target est simplifiée et plus explicite. 
	De plus je peux ignorer l'ajout d'une image si je n'en ai pas besoin. 
	Pour construire une Target je peux maintenant construire son builder 
	sans devoir lui donner de paramètres, avec withName(string) je lui donne un nom, 
	avec withImage(string) je lui donne une image et avec addQuestion(string) je lui ajoute une question. 
	Je peux maintenant faire abstraction de la construction de la Set.
	
	
	
	----------------ADAPTER----------------
	
	Le problème identifié :

	Il pourrait y avoir plusieurs façon d'enregistrer et de récupérer une banque de connaissance, 
	par exemple, les Targets peuvent être mémorisée dans un fichiers binaire, json ou encore xml. 
	Ma classe TargetsHandler a besoin de récupérer les Targets de la banque de connaissance, 
	pour cela je voulais passer un objet de type TargetReader à son constructeur qui permet 
	de le lier avec la banque de connaissance. Ainsi TargetHandler peu récupérer des Targets comme il le souhaite, 
	toutes à la fois ou on pourrais imaginer si il y avait une trop grosse banque de connaissance qu'il 
	ne les récupérerais pas toutes à la fois. Le problème est qu'au début du projet, je ne savais pas sous 
	quel format serait enregistré les données, je ne savais donc pas quel TargetReader passer à TargetsHandler 
	car TargetReader dépend directement de la manière dont sont enregistré les données. 
	Pour mes tests cela posait également problème car il me fallait pouvoir créer des données 
	factices sans devoir passer par un fichier et donc passer par le TargetReader ne fonctionne 
	pas car il ne gère que une banque de connaissance enregistrée dans un fichier.

	Les principes du design pattern choisi :

	Adapter consiste en une interface décrivant certains comportements. Cette interface est implémentée
	par une classe appelée Adapteur, cette classe implémente les comportements décrits par l'interface
	en les traduisant à une ou plusieurs classe appelées Adaptées. 
	Le client ne connait que l'interface et lorsqu'il appelle une méthode de celle-ci, l'adapteur qui ce
	cache derrière va manipuler un ou plusieurs adaptés pour contribuer au besoin.
	

	En quoi il permet bien de résoudre le problème en question :

	Grâce à adapter je peux maintenant faire abstraction de la manière dont sont enregistré les Targets. 
	J'ai une interface TargetReader qui défini comment on peut récupérer des données et son implémentation 
	qui dépend de la manière dont sont enregistré les Targets en adaptants les classes nécessaires.
	Par exemple, la classe JsonTargetFileHandler qui implémente TargetReader adapte 
	des classe de la librairie Javax.json. TargetsHandler utilise et reçois dans son constructeur 
	l'interface TargetReader, on peut ainsi lui donner n'importe quelle implémentation de l'interface. 
	Par exemple une implémentation qui travaille avec un fichier xml, une avec un fichier json,... 
	En plus d'être plus adaptatif en pouvant éventuellement prendre en compte plusieurs types de banques 
	de connaissances, je n'ai pas eu besoin de me soucier de la manière dont serait enregistré mes Target 
	pendant l'élaboration de mes classes métier. Je peux maintenant également fournir une implémentation 
	uniquement pour les tests, ce que j'avais fait au début, même si après j'ai utilisé mockito 
	pour mocker TargetReader.