package olap.xml;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlReader {

	public MultiDim readMultiDim(String in) {
		File input = new File(in);
		MultiDim multidim = new MultiDim();
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(input);
			Node root = doc.getElementsByTagName("multidim").item(0);
			NodeList children = root.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node n = children.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE
						&& n.getNodeName().equals("dimension")) {
					multidim.addDimension(readDim(n));
				} else if (n.getNodeType() == Node.ELEMENT_NODE
						&& n.getNodeName().equals("cubo")) {
					multidim.addOlapCube(readOlapCube(n));
				}
			}
			for (OlapCube olapCube : multidim.getOlapCubes()) {
				List<Dimension> dims = multidim.getDimensions();
				for (DimensionWrapper dimW : olapCube.getDimensionUsage()) {
					dimW.setDimension(this.readDimension(dimW.getPtr(), dims));
				}
			}
		} catch (Exception e) {
			throw new XmlException(e.getMessage());
		}
		return multidim;
	}
	
	private Dimension readDim(Node n) {
		Dimension dim = new Dimension(n.getAttributes().getNamedItem("name")
				.getNodeValue());
		for (int i = 0; i < n.getChildNodes().getLength(); i++) {
			Node child = n.getChildNodes().item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals("level")) {
					dim.addLevel(readLevel(child, dim.getName(), true));
				} else if (child.getNodeName().equals("hierarchy")) {
					dim.addHierarchy(readHierarchy(child));
				}
			}
		}
		return dim;
	}

	private Level readLevel(Node n, String dim, boolean PK) {
		Node name = n.getAttributes().getNamedItem("name");
		Node pos = n.getAttributes().getNamedItem("pos");
		Level l;
		if (name != null && pos != null) {
			l = new Level(name.getNodeValue(), pos.getNodeValue());
		} else {
			l = new Level(dim, 0);
		}
		for (int i = 0; i < n.getChildNodes().getLength(); i++) {
			Node child = n.getChildNodes().item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE
					&& child.getNodeName().equals("property")) {
				l.addProperty(readProperty(child, PK));
			}
		}
		return l;
	}

	private Hierarchy readHierarchy(Node n) {
		Node name = n.getAttributes().getNamedItem("name");
		Hierarchy h = new Hierarchy(name != null ? name.getNodeValue() : null);
		for (int i = 0; i < n.getChildNodes().getLength(); i++) {
			Node child = n.getChildNodes().item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE
					&& child.getNodeName().equals("level")) {
				h.addLevel(readLevel(child, null, false));
			}
		}
		return h;
	}

	private Property readProperty(Node n, boolean PK) {
		String name = n.getFirstChild().getNodeValue().trim();
		Node other = n.getAttributes().getNamedItem("type");
		String type = other != null ? other.getNodeValue() : null;
		other = n.getAttributes().getNamedItem("ID");
		boolean id = other != null ? other.getNodeValue().equals("true")
				: false;
		return new Property(name, type, id, PK && id);
	}

	private OlapCube readOlapCube(Node n) {
		Node name = n.getAttributes().getNamedItem("name");
		OlapCube olapCube = name != null ? new OlapCube(name.getNodeValue())
				: new OlapCube(null);
		for (int i = 0; i < n.getChildNodes().getLength(); i++) {
			Node child = n.getChildNodes().item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE
					&& child.getNodeName().equals("measure")) {
				olapCube.addMeasure(readMeasure(child));
			} else if (child.getNodeType() == Node.ELEMENT_NODE
					&& child.getNodeName().equals("dimension")) {
				olapCube.addDimensionUsage(readDimensionW(child));
			}
		}
		return olapCube;
	}

	private Measure readMeasure(Node node) {
		Node nodeName = node.getAttributes().getNamedItem("name");
		String name = nodeName != null ? nodeName.getNodeValue() : null;
		Node nodeType = node.getAttributes().getNamedItem("type");
		String type = nodeType != null ? nodeType.getNodeValue() : null;
		Node nodeAgg = node.getAttributes().getNamedItem("agg");
		String agg = nodeAgg != null ? nodeAgg.getNodeValue() : null;
		return new Measure(name, type, agg);
	}

	private DimensionWrapper readDimensionW(Node n) {
		Node nodeName = n.getAttributes().getNamedItem("name");
		String name = nodeName != null ? nodeName.getNodeValue() : null;
		Node nodePtr = n.getAttributes().getNamedItem("ptr");
		String ptr = nodePtr != null ? nodePtr.getNodeValue() : null;
		return new DimensionWrapper(name, ptr);
	}

	private Dimension readDimension(String ptr, List<Dimension> dims) {
		ptr = ptr.toLowerCase();
		for (Dimension dim : dims) {
			if (dim.getName().toLowerCase().compareTo(ptr) == 0) {
				return dim;
			}
		}
		throw new XmlException();
	}
}
