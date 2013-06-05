package olap.api;

import java.io.OutputStreamWriter;
import java.util.List;

import olap.db.MultiDimMapper;
import olap.model.MultiDim;

import org.springframework.web.multipart.MultipartFile;

public interface SpatialOlapApi {

	public MultiDim convert(MultipartFile file);
	
	public void write(OutputStreamWriter outputPath, List<MultiDimMapper> multidimToTables, MultiDim multidim, String tableName);
}
