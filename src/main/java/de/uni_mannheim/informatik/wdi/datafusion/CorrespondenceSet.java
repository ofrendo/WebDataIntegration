package de.uni_mannheim.informatik.wdi.datafusion;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import de.uni_mannheim.informatik.wdi.Matchable;

/**
 * Represents a set of correspondences (from the identity resolution)
 * @author Oliver
 *
 * @param <RecordType>
 */
public class CorrespondenceSet<RecordType extends Matchable & Fusable> {

	private Collection<RecordGroup<RecordType>> groups;
	private Map<String, RecordGroup<RecordType>> recordIndex;
	
	public CorrespondenceSet() {
		groups = new LinkedList<>();
		recordIndex = new HashMap<>();
	}
	
	/**
	 * Loads correspondences from a file and adds them to this correspondence set. Can be called multiple times.
	 * @param correspondenceFile
	 * @param first
	 * @param second
	 * @throws IOException
	 */
	public void loadCorrespondences(File correspondenceFile, FusableDataSet<RecordType> first, FusableDataSet<RecordType> second) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(correspondenceFile));
		
		String[] values = null;
		
		while((values = reader.readNext()) != null) {
			// check if the ids exist in the provided datasets
			if(first.getRecord(values[0])==null) {
				System.err.println(String.format("Record %s not found in first dataset", values[0]));
				continue;
			}
			if(second.getRecord(values[1])==null) {
				System.err.println(String.format("Record %s not found in second dataset", values[0]));
				continue;
			}
			
			// check if the ids already belong to any groups
			RecordGroup<RecordType> grp1 = recordIndex.get(values[0]);
			RecordGroup<RecordType> grp2 = recordIndex.get(values[1]);
			
			
			if(grp1==null && grp2==null) {
				// no existing groups, create a new one
				RecordGroup<RecordType> grp = new RecordGroup<>();
				grp.addRecord(values[0], first);
				grp.addRecord(values[1], second);
				recordIndex.put(values[0], grp);
				recordIndex.put(values[1], grp);
				groups.add(grp);
			} else if(grp1!=null && grp2==null) {
				// one existing group, add to this group
				grp1.addRecord(values[1], second);
				recordIndex.put(values[1], grp1);
			} else if(grp1==null && grp2!=null) {
				// one existing group, add to this group
				grp2.addRecord(values[0], first);
				recordIndex.put(values[0], grp2);
			} else {
				// two existing groups, merge
				grp1.mergeWith(grp2);
				
				for(String id : grp2.getRecordIds()) {
					recordIndex.put(id, grp1);
				}
			}
		}
		
		reader.close();
	}
	
	/**
	 * returns the groups of records which are the same according to the correspondences
	 * @return
	 */
	public Collection<RecordGroup<RecordType>> getRecordGroups() {
		return groups;
	}
	
	/**
	 * writes the distribution of the sizes of the groups of records to the specified file
	 * @param outputFile
	 * @throws IOException
	 */
	public void writeGroupSizeDistribution(File outputFile) throws IOException {
		Map<Integer, Integer> sizeDist = new HashMap<>();
		
		for(RecordGroup<RecordType> grp : groups) {
			int size = grp.getSize();
			
			Integer count = sizeDist.get(size);
			
			if(count==null) {
				count = 0;
			}
			
			sizeDist.put(size, ++count);
		}
		
		CSVWriter writer = new CSVWriter(new FileWriter(outputFile));
		
		writer.writeNext(new String[] { "Group Size", "Frequency" });
		for(int size : sizeDist.keySet()) {
			writer.writeNext(new String[] { Integer.toString(size), Integer.toString(sizeDist.get(size)) });
		}
		
		writer.close();
	}
	
}
