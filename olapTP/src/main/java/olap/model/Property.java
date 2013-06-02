package olap.model;

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
	
	public DBColumn getColumn(String before){
		DBColumn column = new DBColumn(before + name, type, isPrimaryKey, id);
		return column;
	}
	
	public String toString(){
		return "PROPERTY:name: " + name + "- type: " + type + "- id: " + id + "\n";
	}
	
}
