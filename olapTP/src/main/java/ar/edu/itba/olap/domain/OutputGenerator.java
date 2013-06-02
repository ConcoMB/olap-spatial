package ar.edu.itba.olap.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OutputGenerator {

//	public static void main(String[] args) {
//		InputParser ip = new InputParser();
//		MultiDim multidim = ip.getMultiDim("input.xml");
//		OutputGenerator og = new OutputGenerator();
//		og.generateOutput("geomondrian.xml", null, multidim, "tableName");
//	}

	public void generateOutput(String outputPath, List<MultiDimToTablesDictionary> multidimToTables, MultiDim multidim, String tableName) {
		Document document;
		try {
			document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			Element schema = document.createElement("Schema");
			schema.setAttribute("name", tableName);
			for(Cubo cubo: multidim.getCubos()){
				Element cuboElement = document.createElement("Cube");
				cuboElement.setAttribute("name", cubo.getName());
				Element tableElement = document.createElement("Table");
				tableElement.setAttribute("name", tableName);
				cuboElement.appendChild(tableElement);
				List<DimensionUsage> dimUsages = cubo.getDimensionUsage();
				for(int i = 0; i < dimUsages.size(); i++){
					DimensionUsage dimUsage = dimUsages.get(i);
					Dimension dim = dimUsage.getDimension();
					Element dimElement = document.createElement("Dimension");
					dimElement.setAttribute("name", dimUsage.getName());
					for(Hierachy hierarchy : dim.getHierachies()){
						Element hierarchyElement = document.createElement("Hierarchy");
						hierarchyElement.setAttribute("name", hierarchy.getName());
						hierarchyElement.setAttribute("hasAll", "true");
						if(dim.getLevels().isEmpty()){
							throw new IllegalArgumentException("El numero de niveles en " + dim.getName() + " fuera de jerarquias es 0 ");
						}
						Level firstLevel = dim.getLevels().get(0);
						Element firstLevelElement = document.createElement("Level");
						for(Property prop : firstLevel.getProperties()){
							if(prop.isId()){
								String firstLevelColName = getColumnName(multidimToTables, dimUsage.getName()+"_"+firstLevel.getName()+"_"+prop.getName());
								firstLevelElement.setAttribute("name" , firstLevelColName);
								firstLevelElement.setAttribute("column" , firstLevelColName);
								firstLevelElement.setAttribute("type", makeFirstCharUpper(prop.getType()));
							}else{
								String propColName = getColumnName(multidimToTables, dimUsage.getName()+"_"+firstLevel.getName()+"_"+prop.getName());
								Element propElement = document.createElement("Property");
								propElement.setAttribute("name" , propColName);
								propElement.setAttribute("column" , propColName);
								propElement.setAttribute("type", makeFirstCharUpper(prop.getType()));
								firstLevelElement.appendChild(propElement);
							}
						}
						hierarchyElement.appendChild(firstLevelElement);
						for(Level level : hierarchy.getLevels()){
							Element levelElement = document.createElement("Level");
							for(Property prop : level.getProperties()){
								if(prop.isId()){
									String levelColName = getColumnName(multidimToTables, dimUsage.getName()+"_"+level.getName()+"_"+prop.getName());
									levelElement.setAttribute("name" , levelColName);
									levelElement.setAttribute("column" , levelColName);
									levelElement.setAttribute("type", makeFirstCharUpper(prop.getType()));
								}else{
									String propColName = getColumnName(multidimToTables, dimUsage.getName()+"_"+level.getName()+"_"+prop.getName());
									Element propElement = document.createElement("Property");
									propElement.setAttribute("name" , propColName);
									propElement.setAttribute("column" , propColName);
									propElement.setAttribute("type", makeFirstCharUpper(prop.getType()));
									levelElement.appendChild(propElement);
								}
							}
							hierarchyElement.appendChild(levelElement);
						}
						dimElement.appendChild(hierarchyElement);
					}
					cuboElement.appendChild(dimElement);
				}
				for(Measure measure : cubo.getMeasures()){
					Element measureElement = document.createElement("Measure");
					String measureColName = getColumnName(multidimToTables, measure.getName());
					measureElement.setAttribute("name", measureColName);
					measureElement.setAttribute("column", measureColName);
					measureElement.setAttribute("aggregator", measure.getAgg());
					measureElement.setAttribute("datatype", makeFirstCharUpper(measure.getType()));
					cuboElement.appendChild(measureElement);
				}
				schema.appendChild(cuboElement);
			}
			document.appendChild(schema);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(writer));
			String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
//			System.out.println(output);
			FileWriter fw = new FileWriter(new File(outputPath));
			fw.write(output);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getColumnName(List<MultiDimToTablesDictionary> multidimToTables, String multidimName) {
		return multidimName;
//		for(MultiDimToTablesDictionary dic : multidimToTables) {
//			if(dic.getMultidimName().equalsIgnoreCase(multidimName)) {
//				return dic.getColumnName();
//			}
//		}
//		throw new IllegalArgumentException();
	}

	private String makeFirstCharUpper(String actualName) {
		StringBuilder name = new StringBuilder(actualName);
		name.setCharAt(0, (char)(name.charAt(0) -32));
		for(int i = 1 ; i < name.length() ; i++){
			if(name.charAt(i -1) == ' ' && name.charAt(i) != ' '){
				name.setCharAt(i, (char)(name.charAt(i) -32));
			}
		}	
		return name.toString();
	}
}
