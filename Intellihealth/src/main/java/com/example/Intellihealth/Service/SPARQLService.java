package com.example.Intellihealth.Service;

import com.example.Intellihealth.model.HealthDataDTO;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SPARQLService {

    //private final Dataset fusekiDataset;
    @Value("${fuseki.endpoint.url}")
    private String fusekiEndpointUrl;
    public static final String PREFIXES =
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX ontology: <https://example.com/ontology/>\n" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";

    public List<Integer> buildCustomQuery(HealthDataDTO healthData) {
        String sparqlCopdQuery = buildCopdQuery(healthData);
        String sparqlCovidQuery = buildCovidQuery(healthData);
        String sparqlCardioQuery = buildCardioQuery(healthData);
        String sparqlGenericQuery = buildGenericQuery(healthData);


        System.out.println(sparqlCopdQuery + "\n");
        System.out.println(sparqlCovidQuery+ "\n");
        System.out.println(sparqlCardioQuery+ "\n");
        System.out.println(sparqlGenericQuery+ "\n");

        List<Integer> countList=new ArrayList<Integer>();

        countList.add(executeCustomQuery(sparqlCopdQuery));
        countList.add(executeCustomQuery(sparqlCovidQuery));
        countList.add(executeCustomQuery(sparqlCardioQuery));
        List<Integer> generic_list = executeGenericQuery(sparqlGenericQuery);
        countList.add(generic_list.get(0));
        countList.add(generic_list.get(1));

        return countList;
    }

    private int executeCustomQuery(String sparqlQuery) {
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(fusekiEndpointUrl, QueryFactory.create(sparqlQuery))) {
            ResultSet results = qexec.execSelect();
            if (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                return soln.getLiteral("userCount").getInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private List<Integer> executeGenericQuery(String sparqlQuery) {
        List<Integer> userCounts = new ArrayList<>();

        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(fusekiEndpointUrl, QueryFactory.create(sparqlQuery))) {
            ResultSet results = qexec.execSelect();

            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Literal userCountLiteral = soln.getLiteral("userCount");

                if (userCountLiteral != null) {
                    int userCount = userCountLiteral.getInt();
                    userCounts.add(userCount);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userCounts;
    }


    public static String buildCopdQuery(HealthDataDTO healthData)
    {
        return PREFIXES +
                "\nSELECT (COUNT(?user) AS ?userCount)\n" +
                "WHERE {\n" +
                "  ?user rdf:type ontology:COPDpatient;\n" +
                "    ontology:hasAge ?userAge;\n" +
                "    ontology:hasBloodPressure ?userBloodPressure;\n" +
                "    ontology:hasDiabetes ?userDiabetes;\n" +
                "    ontology:hasGender ?userGender;\n" +
                "    ontology:hasMuscularProblems ?userMuscularProblems;\n" +
                "    ontology:isSmoker ?userSmoker.\n" +
                "  FILTER(\n" +
                "    xsd:integer(?userAge) >= " + healthData.getAge() + " && xsd:integer(?userAge) <= " + Integer.toString(Integer.parseInt(healthData.getAge()) + 10) + " &&\n" +
                "    ?userBloodPressure = \"" + healthData.getBloodPressure() + "\" &&\n" +
                "    ?userDiabetes = \"" + healthData.getDiabetes() + "\" &&\n" +
                "    ?userGender = \"" + healthData.getGender() + "\" &&\n" +
                "    ?userMuscularProblems = \"" + healthData.getMuscularProblems() + "\" &&\n" +
                "    ?userSmoker = \"" + healthData.getSmoke() + "\"\n" +
                "  )\n" +
                "}";
    }

    public static String buildGenericQuery(HealthDataDTO healthData)
    {
        return PREFIXES + "\nPREFIX onto: <http://www.ontotext.com/>"+
                "\nSELECT (COUNT(?user) AS ?userCount)\n" +
                "WHERE {\n" +
                "?user\n" +
                "    ontology:hasAge ?userAge;\n" +
                "    ontology:hasBloodPressure ?userBloodPressure;\n" +
                "    ontology:hasDiabetes ?userDiabetes;\n" +
                "    ontology:hasGender ?userGender;\n" +
                "    ontology:isSmoker ?userSmoker.\n" +
                "  FILTER(\n" +
                "    xsd:integer(?userAge) >= " + "10" + " && xsd:integer(?userAge) <= " + "100" + " &&\n" +
                "    (?userGender = \"Male\" || ?userGender = \"Female\"))\n" +
                "}\n" +
                "GROUP BY ?userGender";
    }

    private String buildCardioQuery(HealthDataDTO healthData) {
        return PREFIXES + "\nPREFIX onto: <https://example.com/ontology/#>"+
                "\nSELECT (COUNT(?user) AS ?userCount)\n" +
                "WHERE {\n" +
                "  ?user rdf:type onto:CardiovascularPatient;\n" +
                "    ontology:hasAge ?userAge;\n" +
                "    ontology:hasBloodPressure ?userBloodPressure;\n" +
                "    ontology:hasDiabetes ?userDiabetes;\n" +
                "    ontology:hasGender ?userGender;\n" +
                "    ontology:isSmoker ?userIsSmoker.\n" +
                "  FILTER(\n" +
                "    xsd:integer(?userAge) >= " + healthData.getAge() + " && xsd:integer(?userAge) <= " + Integer.toString(Integer.parseInt(healthData.getAge()) + 10) + " &&\n" +
                "    ?userBloodPressure = \"" + healthData.getBloodPressure() + "\" &&\n" +
                "    ?userDiabetes = \"" + healthData.getDiabetes() + "\" &&\n" +
                "    ?userGender = \"" + healthData.getGender() + "\" &&\n" +
                "    ?userIsSmoker = \"" + healthData.getSmoke() + "\"\n" +
                "  )\n" +
                "}";
    }

    private static String buildCovidQuery(HealthDataDTO healthData) {
        return PREFIXES + "\nPREFIX onto: <https://example.com/ontology/#>"+
                "\nSELECT (COUNT(?user) AS ?userCount)\n" +
                "WHERE {\n" +
                "  ?user rdf:type onto:CovidPatient;\n" +
                "    ontology:hasAge ?userAge;\n" +
                "    ontology:hasBloodPressure ?userBloodPressure;\n" +
                "    ontology:hasDiabetes ?userDiabetes;\n" +
                "    ontology:hasGender ?userGender;\n" +
                "    ontology:hasPneumonia ?userPneumonia.\n" +
                "  FILTER(\n" +
                "    xsd:integer(?userAge) >= " + healthData.getAge() + " && xsd:integer(?userAge) <= " + Integer.toString(Integer.parseInt(healthData.getAge()) + 10) + " &&\n" +
                "    ?userBloodPressure = \"" + healthData.getBloodPressure() + "\" &&\n" +
                "    ?userDiabetes = \"" + healthData.getDiabetes() + "\" &&\n" +
                "    ?userGender = \"" + healthData.getGender() + "\" &&\n" +
                "    ?userPneumonia = \"" + healthData.getPneumonia() + "\"\n" +
                "  )\n" +
                "}";
    }

}
