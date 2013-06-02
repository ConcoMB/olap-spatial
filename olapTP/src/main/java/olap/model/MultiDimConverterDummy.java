package olap.model;

public class MultiDimConverterDummy implements MultiDimConverter{

	private String multidimName;
	
	public MultiDimConverterDummy(String multidimName) {
		this.multidimName = multidimName;
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
		return multidimName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		MultiDimConverterDummy other = (MultiDimConverterDummy) obj;
		if (multidimName == null) {
			if (other.multidimName != null)
				return false;
		} else if (!multidimName.equals(other.multidimName))
			return false;
		return true;
	}
	
	
}
