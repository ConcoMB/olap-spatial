package olap.web.command;

import org.springframework.web.multipart.MultipartFile;

public class UploadXmlForm {

	private MultipartFile file;

	public UploadXmlForm() {
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public MultipartFile getFile() {
		return file;
	}

}
