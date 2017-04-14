package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.EmployeeRecord;
import org.demo.persistence.EmployeePersistence;

/**
 * Employee persistence service - SpringMongo implementation 
 */
@Named("EmployeePersistence")
public class EmployeePersistenceSpringMongo implements EmployeePersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<EmployeeRecord> entityClass = EmployeeRecord.class;

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
	private EmployeeRecord newInstanceWithPrimaryKey(String code) {
		EmployeeRecord record = new EmployeeRecord();
        record.setCode(code); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(EmployeeRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(EmployeeRecord record) {
		Update update = new Update();
		update.set("shopCode", record.getShopCode());
		update.set("firstName", record.getFirstName());
		update.set("lastName", record.getLastName());
		update.set("manager", record.getManager());
		update.set("badgeNumber", record.getBadgeNumber());
		update.set("email", record.getEmail());
		return update;
	}

	protected Query queryForIdentifyBean(EmployeeRecord record) {
		Query query = new Query(Criteria.where("code").is(record.getCode()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), EmployeeRecord.class);
	}
	
	@Override
	public EmployeeRecord create(EmployeeRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(EmployeeRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(String code) {
		EmployeeRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(EmployeeRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(String code) {
		EmployeeRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<EmployeeRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public EmployeeRecord findById(String code) {
        EmployeeRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public EmployeeRecord save(EmployeeRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(EmployeeRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
