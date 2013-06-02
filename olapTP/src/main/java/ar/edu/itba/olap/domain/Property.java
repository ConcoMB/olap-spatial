package ar.edu.itba.olap.domain;

public class Property {

	private String name;
	private String type;
	private boolean id;
	private boolean isPrimaryKey;
	
	public Property(String name, String type, boolean id, boolean isPrimaryKey){
		this.name = name;
		this.type = type;
		this.id = id;
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public boolean isId() {
		return id;
	}
	
	public Column getColumn(String before){
		Column column = new Column(before + name, type, isPrimaryKey, id);
		return column;
	}
	
	public String toString(){
		return "PROPERTY:name: " + name + "- type: " + type + "- id: " + id + "\n";
	}
	
}
