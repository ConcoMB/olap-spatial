package olap.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import olap.db.DBColumn;

public class OlapCube {

	private String name;
	private List<Measure> measures = new ArrayList<Measure>();
	private List<DimensionWrapper> dimensionUsages = new ArrayList<DimensionWrapper>();

	public OlapCube(String name){
		this.name = name;
	}
	
	public void addMeasure(Measure measure){
		measures.add(measure);
	}
	
	public String getName(){
		return name;
	}
	
	public void addDimensionUsage(DimensionWrapper dim){
		dimensionUsages.add(dim);
	}
	
	public List<Measure> getMeasures(){
		return measures;
	}
	
	public List<DimensionWrapper> getDimensionUsage(){
		return dimensionUsages;
	}

	public Map<String,Dimension> getColumnNames(){
		Map<String,Dimension> map = new HashMap<String,Dimension>();
		for(DimensionWrapper d : dimensionUsages){
			map.put(d.getName(),d.getDimension());
		}
		return map;
	}
	
	public List<String> getMeasuresNames(){
		List<String> columns = new ArrayList<String>();
		for(Measure m: measures){
			columns.add(name + "_" + m.getName());
		}		
		return columns;
	}
	
	public List<DBColumn> getColumns(){
		List<DBColumn> columns = new ArrayList<DBColumn>();
		for(DimensionWrapper d: dimensionUsages){
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
		for (DimensionWrapper p : dimensionUsages) {
			s = s.append(p.toString());
		}
		return s  + "\n";
	}
}
