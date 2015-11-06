package de.uni_mannheim.informatik.wdi.identityresolution.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.uni_mannheim.informatik.wdi.MatchableFactory;

/**
 * Factory class for creating DefaultModel records from an XML node. All child nodes on the first level are added as attributes.
 * @author Oliver
 *
 */
public class DefaultRecordFactory extends MatchableFactory<DefaultRecord> {

	private String idAttributeName;
	
	public DefaultRecordFactory(String idAttributeName) {
		this.idAttributeName = idAttributeName;
	}
	
	@Override
	public DefaultRecord createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, idAttributeName);

		DefaultRecord model = new DefaultRecord(id, provenanceInfo);
		
		// get all child nodes
		NodeList children = node.getChildNodes();

		// iterate over the child nodes until the node with childName is found
		for (int j = 0; j < children.getLength(); j++) {
			Node child = children.item(j);

			// check the node type
			if (child.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE && child.getChildNodes().getLength()>0) {
				
				// single value or list?
				if(child.getChildNodes().getLength()==1) {
					
					// single value
					model.setValue(child.getNodeName(), child.getTextContent().trim());
					
				} else {
					
					// list
					// prepare a list to hold all values
					List<String> values = new ArrayList<>(child.getChildNodes()
							.getLength());

					// iterate the value nodes
					for (int i = 0; i < child.getChildNodes().getLength(); i++) {
						Node valueNode = child.getChildNodes().item(i);
						String value = valueNode.getTextContent().trim();

						// add the value
						values.add(value);
					}
					
					model.setList(child.getNodeName(), values);
					
				}
				
			}
			
		}
		
		return model;
	}

}
