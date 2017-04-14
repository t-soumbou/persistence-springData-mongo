package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.SynopsisRecord;
import org.demo.persistence.SynopsisPersistence;

/**
 * Synopsis persistence service - SpringMongo implementation 
 */
@Named("SynopsisPersistence")
public class SynopsisPersistenceSpringMongo implements SynopsisPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<SynopsisRecord> entityClass = SynopsisRecord.class;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	//-------------------------------------------------------------------------------------
	// Util methods 
	//-------------------------------------------------------------------------------------
	/**
	 * Creates a new bean instance and set its primary key value(s)
	 * 
	 * @param bookId
	 * @return the new instance
	 */
	private SynopsisRecord newInstanceWithPrimaryKey(Integer bookId) {
		SynopsisRecord record = new SynopsisRecord();
        record.setBookId(bookId); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(SynopsisRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(SynopsisRecord record) {
		Update update = new Update();
		update.set("synopsis", record.getSynopsis());
		return update;
	}

	protected Query queryForIdentifyBean(SynopsisRecord record) {
		Query query = new Query(Criteria.where("bookId").is(record.getBookId()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), SynopsisRecord.class);
	}
	
	@Override
	public SynopsisRecord create(SynopsisRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(SynopsisRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(Integer bookId) {
		SynopsisRecord record = newInstanceWithPrimaryKey(bookId);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(SynopsisRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(Integer bookId) {
		SynopsisRecord record = newInstanceWithPrimaryKey(bookId);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<SynopsisRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public SynopsisRecord findById(Integer bookId) {
        SynopsisRecord record = newInstanceWithPrimaryKey(bookId);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public SynopsisRecord save(SynopsisRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(SynopsisRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
