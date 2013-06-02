package olap.model;

import olap.db.DBColumn;

public class Property {

	private String name, type;
	private boolean id, isPrimaryKey;
	
	public Property(String name, String type, boolean id, boolean isPrimaryKey){
		this.name = name;
		this.isPrimaryKey = isPrimaryKey;
		this.type = type;
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public boolean isId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public DBColumn getColumn(String before){
		DBColumn column = new DBColumn(before + name, type, isPrimaryKey, id);
		return column;
	}
	
	public String toString(){
		return "PROPIEDAD:\n\tnombre = " + name + "; tipo = " + type + (id ? "; ID" : "") + (isPrimaryKey ? "; PK" : "") + "\n";
	}
}
