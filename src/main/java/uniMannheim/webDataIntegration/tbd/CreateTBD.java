package uniMannheim.webDataIntegration.tbd;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;

public class CreateTBD {

	public static void main(String[] args) {
		
		// Make a TDB-backed dataset
		String directory = "tbd/DNB" ;
		Dataset dataset = TDBFactory.createDataset(directory) ;
		
		dataset.begin(ReadWrite.READ) ;
		// Get model inside the transaction
		Model model = dataset.getDefaultModel() ;
		dataset.end() ;

	}

}
