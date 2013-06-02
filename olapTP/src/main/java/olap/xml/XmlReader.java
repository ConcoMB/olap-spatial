package olap.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import olap.exceptions.XmlException;
import olap.model.Dimension;
import olap.model.DimensionUsage;
import olap.model.Hierarchy;
import olap.model.Level;
import olap.model.Measure;
import olap.model.MultiDim;
import olap.model.OlapCube;
import olap.model.Property;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlReader {

	public static Document read(String xml) throws org.xml.sax.SAXException,
			IOException, ParserConfigurationException {
		return read(new ByteArrayInputStream(xml.getBytes()));
	}

	public static Document read(InputStream is)
			throws org.xml.sax.SAXException, java.io.IOException,
			ParserConfigurationException {
		javax.xml.parsers.DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = null;
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document doc = builder.parse(is);
		is.close();
		return doc;
	}

	public MultiDim readMultiDim(String inputPath) {
		File input = new File(inputPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		MultiDim multidim = new MultiDim();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(input);
			Node root = doc.getElementsByTagName("multidim").item(0);
			NodeList children = root.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node first = children.item(i);
				if (first.getNodeType() == Node.ELEMENT_NODE
						&& first.getNodeName().equals("dimension")) {
					Dimension dim = this.getDimension(first);
					multidim.addDimension(dim);
				} else if (first.getNodeType() == Node.ELEMENT_NODE
						&& first.getNodeName().equals("cubo")) {
					OlapCube cubo = this.readOlapCube(first);
					multidim.addCubo(cubo);
				}
			}
			for (OlapCube olapCube : multidim.getCubos()) {
				List<Dimension> dims = multidim.getDimensions();
				for (DimensionUsage dimUsage : olapCube.getDimensionUsage()) {
					dimUsage.setDimension(this.readDimension(dimUsage.getPtr(),
							dims));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return multidim;
	}

	public Dimension getDimension(Node node) {
		Dimension dim = new Dimension(node.getAttributes().getNamedItem("name")
				.getNodeValue());
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node child = node.getChildNodes().item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals("level")) {
					Level level = this.getLevel(child, dim.getName(), true);
					dim.addLevel(level);
				} else if (child.getNodeName().equals("hierarchy")) {
					Hierarchy hierachy = this.readHierachy(child);
					dim.addHierachy(hierachy);
				}
			}
		}
		return dim;
	}

	public Level getLevel(Node node, String dimName, boolean isPrimaryKey) {
		Level level;
		Node nodeName = node.getAttributes().getNamedItem("name");
		Node nodePos = node.getAttributes().getNamedItem("pos");
		if (nodeName != null && nodePos != null) {
			level = new Level(nodeName.getNodeValue(), nodePos.getNodeValue());
		} else {
			level = new Level(dimName, 0);
		}
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node child = node.getChildNodes().item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE
					&& child.getNodeName().equals("property")) {
				Property property = this.readProperty(child, isPrimaryKey);
				level.addProperty(property);
			}
		}
		return level;
	}

	public Hierarchy readHierachy(Node node) {
		Hierarchy hierachy;
		Node nodeName = node.getAttributes().getNamedItem("name");
		if (nodeName != null) {
			hierachy = new Hierarchy(nodeName.getNodeValue());
		} else {
			hierachy = new Hierarchy(null);
		}
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node child = node.getChildNodes().item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE
					&& child.getNodeName().equals("level")) {
				Level level = this.getLevel(child, null, false);
				hierachy.addLevel(level);
			}
		}
		return hierachy;
	}

	public Property readProperty(Node node, boolean isPrimaryKey) {
		String type = null;
		boolean id = false;
		String name = node.getFirstChild().getNodeValue().trim();
		Node aux = node.getAttributes().getNamedItem("type");
		if (aux != null) {
			type = aux.getNodeValue();
		}
		aux = node.getAttributes().getNamedItem("ID");
		if (aux != null) {
			id = aux.getNodeValue().equals("true");
		}
		return new Property(name, type, id, isPrimaryKey && id);
	}

	public OlapCube readOlapCube(Node node) {
		OlapCube olapCube;
		Node nodeName = node.getAttributes().getNamedItem("name");
		if (nodeName != null) {
			olapCube = new OlapCube(nodeName.getNodeValue());
		} else {
			olapCube = new OlapCube(null);
		}
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node child = node.getChildNodes().item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE
					&& child.getNodeName().equals("measure")) {
				Measure measure = readMeasure(child);
				olapCube.addMeasure(measure);
			} else if (child.getNodeType() == Node.ELEMENT_NODE
					&& child.getNodeName().equals("dimension")) {
				DimensionUsage dimensionUsage = this.readDimensionUsage(child);
				olapCube.addDimensionUsage(dimensionUsage);
			}
		}
		return olapCube;
	}

	public Measure readMeasure(Node node) {
		String name = null;
		String type = null;
		String agg = null;
		Node nodeName = node.getAttributes().getNamedItem("name");
		Node nodeType = node.getAttributes().getNamedItem("type");
		Node nodeAgg = node.getAttributes().getNamedItem("agg");
		if (nodeName != null) {
			name = nodeName.getNodeValue();
		}
		if (nodeType != null) {
			type = nodeType.getNodeValue();
		}
		if (nodeAgg != null) {
			agg = nodeAgg.getNodeValue();
		}
		return new Measure(name, type, agg);
	}

	public DimensionUsage readDimensionUsage(Node node) {
		String name = null;
		String ptr = null;
		Node nodeName = node.getAttributes().getNamedItem("name");
		Node nodePtr = node.getAttributes().getNamedItem("ptr");
		if (nodeName != null) {
			name = nodeName.getNodeValue();
		}
		if (nodePtr != null) {
			ptr = nodePtr.getNodeValue();
		}
		DimensionUsage du = new DimensionUsage(name, ptr);
		return du;
	}

	private Dimension readDimension(String ptr, List<Dimension> dims) {
		for (Dimension dim : dims) {
			if (dim.getName().compareToIgnoreCase(ptr) == 0) {
				return dim;
			}
		}
		throw new XmlException();
	}
}