import Tools.ToolChallengeTwo;
import org.apache.jena.rdf.model.Model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    // URI les plus utilisés
    static String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns/";
    static String rdfs = "https://www.w3.org/2000/01/rdf-schema/";
    static String foaf = "http://xmlns.com/foaf/0.1/";

    // Fichiers & Query
    static String sparql1 = "./src/RessourcesQueries/sparql1.rq";
    static String sparql2 = "./src/RessourcesQueries/sparql2.rq";
    static String sparql3 = "./src/RessourcesQueries/sparql3.rq";
    static String sparql4 = "./src/RessourcesQueries/sparql4.rq";
    static String sparql5 = "./src/RessourcesQueries/sparql5.rq";
    static String sparql6 = "./src/RessourcesQueries/sparql6.rq";
    static String sparql7 = "./src/RessourcesQueries/sparql7.rq";

    /**
     * Méthode principale
     * @param args
     */
    public static void main(String args[]) throws Exception {
        System.out.println("Bonjour, import du modèle...");


        String pathEmployee ="Ontology/Hospital_Employe_Instantie.owl";
        String pathPatient ="Ontology/hopital_patient_Instantie.owl";
        String pathInsurance = "Ontology/Hopital_Insurance_Instancie.owl";
        String pathRecord = "Ontology/hopital_record_instantie.owl";
        String pathFusion = "Ontology/Fusion.owl";


        Model employeeModel = importmodel(pathEmployee);
        Model patientModel = importmodel(pathPatient);
        Model insuranceModel = importmodel(pathInsurance);
        Model recordModel = importmodel(pathRecord);
        Model fusedModel     = importmodel(pathFusion);

        System.out.println("... modèle importé !");


        System.out.println("Traîtement de votre requête...");


        // Le nom de requête.
        if(args.length>0) {
            String param0 = args[0];

            switch(param0){
                // Travail 2 Question 1
                case "employee" :
                    listEmployee(employeeModel);
                    break;
                // Travail 2 Question 2
                case "visit" :
                    if (args.length == 3){
                        countVisitOf(recordModel, args[1]);
                    } else {
                        System.out.println("Désolé, nous n'avons pas pu traîter votre requête");
                        System.out.println("Pour obtenir le nombre de visites d'un patient, veillez à bien introduire la commande \"visit\" suivit du prenom et du nom du patient");
                    }
                    break;
                // Travail 2 Question 3
                case "exam" :
                    if (args.length == 3){
                        whoExamOf(recordModel, args[1]);
                    } else {
                        System.out.println("Désolé, nous n'avons pas pu traîter votre requête");
                        System.out.println("Pour obtenir les examinateurs du patient, veillez à bien introduire la commande \"exam\" suivit du prenom et du nom du patient");
                    }
                    break;
                // Travail 2 Question 4
                case "illness" :
                    if (args.length == 3){
                        whichIllness(recordModel, args[1]);
                    } else {
                        System.out.println("Désolé, nous n'avons pas pu traîter votre requête");
                        System.out.println("Pour obtenir la maladie du patient, veillez à bien introduire la commande \"illness\" suivit du prenom et du nom du patient");
                    }
                    break;
                // Travail 2 Question 5
                case "apolicy" :
                    if (args.length == 3){
                        whichPolicy(patientModel, args[1]);
                    } else {
                        System.out.println("Désolé, nous n'avons pas pu traîter votre requête");
                        System.out.println("Pour obtenir l'assurance d'un patient, veillez à bien introduire la commande \"apolicy\" suivit du prenom et du nom du patient");
                    }
                    break;
                // Travail 2 Question 6
                case "people" :
                    listPeople(patientModel);
                    break;
                // Travail 2 Question 7
                case "ageover" :
                    if (args.length == 2){
                        int age = Integer.parseInt(args[1]);
                        listPoepleOver(patientModel, age);
                    } else {
                        System.out.println("Désolé, nous n'avons pas pu traîter votre requête");
                        System.out.println("Pour obtenir la liste des personnes au dessus d'un certain âge, veillez à bien entrer la commande \"ageover\" suivit de l'âge souahité");
                    }
                    break;
                default :
                    System.out.println("Désolé, le paramètre n'est pas reconnu...");
                    break;
            }

        } else {
            System.out.println("Veillez à ajouter un paramètre à votre commande." +
                    "\n Les paramètres sont soit : " +
                    "\n \t - employee" +
                    "\n \t - visit <valeur ID>" +
                    "\n \t - exam <valeur ID>" +
                    "\n \t - illness <valeur ID>" +
                    "\n \t - apolicy <valeur ID>" +
                    "\n \t - people" +
                    "\n \t - ageover <age>");
        }

        System.out.println("La réponse à la requête est stockée dans le dossier /out.");

    }


    public static Model importmodel(String path){
        Model model =  ToolChallengeTwo.lireRDF(path);
        /*
        System.out.println("Test d'une requête SPARQL avant de commencer...");
        String requestpath = "D:\\bri_e\\Documents\\GitHub\\TechnoWeb\\src\\RessourcesQueries\\tmp2.rq";
        ToolChallengeTwo.queryOnModel(model, requestpath, null, null);
        System.out.println("Requête sur le modèle instancié...");
        System.out.println("Requête réussie ! ");
         */
        return model;
    }

    public static void listEmployee(Model model){
        System.out.println("Liste des employés du modèle !");
        String request = importRequest(sparql1);
        ToolChallengeTwo.queryOnModel(model,request, null);

    }

    public static void countVisitOf(Model model, String id){
        System.out.println("Nombre de visites du patient " + id);
        ToolChallengeTwo.queryOnModel(model,sparql2, id);
    }

    public static void whoExamOf(Model model, String id){
        System.out.println("Examinateurs du patient " + id);
        ToolChallengeTwo.queryOnModel(model,sparql3, id);
    }

    public static void whichIllness(Model model, String id){
        System.out.println("Maladie du patient " + id);
        ToolChallengeTwo.queryOnModel(model,sparql4, id);

    }

    public static void whichPolicy(Model model, String id){
        System.out.println("Assurance de " + id);
        ToolChallengeTwo.queryOnModel(model,sparql5, id);

    }

    public static void listPeople(Model model){
        System.out.println("Liste des personnes du système");
        ToolChallengeTwo.queryOnModel(model,sparql6, null);

    }

    public static void listPoepleOver(Model model, int age){
        System.out.println("Personnes âgées de plus de " + age + " ans.");
        ToolChallengeTwo.queryOnModel(model,sparql7, String.valueOf(age));

    }

    public static String importRequest(String requestPath) {
        String qstr = null;
        try {
            qstr = new String(
                    Files.readAllBytes(Paths.get((requestPath))),
                    Charset.defaultCharset()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return qstr;
    }




}
