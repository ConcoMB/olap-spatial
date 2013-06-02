package olap.model;

import java.util.List;

public interface SpatialOlapApi {

	public MultiDim getMultiDim(String filePath);
	
	public void generateOutput(String outputPath, List<MultiDimConverter> multidimToTables, MultiDim multidim, String tableName);
}
