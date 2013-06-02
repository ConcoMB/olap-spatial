package olap.xml;

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

import olap.db.MultiDimMapper;
import olap.exception.XmlException;
import olap.model.Dimension;
import olap.model.DimensionWrapper;
import olap.model.Hierarchy;
import olap.model.Level;
import olap.model.Measure;
import olap.model.MultiDim;
import olap.model.OlapCube;
import olap.model.Property;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlWriter {

	public void write(String out, List<MultiDimMapper> multidimMappers,
			MultiDim multiDim, String tableName) {
		try {
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			Element schema = doc.createElement("Schema");
			schema.setAttribute("name", tableName);
			handleOlapCube(doc, schema, multiDim, tableName, multidimMappers);
			doc.appendChild(schema);
			write(doc, out);
		} catch (Exception e) {
			throw new XmlException(e.getMessage());
		}
	}

	private void write(Document doc, String out) throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		FileWriter fw = new FileWriter(new File(out));
		fw.write(output);
		fw.close();
	}

	private void handleOlapCube(Document doc, Element schema,
			MultiDim multidim, String tableName,
			List<MultiDimMapper> multidimMappers) {
		for (OlapCube olapCube : multidim.getOlapCubes()) {
			Element cubeElem = doc.createElement("Cube");
			cubeElem.setAttribute("name", olapCube.getName());
			Element tableElem = doc.createElement("Table");
			tableElem.setAttribute("name", tableName);
			cubeElem.appendChild(tableElem);
			List<DimensionWrapper> dimWs = olapCube.getDimensionUsage();
			handleDimensions(doc, cubeElem, dimWs, multidimMappers);
			handleMeasures(doc, cubeElem, olapCube, multidimMappers);
			schema.appendChild(cubeElem);
		}
	}

	private void handleMeasures(Document doc, Element cubeElem,
			OlapCube olapCube, List<MultiDimMapper> multidimMappers) {
		for (Measure measure : olapCube.getMeasures()) {
			Element measureElem = doc.createElement("Measure");
			String measureColName = getColumnName(multidimMappers,
					measure.getName());
			measureElem.setAttribute("name", measureColName);
			measureElem.setAttribute("column", measureColName);
			measureElem.setAttribute("aggregator", measure.getAgg());
			measureElem
					.setAttribute("datatype", toCamelCase(measure.getType()));
			cubeElem.appendChild(measureElem);
		}
	}

	private void handleDimensions(Document doc, Element cubeElem,
			List<DimensionWrapper> dimWs, List<MultiDimMapper> multidimMappers) {
		for (DimensionWrapper dimW : dimWs) {
			Dimension dim = dimW.getDimension();
			Element dimElement = doc.createElement("Dimension");
			dimElement.setAttribute("name", dimW.getName());
			handleHierarchies(doc, dimElement, dim, dimW, multidimMappers);
			cubeElem.appendChild(dimElement);
		}
	}

	private void handleHierarchies(Document doc, Element dimElem,
			Dimension dim, DimensionWrapper dimW,
			List<MultiDimMapper> multidimMappers) {
		for (Hierarchy h : dim.getHierarchies()) {
			Element hierarchyElem = doc.createElement("Hierarchy");
			hierarchyElem.setAttribute("name", h.getName());
			hierarchyElem.setAttribute("hasAll", "true");
			if (dim.getLevels().isEmpty()) {
				throw new XmlException("The number of levels on "
						+ dim.getName() + " out of hierarchy is 0 ");
			}
			Level firstLevel = dim.getLevels().get(0);
			Element firstLevelElement = doc.createElement("Level");
			handlePropertiesFirstLevel(doc, firstLevelElement, firstLevel,
					dimW, multidimMappers);
			hierarchyElem.appendChild(firstLevelElement);
			handleLevels(doc, hierarchyElem, h, dimW, multidimMappers);
			dimElem.appendChild(hierarchyElem);
		}
	}

	private void handleLevels(Document doc, Element hierarchyElem, Hierarchy h,
			DimensionWrapper dimW, List<MultiDimMapper> multidimMappers) {
		for (Level level : h.getLevels()) {
			Element levelElem = doc.createElement("Level");
			for (Property prop : level.getProperties()) {
				if (prop.isId()) {
					String levelColName = getColumnName(
							multidimMappers,
							dimW.getName() + "_" + level.getName() + "_"
									+ prop.getName());
					levelElem.setAttribute("name", levelColName);
					levelElem.setAttribute("column", levelColName);
					levelElem.setAttribute("type", toCamelCase(prop.getType()));
				} else {
					String propColName = getColumnName(
							multidimMappers,
							dimW.getName() + "_" + level.getName() + "_"
									+ prop.getName());
					Element propElement = doc.createElement("Property");
					propElement.setAttribute("name", propColName);
					propElement.setAttribute("column", propColName);
					propElement.setAttribute("type",
							toCamelCase(prop.getType()));
					levelElem.appendChild(propElement);
				}
			}
			hierarchyElem.appendChild(levelElem);
		}
	}

	private void handlePropertiesFirstLevel(Document doc,
			Element firstLevelElement, Level firstLevel, DimensionWrapper dimW,
			List<MultiDimMapper> multidimMappers) {
		for (Property prop : firstLevel.getProperties()) {
			if (prop.isId()) {
				String firstLevelColName = getColumnName(multidimMappers,
						dimW.getName() + "_" + firstLevel.getName() + "_"
								+ prop.getName());
				firstLevelElement.setAttribute("name", firstLevelColName);
				firstLevelElement.setAttribute("column", firstLevelColName);
				firstLevelElement.setAttribute("type",
						toCamelCase(prop.getType()));
			} else {
				String propColName = getColumnName(multidimMappers,
						dimW.getName() + "_" + firstLevel.getName() + "_"
								+ prop.getName());
				Element propElement = doc.createElement("Property");
				propElement.setAttribute("name", propColName);
				propElement.setAttribute("column", propColName);
				propElement.setAttribute("type", toCamelCase(prop.getType()));
				firstLevelElement.appendChild(propElement);
			}
		}
	}

	private String getColumnName(List<MultiDimMapper> mappers,
			String multidimName) {
		multidimName = multidimName.toLowerCase();
		for (MultiDimMapper mapper : mappers) {
			if (mapper.getMultidim().toLowerCase().equals(multidimName)) {
				return mapper.getColumn();
			}
		}
		throw new XmlException("No table with name:" + multidimName);
	}

	private String toCamelCase(String actualName) {
		StringBuffer name = new StringBuffer(actualName);
		name.setCharAt(0, (char) (name.charAt(0) - 32));
		for (int i = 1; i < name.length(); i++) {
			if (name.charAt(i - 1) == ' ' && name.charAt(i) != ' ') {
				name.setCharAt(i, (char) (name.charAt(i) - 32));
			}
		}
		return name.toString();
	}
}
