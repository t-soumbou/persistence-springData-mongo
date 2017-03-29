
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
import org.demo.data.record.CustomerRecord;
import org.demo.persistence.CustomerPersistence;
import org.demo.persistence.impl.springMongo.commons.GenericSpringDataMongoDb;

/**
 * Customer persistence service - SpringMongo implementation 
 * 
 * @author Telosys
 *
 */
@Named("CustomerPersistence")
public class CustomerPersistenceSpringMongo extends GenericSpringDataMongoDb<CustomerRecord> implements CustomerPersistence {

	/**
	 * Constructor
	 */
	public CustomerPersistenceSpringMongo() {
		super("customer", CustomerRecord.class);
	}

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

	//-------------------------------------------------------------------------------------
	// Generic DAO abstract methods implementations
	//-------------------------------------------------------------------------------------
	@Override
	protected void setAutoIncrementedKey(CustomerRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	@Override
	protected Update updateQuery(CustomerRecord record) {
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

	@Override
	protected Query queryForIdentifyBean(CustomerRecord record, MongoConverter converter) {
		Object keycode = converter.convertToMongoType(record.getCode());
		Query query = new Query(Criteria.where("code").is(keycode));
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
	public CustomerRecord create(CustomerRecord record) {
		super.doInsert(record);
		return record;
	}

	@Override
	public boolean delete(CustomerRecord record) {
		if(super.doExists(record))
			return super.doDelete(record);
		return false;
	}

	@Override
	public boolean deleteById(String code) {
		CustomerRecord record = newInstanceWithPrimaryKey(code);
		if(super.doExists(record))
			return super.doDelete(record);
		return false;
	}

	@Override
	public boolean exists(CustomerRecord record) {
		return super.doExists(record);
	}

	@Override
	public boolean exists(String code) {
		CustomerRecord record = newInstanceWithPrimaryKey(code);
		return super.doExists(record);
	}

	@Override
	public List<CustomerRecord> findAll() {
		return super.doSelectAll();
	}

	@Override
	public CustomerRecord findById(String code) {
        CustomerRecord record = newInstanceWithPrimaryKey(code);
		return super.doSelect(record);
	}

	@Override
	public CustomerRecord save(CustomerRecord record) {
		if (super.doExists(record)) {
			super.doUpdate(record);
		} else {
			super.doInsert(record);
		}
        return record;
	}

	@Override
	public boolean update(CustomerRecord record) {
		return super.doUpdate(record);	
	}
}