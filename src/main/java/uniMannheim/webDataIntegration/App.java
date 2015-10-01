package uniMannheim.webDataIntegration;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        String filePath = "C:\\Users\\D059373\\Downloads\\DNBTitel.ttl\\DNBTitel.ttl";
        
        Model model = ModelFactory.createDefaultModel();
        model.read(filePath, "TTL");
        
        
    }
}
