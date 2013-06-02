package olap.db;

public class MultiDimMapper {

	private String multidim, column;
	
	public MultiDimMapper(String multidim, String column) {
		this.multidim = multidim;
		this.column = column;
	}

	public String getMultidim() {
		return multidim;
	}
	
	public void setMultidim(String m) {
		multidim = m;
	}

	public void setColumn(String c) {
		column = c;
	}
	
	public String getColumn() {
		return column != null ? column : multidim;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((column == null) ? 0 : column.hashCode());
		result = prime * result
				+ ((multidim == null) ? 0 : multidim.hashCode());
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
		MultiDimMapper other = (MultiDimMapper) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (multidim == null) {
			if (other.multidim != null)
				return false;
		} else if (!multidim.equals(other.multidim))
			return false;
		return true;
	}
}
