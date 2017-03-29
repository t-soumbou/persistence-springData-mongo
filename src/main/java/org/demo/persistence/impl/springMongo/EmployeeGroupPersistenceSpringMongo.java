
/*
 * Created on 2017-03-29 ( Date ISO 2017-03-29 - Time 11:30:14 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.0.0
 */
package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.demo.data.record.EmployeeGroupRecord;
import org.demo.persistence.EmployeeGroupPersistence;
import org.demo.persistence.impl.springMongo.commons.GenericSpringDataMongoDb;

/**
 * EmployeeGroup persistence service - SpringMongo implementation 
 * 
 * @author Telosys
 *
 */
@Named("EmployeeGroupPersistence")
public class EmployeeGroupPersistenceSpringMongo extends GenericSpringDataMongoDb<EmployeeGroupRecord> implements EmployeeGroupPersistence {

	/**
	 * Constructor
	 */
	public EmployeeGroupPersistenceSpringMongo() {
		super("employeeGroup", EmployeeGroupRecord.class);
	}

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

	//-------------------------------------------------------------------------------------
	// Generic DAO abstract methods implementations
	//-------------------------------------------------------------------------------------
	@Override
	protected void setAutoIncrementedKey(EmployeeGroupRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	@Override
	protected Update updateQuery(EmployeeGroupRecord record) {
		Update update = new Update();
		return update;
	}

	@Override
	protected Query queryForIdentifyBean(EmployeeGroupRecord record, MongoConverter converter) {
		Object keyemployeeCode = converter.convertToMongoType(record.getEmployeeCode());
		Object keygroupId = converter.convertToMongoType(record.getGroupId());
		Query query = new Query(Criteria.where("employeeCode").is(keyemployeeCode) .and("groupId").is(keygroupId));
	    return query;
	}
	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return super.doCountAll();
	}
	
	@Override
	public EmployeeGroupRecord create(EmployeeGroupRecord record) {
		super.doInsert(record);
		return record;
	}

	@Override
	public boolean delete(EmployeeGroupRecord record) {
		if(super.doExists(record))
			return super.doDelete(record);
		return false;
	}

	@Override
	public boolean deleteById(String employeeCode, Short groupId) {
		EmployeeGroupRecord record = newInstanceWithPrimaryKey(employeeCode, groupId);
		if(super.doExists(record))
			return super.doDelete(record);
		return false;
	}

	@Override
	public boolean exists(EmployeeGroupRecord record) {
		return super.doExists(record);
	}

	@Override
	public boolean exists(String employeeCode, Short groupId) {
		EmployeeGroupRecord record = newInstanceWithPrimaryKey(employeeCode, groupId);
		return super.doExists(record);
	}

	@Override
	public List<EmployeeGroupRecord> findAll() {
		return super.doSelectAll();
	}

	@Override
	public EmployeeGroupRecord findById(String employeeCode, Short groupId) {
        EmployeeGroupRecord record = newInstanceWithPrimaryKey(employeeCode, groupId);
		return super.doSelect(record);
	}

	@Override
	public EmployeeGroupRecord save(EmployeeGroupRecord record) {
		if (super.doExists(record)) {
			super.doUpdate(record);
		} else {
			super.doInsert(record);
		}
        return record;
	}

	@Override
	public boolean update(EmployeeGroupRecord record) {
		return super.doUpdate(record);	
	}
}
