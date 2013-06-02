package olap.api;

import java.util.List;

import olap.db.MultiDimMapper;
import olap.model.MultiDim;

public interface SpatialOlapApi {

	public MultiDim read(String filePath);
	
	public void write(String outputPath, List<MultiDimMapper> multidimToTables, MultiDim multidim, String tableName);
}
