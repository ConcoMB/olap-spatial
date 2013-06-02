package ar.edu.itba.olap.domain;

public class Measure {

	private String name;
	private String type;
	private String agg;
	
	public Measure(String name, String type, String agg){
		this.name = name;
		this.type = type;
		this.agg = agg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAgg() {
		return agg;
	}

	public void setAgg(String agg) {
		this.agg = agg;
	}
	
	public Column getColumn(){
		Column c = new Column(name,type,false,false);
		return c;
	}
	
	public String toString(){
		return "MEASURE:name: " + name + "- type: " + type + "- agg: " + agg + "\n";
	}
}
