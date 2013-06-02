package olap.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import olap.db.DBColumn;

public class Level implements Comparator<Level>, Comparable<Level>{

	private String name, type;
	private int pos;
	private List<Property> properties = new LinkedList<Property>();
	
	public Level(String name, int pos){
		this.name = name;
		this.pos = pos;
	}
	
	public Level(String name, String pos){
		this(name, Integer.parseInt(pos));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return pos;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setPosition(int pos) {
		this.pos = pos;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void addProperty(Property property){
		this.properties.add(property);
	}
	
	@Override
	public int compare(Level level1, Level level2) {
		return level1.getPosition() - level2.getPosition();
	}

	@Override
	public int compareTo(Level level) {
		return this.getPosition() - level.getPosition();
	}
	
	public List<String>getColumnNames(String dimName){
		List<String> columns = new LinkedList<String>();
		String before = dimName + name + "_";
		for(Property p: properties){
			columns.add(before.concat(p.getName()));
		}
		return columns;
	}
	
	public List<DBColumn> getCols(String b){
		List<DBColumn> columns = new LinkedList<DBColumn>();
		for(Property p: properties){
			columns.add(p.getColumn(b + name + "_"));
		}		
		return columns;
	}
	
	public String toString(){
		String string =  "NIVEL:\n\tnombre = " + name + "; tipo = " + type + "; pos = " + pos + "\n\t Propiedades => \n";
		for(Property p :properties){
			string = string.concat(p.toString());
		}
		return string;
	}
	
}
