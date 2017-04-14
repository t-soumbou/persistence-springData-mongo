package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.ReviewRecord;
import org.demo.persistence.ReviewPersistence;

/**
 * Review persistence service - SpringMongo implementation 
 */
@Named("ReviewPersistence")
public class ReviewPersistenceSpringMongo implements ReviewPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<ReviewRecord> entityClass = ReviewRecord.class;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	//-------------------------------------------------------------------------------------
	// Util methods 
	//-------------------------------------------------------------------------------------
	/**
	 * Creates a new bean instance and set its primary key value(s)
	 * 
	 * @param customerCode
	 * @param bookId
	 * @return the new instance
	 */
	private ReviewRecord newInstanceWithPrimaryKey(String customerCode, Integer bookId) {
		ReviewRecord record = new ReviewRecord();
        record.setCustomerCode(customerCode); 
        record.setBookId(bookId); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(ReviewRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(ReviewRecord record) {
		Update update = new Update();
		update.set("reviewText", record.getReviewText());
		update.set("reviewNote", record.getReviewNote());
		update.set("creation", record.getCreation());
		update.set("lastUpdate", record.getLastUpdate());
		return update;
	}

	protected Query queryForIdentifyBean(ReviewRecord record) {
		Query query = new Query(Criteria.where("customerCode").is(record.getCustomerCode()) .and("bookId").is(record.getBookId()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), ReviewRecord.class);
	}
	
	@Override
	public ReviewRecord create(ReviewRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(ReviewRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(String customerCode, Integer bookId) {
		ReviewRecord record = newInstanceWithPrimaryKey(customerCode, bookId);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(ReviewRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(String customerCode, Integer bookId) {
		ReviewRecord record = newInstanceWithPrimaryKey(customerCode, bookId);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<ReviewRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public ReviewRecord findById(String customerCode, Integer bookId) {
        ReviewRecord record = newInstanceWithPrimaryKey(customerCode, bookId);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public ReviewRecord save(ReviewRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(ReviewRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
