package olap.converter;

public class MultiDimConverter {

	private String multidimName, columnName;
	
	public MultiDimConverter(String multidimName, String columnName) {
		this.multidimName = multidimName;
		this.columnName = columnName;
	}

	public String getMultidimName() {
		return multidimName;
	}
	
	public void setMultidimName(String m) {
		multidimName = m;
	}

	public void setColumnName(String c) {
		columnName = c;
	}
	
	public String getColumnName() {
		return columnName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((multidimName == null) ? 0 : multidimName.hashCode());
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
		MultiDimConverter other = (MultiDimConverter) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (multidimName == null) {
			if (other.multidimName != null)
				return false;
		} else if (!multidimName.equals(other.multidimName))
			return false;
		return true;
	}
}
