package ar.edu.itba.olap.domain;

import java.util.List;

public class Table {
	
	private String name;
	private List<Column> columns;

	public Table(String name, List<Column> columns) throws IllegalArgumentException{
		this.setName(name);
		this.columns = columns;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name == null || name.equals("")){
			throw new IllegalArgumentException();
		}else{
			this.name = name;
		}
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
