package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.EmployeeGroupRecord;
import org.demo.persistence.EmployeeGroupPersistence;

/**
 * EmployeeGroup persistence service - SpringMongo implementation 
 */
@Named("EmployeeGroupPersistence")
public class EmployeeGroupPersistenceSpringMongo implements EmployeeGroupPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<EmployeeGroupRecord> entityClass = EmployeeGroupRecord.class;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	//-------------------------------------------------------------------------------------
	// Util methods 
	//-------------------------------------------------------------------------------------
	/**
	 * Creates a new bean instance and set its primary key value(s)
	 * 
	 * @param employeeCode
	 * @param groupId
	 * @return the new instance
	 */
	private EmployeeGroupRecord newInstanceWithPrimaryKey(String employeeCode, Short groupId) {
		EmployeeGroupRecord record = new EmployeeGroupRecord();
        record.setEmployeeCode(employeeCode); 
        record.setGroupId(groupId); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(EmployeeGroupRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(EmployeeGroupRecord record) {
		Update update = new Update();
		return update;
	}

	protected Query queryForIdentifyBean(EmployeeGroupRecord record) {
		Query query = new Query(Criteria.where("employeeCode").is(record.getEmployeeCode()) .and("groupId").is(record.getGroupId()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), EmployeeGroupRecord.class);
	}
	
	@Override
	public EmployeeGroupRecord create(EmployeeGroupRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(EmployeeGroupRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(String employeeCode, Short groupId) {
		EmployeeGroupRecord record = newInstanceWithPrimaryKey(employeeCode, groupId);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(EmployeeGroupRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(String employeeCode, Short groupId) {
		EmployeeGroupRecord record = newInstanceWithPrimaryKey(employeeCode, groupId);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<EmployeeGroupRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public EmployeeGroupRecord findById(String employeeCode, Short groupId) {
        EmployeeGroupRecord record = newInstanceWithPrimaryKey(employeeCode, groupId);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public EmployeeGroupRecord save(EmployeeGroupRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(EmployeeGroupRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
