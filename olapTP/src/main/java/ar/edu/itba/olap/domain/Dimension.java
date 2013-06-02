package ar.edu.itba.olap.domain;

import java.util.LinkedList;
import java.util.List;

public class Dimension {

	private String name;
	private List<Hierachy> hierachies;
	private List<Level> levels;
	
	
	public Dimension(String name){
		this.name = name;
		this.hierachies = new LinkedList<Hierachy>();
		this.levels = new LinkedList<Level>();
	}
	
	public void addHierachy(Hierachy h){
		hierachies.add(h);
	}
	
	public List<Hierachy> getHierachies(){
		return this.hierachies;
	}
	
	public List<Level> getLevels(){
		return this.levels;
	}
	
	public void addLevel(Level level){
		levels.add(level);
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		String string = "\n" + "DIMENSION: name: "+name+"\n";
		string = string.concat("Dimension's levelsList:" + "\n");
		for (Level p : levels) {
			string = string.concat(p.toString() + "\n");
		}
		string = string.concat("hierachiesList:" + "\n");
		for (Hierachy p : hierachies) {
			string = string.concat(p.toString() + "\n");
		}
		
		return string  + "\n";
	}
	
	public List<String> getColumnNames(String cuboName){
		List<String> columns = new LinkedList<String>();
		for(Level l: levels){
			columns.addAll(l.getColumnNames(cuboName));			
		}
		for(Hierachy h:hierachies){
			columns.addAll(h.getColumnNames(cuboName));
		}		
		return columns;
	}
	
	public List<Column> getColumns(String before){
		List<Column> columns = new LinkedList<Column>();
		for(Level l: levels){
			columns.addAll(l.getColumns(before));
		}
		for(Hierachy h: hierachies){
			columns.addAll(h.getColumns(before));
		}
		return columns;
	}
}
