
/*
 * Created on 2017-03-29 ( Date ISO 2017-03-29 - Time 11:30:13 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.0.0
 */
package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.demo.data.record.BookRecord;
import org.demo.persistence.BookPersistence;
import org.demo.persistence.impl.springMongo.commons.GenericSpringDataMongoDb;

/**
 * Book persistence service - SpringMongo implementation 
 * 
 * @author Telosys
 *
 */
@Named("BookPersistence")
public class BookPersistenceSpringMongo extends GenericSpringDataMongoDb<BookRecord> implements BookPersistence {

	/**
	 * Constructor
	 */
	public BookPersistenceSpringMongo() {
		super("book", BookRecord.class);
	}

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

	//-------------------------------------------------------------------------------------
	// Generic DAO abstract methods implementations
	//-------------------------------------------------------------------------------------
	@Override
	protected void setAutoIncrementedKey(BookRecord record, long value) {
		throw new IllegalStateException("Unexpected call to method 'setAutoIncrementedKey'");
	}

	@Override
	protected Update updateQuery(BookRecord record) {
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

	@Override
	protected Query queryForIdentifyBean(BookRecord record, MongoConverter converter) {
		Object keyid = converter.convertToMongoType(record.getId());
		Query query = new Query(Criteria.where("id").is(keyid));
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
	public BookRecord create(BookRecord record) {
		super.doInsert(record);
		return record;
	}

	@Override
	public boolean delete(BookRecord record) {
		if(super.doExists(record))
			return super.doDelete(record);
		return false;
	}

	@Override
	public boolean deleteById(Integer id) {
		BookRecord record = newInstanceWithPrimaryKey(id);
		if(super.doExists(record))
			return super.doDelete(record);
		return false;
	}

	@Override
	public boolean exists(BookRecord record) {
		return super.doExists(record);
	}

	@Override
	public boolean exists(Integer id) {
		BookRecord record = newInstanceWithPrimaryKey(id);
		return super.doExists(record);
	}

	@Override
	public List<BookRecord> findAll() {
		return super.doSelectAll();
	}

	@Override
	public BookRecord findById(Integer id) {
        BookRecord record = newInstanceWithPrimaryKey(id);
		return super.doSelect(record);
	}

	@Override
	public BookRecord save(BookRecord record) {
		if (super.doExists(record)) {
			super.doUpdate(record);
		} else {
			super.doInsert(record);
		}
        return record;
	}

	@Override
	public boolean update(BookRecord record) {
		return super.doUpdate(record);	
	}
}