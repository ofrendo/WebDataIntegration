package uniMannheim.webDataIntegration;

import static spark.Spark.post;
import static spark.Spark.get;
import static spark.SparkBase.port;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println("Starting server..." );
        //String filePath = "C:\\Users\\D059373\\Downloads\\DNBTitel.ttl\\DNBTitel.ttl";
        
        
        //Model model = ModelFactory.createDefaultModel();
        //model.read(filePath, "TTL");
        
        int port = 5000;
        port(port);
        
        get("/", (request, response) -> {
        	return "Hello world!";
        });
        
        post("/", (request, response) -> {
        	SparqlHandler handler = SparqlHandler.getInstance();
        	if (!handler.isModelLoaded()) {
        		return "Model not loaded yet.";
        	}
        	String sparql = request.body();
        	String result = handler.doQuery(sparql);
        	return result;
        });
        
        System.out.println("Loading data...");
        SparqlHandler.getInstance().loadModel(SparqlHandler.FILE_DNB, SparqlHandler.FILE_TYPE_RDFXML);
    }
}
