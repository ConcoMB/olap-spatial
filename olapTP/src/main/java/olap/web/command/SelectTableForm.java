package olap.web.command;

import java.util.List;

public class SelectTableForm {
	private List<String> tables;
	private String selection;

	public SelectTableForm() {
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}
}
