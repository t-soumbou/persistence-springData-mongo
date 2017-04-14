package org.demo.persistence.impl.springMongo.commons;


public class Sequence {
	private String id;
	private long seq;
	
	public Sequence(String id, long seq) {
		this.setId(id);
		this.seq = seq;
	}
	
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
