import Tools.ToolChallengeTwo;
import org.apache.jena.rdf.model.Model;

public class Main {

    // URI les plus utilisés
    static String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns/";
    static String rdfs = "https://www.w3.org/2000/01/rdf-schema/";
    static String foaf = "http://xmlns.com/foaf/0.1/";

    // Fichiers & Query
    static String sparql1 = "RessourcesQueries/sparql1.rq";
    static String sparql2 = "RessourcesQueries/sparql2.rq";
    static String sparql3 = "RessourcesQueries/sparql3.rq";
    static String sparql4 = "RessourcesQueries/sparql4.rq";
    static String sparql5 = "RessourcesQueries/sparql5.rq";
    static String sparql6 = "RessourcesQueries/sparql6.rq";
    static String sparql7 = "RessourcesQueries/sparql7.rq";

    /**
     * Méthode principale
     * @param args
     */
    public static void main(String args[]) throws Exception {
        System.out.println("Bonjour, import du modèle...");

        Model model = importmodels();

        System.out.println("... modèle importé !");


        System.out.println("Traîtement de votre requête...");


        // Le nom de requête.
        if(args.length>0) {
            String param0 = args[0];

            switch(param0){
                // Travail 2 Question 1
                case "employee" :
                    listEmployee(model);
                    break;
                // Travail 2 Question 2
                case "visit" :
                    if (args.length == 3){
                        countVisitOf(model, args[2], args[1]);
                    } else {
                        System.out.println("Désolé, nous n'avons pas pu traîter votre requête");
                        System.out.println("Pour obtenir le nombre de visites d'un patient, veillez à bien introduire la commande \"visit\" suivit du prenom et du nom du patient");
                    }
                    break;
                // Travail 2 Question 3
                case "exam" :
                    if (args.length == 3){
                        whoExamOf(model, args[2], args[1]);
                    } else {
                        System.out.println("Désolé, nous n'avons pas pu traîter votre requête");
                        System.out.println("Pour obtenir les examinateurs du patient, veillez à bien introduire la commande \"exam\" suivit du prenom et du nom du patient");
                    }
                    break;
                // Travail 2 Question 4
                case "illness" :
                    if (args.length == 3){
                        whichIllness(model, args[2], args[1]);
                    } else {
                        System.out.println("Désolé, nous n'avons pas pu traîter votre requête");
                        System.out.println("Pour obtenir la maladie du patient, veillez à bien introduire la commande \"illness\" suivit du prenom et du nom du patient");
                    }
                    break;
                // Travail 2 Question 5
                case "apolicy" :
                    if (args.length == 3){
                        whichPolicy(model, args[2], args[1]);
                    } else {
                        System.out.println("Désolé, nous n'avons pas pu traîter votre requête");
                        System.out.println("Pour obtenir l'assurance d'un patient, veillez à bien introduire la commande \"apolicy\" suivit du prenom et du nom du patient");
                    }
                    break;
                // Travail 2 Question 6
                case "people" :
                    listPeople(model);
                    break;
                // Travail 2 Question 7
                case "ageover" :
                    if (args.length == 2){
                        int age = Integer.parseInt(args[1]);
                        listPoepleOver(model, age);
                    } else {
                        System.out.println("Désolé, nous n'avons pas pu traîter votre requête");
                        System.out.println("Pour obtenir la liste des personnes au dessus d'un certain âge, veillez à bien entrer la commande \"ageover\" suivit de l'âge souahité");
                    }
                    break;
                default :
                    System.out.println("Error : Unrecognized argument.");
                    break;
            }

        } else {
            throw new Exception("Argument Missing");
        }

    }


    public static Model importmodels(){
        String path ="Ontology/Hospital_Employe_Instantie.owl";
        Model model =  ToolChallengeTwo.lireRDF(path);

        System.out.println("Test d'une requête SPARQL avant de commencer...");


        System.out.println("Requête sur le modèle instancié...");

        System.out.println("Requête réussie ! ");

        return model;
    }

    public static void listEmployee(Model model){
        System.out.println("Liste des employés du modèle !");

        ToolChallengeTwo.queryOnModel(model,sparql1);

    }

    public static void countVisitOf(Model model, String name, String surname){
        System.out.println("Nombre de visites de " + surname + " " + name);

        //TODO : alterate query
        ToolChallengeTwo.queryOnModel(model,sparql2);


    }

    public static void whoExamOf(Model model, String name, String surname){
        System.out.println("Examinateurs de " + surname + " " + name);

        //TODO : alterate query
        ToolChallengeTwo.queryOnModel(model,sparql3);

    }

    public static void whichIllness(Model model, String name, String surname){
        System.out.println("Maladie de " + surname + " " + name);


        //TODO : alterate query
        ToolChallengeTwo.queryOnModel(model,sparql4);

    }

    public static void whichPolicy(Model model, String name, String surname){
        System.out.println("Assurance de " + surname + " " + name);


        //TODO : alterate query
        ToolChallengeTwo.queryOnModel(model,sparql5);

    }

    public static void listPeople(Model model){
        System.out.println("Liste des personnes du système");

        ToolChallengeTwo.queryOnModel(model,sparql6);

    }

    public static void listPoepleOver(Model model, int age){
        System.out.println("Personnes âgées de plus de " + age + " ans.");


        //TODO : alterate query
        ToolChallengeTwo.queryOnModel(model,sparql7);

    }




}
