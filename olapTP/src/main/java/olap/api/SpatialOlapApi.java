package olap.api;

import java.util.List;

import olap.db.MultiDimMapper;
import olap.model.MultiDim;

public interface SpatialOlapApi {

	public MultiDim getMultiDim(String filePath);
	
	public void generateOutput(String outputPath, List<MultiDimMapper> multidimToTables, MultiDim multidim, String tableName);
}
