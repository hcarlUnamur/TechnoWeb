package Tools;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ToolChallengeTwo {


    public static void creerRDF(){
        // faire un model avec ma vision

        String URI1 = "http://somewhere/URI1";
        String literal = "Quentin";
        String ns = "http://www.example.org#";
        // créer un model vide
        Model model = ModelFactory.createDefaultModel();

        // pas obligé mais c'est pour avoir un controle sur les raccourcis
        model.setNsPrefix("per", ns);

        // Créer une ressource
        Resource ressource1 = model.createResource(URI1);

        // Créer une propriété
        Property proName = model.createProperty("URI");

        // ajouter à une ressource une autre ressource ou un litéral
        ressource1.addProperty(RDFS.subClassOf, ressource1);
        ressource1.addLiteral(FOAF.name, literal);



        //Imprimer le model e
        // Format d'écriture "N-TRIPLES" ,  "TURTLE" , "RDF/XML"
        //model.write(System.out, "TURTLE");

        // Ecrire le modèle dans un fichier
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("src\\output\\outPutMoi.xml", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.write(writer);
        if (writer != null) {
            writer.close();
        }
    }

    public static void makeModelProf() {
        // Faire un model avec la vision donnée en cours

        Model model = null;
        model = createModel(model);
        writeModel(model);
    }

    public static Model createModel(Model model){
        model = ModelFactory.createDefaultModel();
        String ns = "http://www.example.org#";

        //set a namespace
        model.setNsPrefix("per", ns);

        addStatement(model, ns + "Student", RDFS.subClassOf, ns + "Person");

        // addStatement(model, ns + "prof1", FOAF.age, ns + "50");
        RDFNode undergraduate = model.createResource(ns + "Undergraduate");
        RDFNode professor = model.createResource(ns + "Professor");
        Resource alice = model.createResource()
                .addProperty(RDF.type, undergraduate)
                .addLiteral(FOAF.name, "Alice")
                .addLiteral(FOAF.age, 36);
        Resource bob = model.createResource(ns + "prof1")
                .addProperty(RDF.type, professor)
                .addLiteral(FOAF.name, "Bob")
                .addLiteral(FOAF.age, 50);

        return model;
    }



    public static void addStatement(Model model, String s, Property predicate, String o){
        Resource subject = model.createResource(s);
        RDFNode object = model.createResource(o);
        Statement stmt = model.createStatement(subject, predicate, object);
        model.add(stmt);
    }

    public void addStatement(Model model, String s, String p, String o){
        Resource subject = model.createResource(s);
        Property predicate = model.createProperty(p);
        RDFNode object = model.createResource(o);
        Statement stmt = model.createStatement(subject, predicate, object);
        model.add(stmt);
    }

    public static void writeModel(Model model){
        model.write(System.out, "TTL");

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("src\\output\\outPutProf.xml", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.write(writer);
        if (writer != null) {
            writer.close();
        }
    }



    private static void queryServeur( String s1) {
        // faire une requète sur un serveur

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(s1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String querytest =  stringBuilder.toString();

        //Query query = QueryFactory.create(sparqlQueryString1);
        Query query = QueryFactory.create(querytest);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

        ResultSet results = qexec.execSelect();
        ResultSetFormatter.out(System.out, results, query);

        qexec.close() ;
    }


    public static void queryOnModel(Model model, String s1, String firstParam, String secondParam){
        // faire des requètes sur un model (s2) à partir d'un fichier (s1)

        /*
        ==> Modèle parsing HIST
        Model model = ModelFactory.createDefaultModel() ;
        //model.read("src/main/resources/ex2.ttl", "TURTLE") ;
        model.read(s2, "RDFXML") ;
        */


        String qstr = null;
        try {
            qstr = new String(
                    Files.readAllBytes(Paths.get((s1))),
                    Charset.defaultCharset()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(secondParam!=null){
            qstr.replaceAll("_surname_", firstParam);
            qstr.replaceAll("_name_", secondParam);
        } else {
            if(firstParam!=null){
                qstr.replaceAll("_age_", firstParam);
            }
        }




        Query q = QueryFactory.create(qstr);




        QueryExecution qexec = QueryExecutionFactory.create(q, model);

        ResultSet results = qexec.execSelect();
        ResultSetFormatter.out(System.out, results, q);


    }

    public static Model lireRDF(String path) {

        Model modelLire = ModelFactory.createDefaultModel();
        //model.read("src/main/resources/ex2.ttl", "TURTLE") ;
        modelLire.read(path, "TTL");

        //modelLire.write(System.out);

        return modelLire;

    }

}
