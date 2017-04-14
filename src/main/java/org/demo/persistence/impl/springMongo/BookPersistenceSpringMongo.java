package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.BookRecord;
import org.demo.persistence.BookPersistence;

/**
 * Book persistence service - SpringMongo implementation 
 */
@Named("BookPersistence")
public class BookPersistenceSpringMongo implements BookPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<BookRecord> entityClass = BookRecord.class;

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
	private BookRecord newInstanceWithPrimaryKey(Integer id) {
		BookRecord record = new BookRecord();
        record.setId(id); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(BookRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(BookRecord record) {
		Update update = new Update();
		update.set("publisherId", record.getPublisherId());
		update.set("authorId", record.getAuthorId());
		update.set("isbn", record.getIsbn());
		update.set("title", record.getTitle());
		update.set("price", record.getPrice());
		update.set("quantity", record.getQuantity());
		update.set("discount", record.getDiscount());
		update.set("availability", record.getAvailability());
		update.set("bestSeller", record.getBestSeller());
		return update;
	}

	protected Query queryForIdentifyBean(BookRecord record) {
		Query query = new Query(Criteria.where("id").is(record.getId()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), BookRecord.class);
	}
	
	@Override
	public BookRecord create(BookRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(BookRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(Integer id) {
		BookRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(BookRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(Integer id) {
		BookRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<BookRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public BookRecord findById(Integer id) {
        BookRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public BookRecord save(BookRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(BookRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
