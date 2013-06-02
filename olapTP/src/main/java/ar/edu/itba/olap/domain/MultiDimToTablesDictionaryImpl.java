package ar.edu.itba.olap.domain;


public class MultiDimToTablesDictionaryImpl implements MultiDimToTablesDictionary{

	private String multidimName;
	private String columnName;
	
	public MultiDimToTablesDictionaryImpl(String multidimName, String columnName) {
		this.multidimName = multidimName;
		this.columnName = columnName;
	}

	@Override
	public String getMultidimName() {
		return multidimName;
	}

	public void setMultidimName(String multidimName) {
		this.multidimName = multidimName;
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
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
		MultiDimToTablesDictionaryImpl other = (MultiDimToTablesDictionaryImpl) obj;
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
