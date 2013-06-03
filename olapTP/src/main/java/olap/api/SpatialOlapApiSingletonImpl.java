package olap.api;

import java.util.List;

import olap.db.MultiDimMapper;
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
	public void write(String outputPath, List<MultiDimMapper> multidimToTables, MultiDim multidim, String tableName) {
		XmlWriter xmlWriter = new XmlWriter();
		xmlWriter.write(outputPath, multidimToTables, multidim, tableName);
	}

	@Override
	public MultiDim read(String filePath) {
		XmlReader xmlReader = new XmlReader();
		return xmlReader.readMultiDim(filePath);
	}
}
