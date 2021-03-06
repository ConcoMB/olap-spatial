package olap.model;

import java.util.LinkedList;
import java.util.List;

import olap.db.DBColumn;

public class DimensionWrapper {
	
	private String name, ptr;
	private Dimension dimension;
	
	public DimensionWrapper(String name, String ptr){
		this.name = name;
		this.ptr = ptr;
	}
	
	public void setDimension(Dimension dimension){
		this.dimension = dimension;
	}
	
	public List<DBColumn> getColumns(){
		List<DBColumn> columns = new LinkedList<DBColumn>();
		columns.addAll(dimension.getColumns(name+"_"));
		return columns;		
	}
	
	public Dimension getDimension(){
		return this.dimension;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPtr() {
		return ptr;
	}

	public void setPtr(String ptr) {
		this.ptr = ptr;
	}
	
	public String toString(){
		return "DIMENSION_W:\n\tnombre = " + name + "; ptr = " + ptr + "; dimension = " + dimension + "\n";
	}
}
