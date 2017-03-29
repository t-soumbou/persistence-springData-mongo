
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
import org.demo.data.record.ShopRecord;
import org.demo.persistence.ShopPersistence;
import org.demo.persistence.impl.springMongo.commons.GenericSpringDataMongoDb;

/**
 * Shop persistence service - SpringMongo implementation 
 * 
 * @author Telosys
 *
 */
@Named("ShopPersistence")
public class ShopPersistenceSpringMongo extends GenericSpringDataMongoDb<ShopRecord> implements ShopPersistence {

	/**
	 * Constructor
	 */
	public ShopPersistenceSpringMongo() {
		super("shop", ShopRecord.class);
	}

	/**
	 * Creates a new bean instance and set its primary key value(s)
	 * 
	 * @param code
	 * @return the new instance
	 */
	private ShopRecord newInstanceWithPrimaryKey(String code) {
		ShopRecord record = new ShopRecord();
        record.setCode(code); 
		return record;
	}

	//-------------------------------------------------------------------------------------
	// Generic DAO abstract methods implementations
	//-------------------------------------------------------------------------------------
	@Override
	protected void setAutoIncrementedKey(ShopRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	@Override
	protected Update updateQuery(ShopRecord record) {
		Update update = new Update();
		update.set("name", record.getName());
		update.set("address1", record.getAddress1());
		update.set("address2", record.getAddress2());
		update.set("zipCode", record.getZipCode());
		update.set("city", record.getCity());
		update.set("countryCode", record.getCountryCode());
		update.set("phone", record.getPhone());
		update.set("email", record.getEmail());
		update.set("executive", record.getExecutive());
		return update;
	}

	@Override
	protected Query queryForIdentifyBean(ShopRecord record, MongoConverter converter) {
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
	public ShopRecord create(ShopRecord record) {
		super.doInsert(record);
		return record;
	}

	@Override
	public boolean delete(ShopRecord record) {
		if(super.doExists(record))
			return super.doDelete(record);
		return false;
	}

	@Override
	public boolean deleteById(String code) {
		ShopRecord record = newInstanceWithPrimaryKey(code);
		if(super.doExists(record))
			return super.doDelete(record);
		return false;
	}

	@Override
	public boolean exists(ShopRecord record) {
		return super.doExists(record);
	}

	@Override
	public boolean exists(String code) {
		ShopRecord record = newInstanceWithPrimaryKey(code);
		return super.doExists(record);
	}

	@Override
	public List<ShopRecord> findAll() {
		return super.doSelectAll();
	}

	@Override
	public ShopRecord findById(String code) {
        ShopRecord record = newInstanceWithPrimaryKey(code);
		return super.doSelect(record);
	}

	@Override
	public ShopRecord save(ShopRecord record) {
		if (super.doExists(record)) {
			super.doUpdate(record);
		} else {
			super.doInsert(record);
		}
        return record;
	}

	@Override
	public boolean update(ShopRecord record) {
		return super.doUpdate(record);	
	}
}