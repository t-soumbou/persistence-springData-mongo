package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.PublisherRecord;
import org.demo.persistence.PublisherPersistence;

/**
 * Publisher persistence service - SpringMongo implementation 
 */
@Named("PublisherPersistence")
public class PublisherPersistenceSpringMongo implements PublisherPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<PublisherRecord> entityClass = PublisherRecord.class;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	//-------------------------------------------------------------------------------------
	// Util methods 
	//-------------------------------------------------------------------------------------
	/**
	 * Creates a new bean instance and set its primary key value(s)
	 * 
	 * @param code
	 * @return the new instance
	 */
	private PublisherRecord newInstanceWithPrimaryKey(Integer code) {
		PublisherRecord record = new PublisherRecord();
        record.setCode(code); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(PublisherRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(PublisherRecord record) {
		Update update = new Update();
		update.set("countryCode", record.getCountryCode());
		update.set("name", record.getName());
		update.set("email", record.getEmail());
		update.set("contact", record.getContact());
		update.set("city", record.getCity());
		update.set("zipCode", record.getZipCode());
		update.set("phone", record.getPhone());
		return update;
	}

	protected Query queryForIdentifyBean(PublisherRecord record) {
		Query query = new Query(Criteria.where("code").is(record.getCode()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), PublisherRecord.class);
	}
	
	@Override
	public PublisherRecord create(PublisherRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(PublisherRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(Integer code) {
		PublisherRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(PublisherRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(Integer code) {
		PublisherRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<PublisherRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public PublisherRecord findById(Integer code) {
        PublisherRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public PublisherRecord save(PublisherRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(PublisherRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
