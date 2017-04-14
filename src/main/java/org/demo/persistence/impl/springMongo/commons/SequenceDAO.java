package org.demo.persistence.impl.springMongo.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class SequenceDAO {

	@Autowired
	private MongoOperations mongoOps;

	/**
	 * Sets the MongoOps implementation.
	 *
	 * @param mongoOps
	 *            the mongoOps to set
	 */
	public void setMongoOps(MongoOperations mongoOps) {
		this.mongoOps = mongoOps;
	}
	
	public SequenceDAO(MongoOperations mongoOps, String name) {
		this.mongoOps = mongoOps;
		mongoOps.save(new Sequence(name,0L));
	}

	public long currVal(String key) {
		Query query = new Query(Criteria.where("id").is(key));
		Sequence seq = mongoOps.findOne(query, Sequence.class);
		return seq.getSeq();
	}

	public long nextVal(String key) throws Exception {
		Query query = new Query(Criteria.where("id").is(key));
		Update update = new Update();
		update.inc("seq", 1);
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		Sequence seq = mongoOps.findAndModify(query, update, options, Sequence.class);
		if (seq == null) {
			throw new Exception("Unable to get sequence id for key : " + key);
		}
		return seq.getSeq();
	}

	public boolean delete(String key) {
		Query query = new Query(Criteria.where("id").is(key));
		mongoOps.remove(query, Sequence.class);
		return true;
	}
}
