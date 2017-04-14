package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.CustomerRecord;
import org.demo.persistence.CustomerPersistence;

/**
 * Customer persistence service - SpringMongo implementation 
 */
@Named("CustomerPersistence")
public class CustomerPersistenceSpringMongo implements CustomerPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<CustomerRecord> entityClass = CustomerRecord.class;

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
	private CustomerRecord newInstanceWithPrimaryKey(String code) {
		CustomerRecord record = new CustomerRecord();
        record.setCode(code); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(CustomerRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(CustomerRecord record) {
		Update update = new Update();
		update.set("countryCode", record.getCountryCode());
		update.set("firstName", record.getFirstName());
		update.set("lastName", record.getLastName());
		update.set("login", record.getLogin());
		update.set("password", record.getPassword());
		update.set("age", record.getAge());
		update.set("city", record.getCity());
		update.set("zipCode", record.getZipCode());
		update.set("phone", record.getPhone());
		update.set("reviewer", record.getReviewer());
		return update;
	}

	protected Query queryForIdentifyBean(CustomerRecord record) {
		Query query = new Query(Criteria.where("code").is(record.getCode()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), CustomerRecord.class);
	}
	
	@Override
	public CustomerRecord create(CustomerRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(CustomerRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(String code) {
		CustomerRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(CustomerRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(String code) {
		CustomerRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<CustomerRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public CustomerRecord findById(String code) {
        CustomerRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public CustomerRecord save(CustomerRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(CustomerRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
