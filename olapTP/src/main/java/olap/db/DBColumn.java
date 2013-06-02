package olap.db;

public class DBColumn {
	
	private String name, type;
	private boolean isPrimaryKey, notNull;
	
	public DBColumn(String name, String type, boolean isPrimaryKey, boolean notNull) {
		this.name = name;
		this.type = type;
		this.isPrimaryKey = isPrimaryKey;
		this.notNull = notNull;
	}

	public String getType() {
		return type;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DBColumn other = (DBColumn) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	public String toString(){
		return "COLUMNA:\n\t nombre = " + name + "; tipo = " + type + "; " + (isPrimaryKey ? "PK; ": "") +  (notNull ? "not null; ": "") + "\n";
	}
}
