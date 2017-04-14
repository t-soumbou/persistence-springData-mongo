package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.CountryRecord;
import org.demo.persistence.CountryPersistence;

/**
 * Country persistence service - SpringMongo implementation 
 */
@Named("CountryPersistence")
public class CountryPersistenceSpringMongo implements CountryPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<CountryRecord> entityClass = CountryRecord.class;

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
	private CountryRecord newInstanceWithPrimaryKey(String code) {
		CountryRecord record = new CountryRecord();
        record.setCode(code); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(CountryRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(CountryRecord record) {
		Update update = new Update();
		update.set("name", record.getName());
		return update;
	}

	protected Query queryForIdentifyBean(CountryRecord record) {
		Query query = new Query(Criteria.where("code").is(record.getCode()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), CountryRecord.class);
	}
	
	@Override
	public CountryRecord create(CountryRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(CountryRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(String code) {
		CountryRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(CountryRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(String code) {
		CountryRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<CountryRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public CountryRecord findById(String code) {
        CountryRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public CountryRecord save(CountryRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(CountryRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
