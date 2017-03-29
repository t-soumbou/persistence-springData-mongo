
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
import org.demo.data.record.CountryRecord;
import org.demo.persistence.CountryPersistence;
import org.demo.persistence.impl.springMongo.commons.GenericSpringDataMongoDb;

/**
 * Country persistence service - SpringMongo implementation 
 * 
 * @author Telosys
 *
 */
@Named("CountryPersistence")
public class CountryPersistenceSpringMongo extends GenericSpringDataMongoDb<CountryRecord> implements CountryPersistence {

	/**
	 * Constructor
	 */
	public CountryPersistenceSpringMongo() {
		super("country", CountryRecord.class);
	}

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

	//-------------------------------------------------------------------------------------
	// Generic DAO abstract methods implementations
	//-------------------------------------------------------------------------------------
	@Override
	protected void setAutoIncrementedKey(CountryRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	@Override
	protected Update updateQuery(CountryRecord record) {
		Update update = new Update();
		update.set("name", record.getName());
		return update;
	}

	@Override
	protected Query queryForIdentifyBean(CountryRecord record, MongoConverter converter) {
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
	public CountryRecord create(CountryRecord record) {
		super.doInsert(record);
		return record;
	}

	@Override
	public boolean delete(CountryRecord record) {
		if(super.doExists(record))
			return super.doDelete(record);
		return false;
	}

	@Override
	public boolean deleteById(String code) {
		CountryRecord record = newInstanceWithPrimaryKey(code);
		if(super.doExists(record))
			return super.doDelete(record);
		return false;
	}

	@Override
	public boolean exists(CountryRecord record) {
		return super.doExists(record);
	}

	@Override
	public boolean exists(String code) {
		CountryRecord record = newInstanceWithPrimaryKey(code);
		return super.doExists(record);
	}

	@Override
	public List<CountryRecord> findAll() {
		return super.doSelectAll();
	}

	@Override
	public CountryRecord findById(String code) {
        CountryRecord record = newInstanceWithPrimaryKey(code);
		return super.doSelect(record);
	}

	@Override
	public CountryRecord save(CountryRecord record) {
		if (super.doExists(record)) {
			super.doUpdate(record);
		} else {
			super.doInsert(record);
		}
        return record;
	}

	@Override
	public boolean update(CountryRecord record) {
		return super.doUpdate(record);	
	}
}