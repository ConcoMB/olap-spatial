package olap.model;

public class Measure {

	private String name, type, agg;
	
	public Measure(String name, String type, String agg){
		this.name = name;
		this.type = type;
		this.agg = agg;
	}

	public String getName() {
		return name;
	}

	public DBColumn getColumn(){
		return new DBColumn(name,type,false,false);
	}
	
	public String getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAgg(String agg) {
		this.agg = agg;
	}

	public String getAgg() {
		return agg;
	}

	
	public String toString(){
		return "MEDIDA:\n\t nombre = " + name + "; tipo = " + type + "; agg = " + agg + "\n";
	}
}
