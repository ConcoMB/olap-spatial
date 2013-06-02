package olap.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import olap.db.DBColumn;

public class MultiDim {

	private List<Dimension> dimensions = new ArrayList<Dimension>();
	private List<OlapCube> olapCubes = new ArrayList<OlapCube>();
	
	public void addDimension(Dimension dim) {
		dimensions.add(dim);
	}


	public List<Dimension> getDimensions() {
		return dimensions;
	}

	public List<OlapCube> getOlapCubes() {
		return olapCubes;
	}

	public List<DBColumn> getColumns(){
		List<DBColumn> columns = new ArrayList<DBColumn>();
		for(OlapCube c : olapCubes){
			columns.addAll(c.getColumns());
		}
		return columns;
	}

	public void addOlapCube(OlapCube cubo) {
		olapCubes.add(cubo);
	}

	public List<String> getMultiDimNames(){
		List<String> columns = new ArrayList<String>();
		for(OlapCube c: olapCubes){
			columns.addAll(c.getMeasuresNames());
			Map<String,Dimension> name = c.getColumnNames();		
			 List<String> names = new ArrayList<String>();
			for(Entry<String,Dimension> d: name.entrySet()){
				names.addAll(d.getValue().getColumnNames(c.getName()+"_"+d.getKey()+"_"));
			}
			columns.addAll(names);
		}
		return columns;
	}
	
	public String toString() {
		StringBuffer string = new StringBuffer("MULTI DIM:\n Dimensiones = \n");
		for (Dimension p : dimensions) {
			string = string.append(p.toString());
		}
		string = string.append("CUBOS:" + "\n");
		for (OlapCube p : olapCubes) {
			string = string.append(p.toString());
		}
		return string + "\n";
	}
}
