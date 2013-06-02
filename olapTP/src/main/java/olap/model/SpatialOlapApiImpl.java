package olap.model;

import java.util.List;

import olap.xml.XmlReader;
import olap.xml.XmlWriter;


public class SpatialOlapApiImpl implements SpatialOlapApi {
	
	private static SpatialOlapApiImpl instance;
	
	public static synchronized SpatialOlapApiImpl getInstance(){
		if(instance == null)
			instance = new SpatialOlapApiImpl();
		return instance;
	}


	@Override
	public void generateOutput(String outputPath, List<MultiDimConverter> multidimToTables, MultiDim multidim, String tableName) {
		XmlWriter xmlWriter = new XmlWriter();
		xmlWriter.write(outputPath, multidimToTables, multidim, tableName);
	}

	@Override
	public MultiDim getMultiDim(String filePath) {
		XmlReader xmlReader = new XmlReader();
		return xmlReader.readMultiDim(filePath);
	}
}
