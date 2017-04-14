package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;

import org.demo.data.record.BookOrderRecord;
import org.demo.persistence.BookOrderPersistence;

/**
 * BookOrder persistence service - SpringMongo implementation 
 */
@Named("BookOrderPersistence")
public class BookOrderPersistenceSpringMongo implements BookOrderPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<BookOrderRecord> entityClass = BookOrderRecord.class;

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
	private BookOrderRecord newInstanceWithPrimaryKey(Integer id) {
		BookOrderRecord record = new BookOrderRecord();
        record.setId(id); 
		return record;
	}

	@SuppressWarnings("unused")
	private void setAutoIncrementedKey(BookOrderRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	private Update updateQuery(BookOrderRecord record) {
		Update update = new Update();
		update.set("shopCode", record.getShopCode());
		update.set("customerCode", record.getCustomerCode());
		update.set("employeeCode", record.getEmployeeCode());
		update.set("date", record.getDate());
		update.set("state", record.getState());
		return update;
	}

	protected Query queryForIdentifyBean(BookOrderRecord record) {
		Query query = new Query(Criteria.where("id").is(record.getId()));
	    return query;
	}

	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), BookOrderRecord.class);
	}
	
	@Override
	public BookOrderRecord create(BookOrderRecord record) {
		mongoTemplate.insert(record);
		return record;
	}

	@Override
	public boolean delete(BookOrderRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(Integer id) {
		BookOrderRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(BookOrderRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(Integer id) {
		BookOrderRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<BookOrderRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public BookOrderRecord findById(Integer id) {
        BookOrderRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public BookOrderRecord save(BookOrderRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(BookOrderRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
