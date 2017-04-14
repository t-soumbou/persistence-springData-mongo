package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.BadgeRecord;
import org.demo.persistence.BadgePersistence;

/**
 * Badge persistence service - SpringMongo implementation 
 */
@Named("BadgePersistence")
public class BadgePersistenceSpringMongo implements BadgePersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<BadgeRecord> entityClass = BadgeRecord.class;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	//-------------------------------------------------------------------------------------
	// Util methods 
	//-------------------------------------------------------------------------------------
	/**
	 * Creates a new bean instance and set its primary key value(s)
	 * 
	 * @param badgeNumber
	 * @return the new instance
	 */
	private BadgeRecord newInstanceWithPrimaryKey(Integer badgeNumber) {
		BadgeRecord record = new BadgeRecord();
        record.setBadgeNumber(badgeNumber); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(BadgeRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(BadgeRecord record) {
		Update update = new Update();
		update.set("authorizationLevel", record.getAuthorizationLevel());
		update.set("endOfValidity", record.getEndOfValidity());
		return update;
	}

	protected Query queryForIdentifyBean(BadgeRecord record) {
		Query query = new Query(Criteria.where("badgeNumber").is(record.getBadgeNumber()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), BadgeRecord.class);
	}
	
	@Override
	public BadgeRecord create(BadgeRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(BadgeRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(Integer badgeNumber) {
		BadgeRecord record = newInstanceWithPrimaryKey(badgeNumber);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(BadgeRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(Integer badgeNumber) {
		BadgeRecord record = newInstanceWithPrimaryKey(badgeNumber);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<BadgeRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public BadgeRecord findById(Integer badgeNumber) {
        BadgeRecord record = newInstanceWithPrimaryKey(badgeNumber);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public BadgeRecord save(BadgeRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(BadgeRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
