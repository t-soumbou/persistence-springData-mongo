package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.ShopRecord;
import org.demo.persistence.ShopPersistence;

/**
 * Shop persistence service - SpringMongo implementation 
 */
@Named("ShopPersistence")
public class ShopPersistenceSpringMongo implements ShopPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<ShopRecord> entityClass = ShopRecord.class;

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
	private ShopRecord newInstanceWithPrimaryKey(String code) {
		ShopRecord record = new ShopRecord();
        record.setCode(code); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(ShopRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(ShopRecord record) {
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

	protected Query queryForIdentifyBean(ShopRecord record) {
		Query query = new Query(Criteria.where("code").is(record.getCode()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), ShopRecord.class);
	}
	
	@Override
	public ShopRecord create(ShopRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(ShopRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(String code) {
		ShopRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(ShopRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(String code) {
		ShopRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<ShopRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public ShopRecord findById(String code) {
        ShopRecord record = newInstanceWithPrimaryKey(code);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public ShopRecord save(ShopRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(ShopRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
