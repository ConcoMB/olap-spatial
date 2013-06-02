package olap.model;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import olap.db.DBColumn;

public class Hierarchy {

	private String name;
	private SortedSet<Level> levels = new TreeSet<Level>();
	
	public Hierarchy(String name){
		this.name = name;
	}
	
	public SortedSet<Level> getLevels(){
		return this.levels;
	}
	
	public void addLevel(Level level){
		levels.add(level);
	}
	
	public String getName(){
		return this.name;
	}
	
	public List<String> getColumnNames(String dimName){
		List<String> columns = new LinkedList<String>();
		for(Level l: levels){
			columns.addAll(l.getColumnNames(dimName));			
		}		
		return columns;
	}
	
	public List<DBColumn> getColumns(String before){
		List<DBColumn> columns = new LinkedList<DBColumn>();
		for(Level l: levels){
			columns.addAll(l.getCols(before));
		}
		return columns;
	}

	public String toString(){
		StringBuffer s = new StringBuffer("JERARQUIA:\n\tnombre = "+name+ "\n niveles:\n");
		s = s.append("levels:" + "\n");
		for (Level p : levels) {
			s = s.append(p.toString());
		}
		return s + "\n";
	}
}
