package olap.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import olap.db.DBColumn;

public class OlapCube {

	private String name;
	private List<Measure> measures = new LinkedList<Measure>();
	private List<DimensionUsage> dimensionUsages = new LinkedList<DimensionUsage>();

	public OlapCube(String name){
		this.name = name;
	}
	
	public void addMeasure(Measure measure){
		measures.add(measure);
	}
	
	public String getName(){
		return name;
	}
	
	public void addDimensionUsage(DimensionUsage dim){
		dimensionUsages.add(dim);
	}
	
	public List<Measure> getMeasures(){
		return measures;
	}
	
	public List<DimensionUsage> getDimensionUsage(){
		return dimensionUsages;
	}

	public Map<String,Dimension> getColumnNames(){
		Map<String,Dimension> map = new HashMap<String,Dimension>();
		for(DimensionUsage d : dimensionUsages){
			map.put(d.getName(),d.getDimension());
		}
		return map;
	}
	
	public List<String> getMeasuresNames(){
		List<String> columns = new LinkedList<String>();
		for(Measure m: measures){
			columns.add(name + "_" + m.getName());
		}		
		return columns;
	}
	
	public List<DBColumn> getColumns(){
		List<DBColumn> columns = new LinkedList<DBColumn>();
		for(DimensionUsage d: dimensionUsages){
			columns.addAll(d.getColumns());
		}
		for(Measure m: measures){
			columns.add(m.getColumn());
		}
		return columns;
	}
	
	public String toString(){
		StringBuffer s = new StringBuffer("CUBO:\n\tnombre = " + name + "\n");
		s = s.append("MEDIDAS:\n\t" + "\n");
		for (Measure p : measures) {
			s = s.append(p.toString());
		}
		s = s.append("DIMS:\n\t" + "\n");
		for (DimensionUsage p : dimensionUsages) {
			s = s.append(p.toString());
		}
		return s  + "\n";
	}
}
