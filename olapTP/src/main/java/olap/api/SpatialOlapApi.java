package olap.api;

import java.util.List;

import olap.converter.MultiDimConverter;
import olap.model.MultiDim;

public interface SpatialOlapApi {

	public MultiDim getMultiDim(String filePath);
	
	public void generateOutput(String outputPath, List<MultiDimConverter> multidimToTables, MultiDim multidim, String tableName);
}
