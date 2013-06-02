package ar.edu.itba.olap.domain;

import java.util.List;

public class ApiImpl implements Api {
	
	private static ApiImpl instance;
	
	public static synchronized ApiImpl getInstance(){
		if(instance == null)
			instance = new ApiImpl();
		return instance;
	}

	@Override
	public MultiDim getMultiDim(String filePath) {
		InputParser inputParser = new InputParser();
		MultiDim multidim = inputParser.getMultiDim(filePath);
		return multidim;
	}

	@Override
	public void generateOutput(String outputPath, List<MultiDimToTablesDictionary> multidimToTables, MultiDim multidim, String tableName) {
		OutputGenerator outputGenerator = new OutputGenerator();
		outputGenerator.generateOutput(outputPath, multidimToTables, multidim, tableName);
	}

}
