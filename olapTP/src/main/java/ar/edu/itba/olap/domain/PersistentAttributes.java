package ar.edu.itba.olap.domain;


public abstract class PersistentAttributes {

	private int id = -1;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
