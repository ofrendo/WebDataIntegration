package uniMannheim.webDataIntegration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReaderBeginning {

	public static void main(String[] args) throws IOException {
		String filePath = "data/DNBTitel.rdf";
        //File file = new File(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
		String line;
		int max = 1000;
		int i = 0;
		while ((line = br.readLine()) != null && i != max) {
			i++;
			System.out.println(line);
		}
		br.close();

	}

}
