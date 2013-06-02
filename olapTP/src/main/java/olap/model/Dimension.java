package olap.model;

import java.util.LinkedList;
import java.util.List;

public class Dimension {

	private String name;
	private List<Hierarchy> hierachies = new LinkedList<Hierarchy>();
	private List<Level> levels = new LinkedList<Level>();
	
	
	public Dimension(String name){
		this.name = name;
	}
	
	public void addHierachy(Hierarchy h){
		hierachies.add(h);
	}
	
	public List<Hierarchy> getHierachies(){
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
		for (Hierarchy p : hierachies) {
			string = string.concat(p.toString() + "\n");
		}
		
		return string  + "\n";
	}
	
	public List<String> getColumnNames(String cuboName){
		List<String> columns = new LinkedList<String>();
		for(Level l: levels){
			columns.addAll(l.getColumnNames(cuboName));			
		}
		for(Hierarchy h:hierachies){
			columns.addAll(h.getColumnNames(cuboName));
		}		
		return columns;
	}
	
	public List<DBColumn> getColumns(String before){
		List<DBColumn> columns = new LinkedList<DBColumn>();
		for(Level l: levels){
			columns.addAll(l.getCols(before));
		}
		for(Hierarchy h: hierachies){
			columns.addAll(h.getColumns(before));
		}
		return columns;
	}
}
