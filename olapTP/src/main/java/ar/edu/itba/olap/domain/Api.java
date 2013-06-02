package ar.edu.itba.olap.domain;

import java.util.List;


public interface Api {

	public MultiDim getMultiDim(String filePath);
	
	public void generateOutput(String outputPath, List<MultiDimToTablesDictionary> multidimToTables, MultiDim multidim, String tableName);
}
