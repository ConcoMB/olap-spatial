package ar.edu.itba.olap.domain;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Level implements Comparator<Level>, Comparable<Level>{

	private String name;
	private int pos;
	private List<Property> properties;
	private String type;
	
	public Level(String name, int pos){
		this.name = name;
		this.pos = pos;
		this.properties = new LinkedList<Property>();
	}
	
	public Level(String name, String pos){
		this.name = name;
		this.pos = Integer.parseInt(pos);
		this.properties = new LinkedList<Property>();
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

	public void setPosition(int pos) {
		this.pos = pos;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void addProperty(Property property){
		this.properties.add(property);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int compare(Level level1, Level level2) {
		return level1.getPosition() - level2.getPosition();
	}

	@Override
	public int compareTo(Level level) {
		return this.getPosition() - level.getPosition();
	}
	
	public String toString(){
		String string =  "LEVEL:name: " + name + " pos: " + pos + " type: " + type + "\n";
		for(Property p :properties){
			string = string.concat(p.toString());
		}
		return string + " fin level \n";
	}
	
	public List<String>getColumnNames(String dimName){
		List<String> columns = new LinkedList<String>();
		String before = dimName + name + "_";
		for(Property p: properties){
			columns.add(before.concat(p.getName()));
		}
		return columns;
	}
	
	public List<Column> getColumns(String before){
		List<Column> columns = new LinkedList<Column>();
		for(Property p: properties){
			columns.add(p.getColumn(before + name + "_"));
		}		
		return columns;
	}
	
}
