package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.BookOrderItemRecord;
import org.demo.persistence.BookOrderItemPersistence;

/**
 * BookOrderItem persistence service - SpringMongo implementation 
 */
@Named("BookOrderItemPersistence")
public class BookOrderItemPersistenceSpringMongo implements BookOrderItemPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<BookOrderItemRecord> entityClass = BookOrderItemRecord.class;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	//-------------------------------------------------------------------------------------
	// Util methods 
	//-------------------------------------------------------------------------------------
	/**
	 * Creates a new bean instance and set its primary key value(s)
	 * 
	 * @param bookOrderId
	 * @param bookId
	 * @return the new instance
	 */
	private BookOrderItemRecord newInstanceWithPrimaryKey(Integer bookOrderId, Integer bookId) {
		BookOrderItemRecord record = new BookOrderItemRecord();
        record.setBookOrderId(bookOrderId); 
        record.setBookId(bookId); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(BookOrderItemRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(BookOrderItemRecord record) {
		Update update = new Update();
		update.set("quantity", record.getQuantity());
		update.set("price", record.getPrice());
		return update;
	}

	protected Query queryForIdentifyBean(BookOrderItemRecord record) {
		Query query = new Query(Criteria.where("bookOrderId").is(record.getBookOrderId()) .and("bookId").is(record.getBookId()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), BookOrderItemRecord.class);
	}
	
	@Override
	public BookOrderItemRecord create(BookOrderItemRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(BookOrderItemRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(Integer bookOrderId, Integer bookId) {
		BookOrderItemRecord record = newInstanceWithPrimaryKey(bookOrderId, bookId);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(BookOrderItemRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(Integer bookOrderId, Integer bookId) {
		BookOrderItemRecord record = newInstanceWithPrimaryKey(bookOrderId, bookId);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<BookOrderItemRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public BookOrderItemRecord findById(Integer bookOrderId, Integer bookId) {
        BookOrderItemRecord record = newInstanceWithPrimaryKey(bookOrderId, bookId);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public BookOrderItemRecord save(BookOrderItemRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(BookOrderItemRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
