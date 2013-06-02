package olap.model;

import java.util.LinkedList;
import java.util.List;

import olap.db.DBColumn;

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
	
	public String toString(){
		StringBuffer s = new StringBuffer("\n" + "DIMENSION: name: "+name+"\n");
		s = s.append("Dimension's levelsList:" + "\n");
		for (Level p : levels) {
			s = s.append(p.toString() + "\n");
		}
		s = s.append("hierachiesList:" + "\n");
		for (Hierarchy p : hierachies) {
			s = s.append(p.toString() + "\n");
		}
		return s  + "\n";
	}
}
