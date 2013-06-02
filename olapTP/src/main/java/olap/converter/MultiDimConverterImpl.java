package olap.converter;

public class MultiDimConverterImpl implements MultiDimConverter{

	private String mdName, columnName;
	
	public MultiDimConverterImpl(String multidimName, String columnName) {
		this.mdName = multidimName;
		this.columnName = columnName;
	}

	@Override
	public String multidim() {
		return mdName;
	}

	@Override
	public String column() {
		return columnName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((mdName == null) ? 0 : mdName.hashCode());
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
		MultiDimConverterImpl other = (MultiDimConverterImpl) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (mdName == null) {
			if (other.mdName != null)
				return false;
		} else if (!mdName.equals(other.mdName))
			return false;
		return true;
	}
}
