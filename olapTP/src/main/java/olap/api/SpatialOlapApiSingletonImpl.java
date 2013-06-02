package olap.api;

import java.util.List;

import olap.converter.MultiDimConverter;
import olap.model.MultiDim;
import olap.xml.XmlReader;
import olap.xml.XmlWriter;


public class SpatialOlapApiSingletonImpl implements SpatialOlapApi {
	
	private static SpatialOlapApiSingletonImpl api;
	
	public static synchronized SpatialOlapApiSingletonImpl getInstance(){
		if(api == null)
			api = new SpatialOlapApiSingletonImpl();
		return api;
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
