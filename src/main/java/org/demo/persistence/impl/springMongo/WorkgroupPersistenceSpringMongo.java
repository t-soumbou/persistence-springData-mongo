package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.WorkgroupRecord;
import org.demo.persistence.WorkgroupPersistence;

/**
 * Workgroup persistence service - SpringMongo implementation 
 */
@Named("WorkgroupPersistence")
public class WorkgroupPersistenceSpringMongo implements WorkgroupPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<WorkgroupRecord> entityClass = WorkgroupRecord.class;

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
	private WorkgroupRecord newInstanceWithPrimaryKey(Short id) {
		WorkgroupRecord record = new WorkgroupRecord();
        record.setId(id); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(WorkgroupRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(WorkgroupRecord record) {
		Update update = new Update();
		update.set("name", record.getName());
		update.set("description", record.getDescription());
		update.set("creationDate", record.getCreationDate());
		return update;
	}

	protected Query queryForIdentifyBean(WorkgroupRecord record) {
		Query query = new Query(Criteria.where("id").is(record.getId()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), WorkgroupRecord.class);
	}
	
	@Override
	public WorkgroupRecord create(WorkgroupRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(WorkgroupRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(Short id) {
		WorkgroupRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(WorkgroupRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(Short id) {
		WorkgroupRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<WorkgroupRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public WorkgroupRecord findById(Short id) {
        WorkgroupRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public WorkgroupRecord save(WorkgroupRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(WorkgroupRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
