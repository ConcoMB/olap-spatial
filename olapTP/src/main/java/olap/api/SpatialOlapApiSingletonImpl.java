package olap.api;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import olap.db.MultiDimMapper;
import olap.model.MultiDim;
import olap.xml.XmlReader;
import olap.xml.XmlWriter;

import org.springframework.web.multipart.MultipartFile;


public class SpatialOlapApiSingletonImpl implements SpatialOlapApi {
	
	private static SpatialOlapApiSingletonImpl api;
	
	public static synchronized SpatialOlapApiSingletonImpl getInstance(){
		if(api == null)
			api = new SpatialOlapApiSingletonImpl();
		return api;
	}

	@Override
	public void write(OutputStreamWriter outputPath, List<MultiDimMapper> multidimToTables, MultiDim multidim, String tableName) {
		XmlWriter xmlWriter = new XmlWriter();
		xmlWriter.write(outputPath, multidimToTables, multidim, tableName);
	}

	@Override
	public MultiDim convert(MultipartFile file) {
		XmlReader xmlReader = new XmlReader();
		File tmpFile = new File(System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator")
				+ file.getOriginalFilename());
		try {
			file.transferTo(tmpFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xmlReader.readMultiDim(tmpFile.getAbsolutePath());
	}
}
