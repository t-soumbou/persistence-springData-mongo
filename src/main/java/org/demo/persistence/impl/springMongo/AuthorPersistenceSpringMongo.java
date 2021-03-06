package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.AuthorRecord;
import org.demo.persistence.AuthorPersistence;

/**
 * Author persistence service - SpringMongo implementation 
 *
 */
@Named("AuthorPersistence")
public class AuthorPersistenceSpringMongo implements AuthorPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<AuthorRecord> entityClass = AuthorRecord.class;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	//-------------------------------------------------------------------------------------
	// Util methods 
	//-------------------------------------------------------------------------------------
	/**
	 * Creates a new bean instance and set its primary key value(s)
	 * 
	 * @param id
	 * @return the new instance
	 */
	private AuthorRecord newInstanceWithPrimaryKey(Integer id) {
		AuthorRecord record = new AuthorRecord();
        record.setId(id); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(AuthorRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(AuthorRecord record) {
		Update update = new Update();
		update.set("firstName", record.getFirstName());
		update.set("lastName", record.getLastName());
		return update;
	}

	protected Query queryForIdentifyBean(AuthorRecord record) {
		Query query = new Query(Criteria.where("id").is(record.getId()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), AuthorRecord.class);
	}
	
	@Override
	public AuthorRecord create(AuthorRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(AuthorRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(Integer id) {
		AuthorRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(AuthorRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(Integer id) {
		AuthorRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<AuthorRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public AuthorRecord findById(Integer id) {
        AuthorRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public AuthorRecord save(AuthorRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(AuthorRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
