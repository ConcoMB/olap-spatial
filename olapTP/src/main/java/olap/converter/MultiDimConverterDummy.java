package olap.converter;

public class MultiDimConverterDummy implements MultiDimConverter{

	private String mdName;
	
	public MultiDimConverterDummy(String multidimName) {
		this.mdName = multidimName;
	}

	@Override
	public String multidim() {
		return mdName;
	}

	public void setMultidimName(String multidimName) {
		this.mdName = multidimName;
	}

	@Override
	public String column() {
		return mdName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		MultiDimConverterDummy other = (MultiDimConverterDummy) obj;
		if (mdName == null) {
			if (other.mdName != null)
				return false;
		} else if (!mdName.equals(other.mdName))
			return false;
		return true;
	}
}
