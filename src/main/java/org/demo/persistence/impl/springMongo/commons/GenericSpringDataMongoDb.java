/*
 * Created on 2017-03-29 ( Date ISO 2017-03-29 - Time 11:30:14 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.0.0
 */
/*
 * Created on 2017-03-29 ( Time 11:30:14 )
 * Generated by Telosys Tools Generator ( version 3.0.0 )
 */
package org.demo.persistence.impl.springMongo.commons;


import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

/**
 * Generic abstract class for basic MONGO DAO
 * 
 * @author telosys
 *
 * @param <T>
 */

public abstract class GenericSpringDataMongoDb<T> {
	private String entityName;
	protected final Class<T> entityClass;
	private MongoTemplate mongoTemplate;
	private MongoConverter converter;
	
	//-----------------------------------------------------------------------------------------
	/**
	 * Constructor
	 * @param entityName
	 * @param entityClass
	 */
	protected GenericSpringDataMongoDb(String entityName, Class<T> entityClass) {
		this.entityName = entityName;
		this.entityClass = entityClass;
		this.mongoTemplate = SpringGetConfig.getMongotemplate();
		this.converter= mongoTemplate.getConverter();

	}
	
	//-----------------------------------------------------------------------------------------
	// ABSTRACT METHODS
	//-----------------------------------------------------------------------------------------
	/**
	 * Abstract method used to set the computed value for an auto-incremented
	 * key <br>
	 * This method is supposed to set the key if the bean has an
	 * auto-incremented key <br>
	 * or to throw an exception if the bean doesn't have an auto-incremented key
	 * @param bean
	 * @param id
	 */
	protected abstract void setAutoIncrementedKey(T bean, long id);

	/**
	 * return a query for update bean
	 * @param bean
	 * @return
	 */
	protected abstract Update updateQuery(T bean);

	/**
	 * return a query for identify bean
	 * @param bean
	 * @param converter
	 * @return
	 */
	protected abstract Query queryForIdentifyBean(T bean, MongoConverter converter);
	
	//-----------------------------------------------------------------------------------------
	// REDIS PERSISTENCE METHODS
	//-----------------------------------------------------------------------------------------
	/**
	 * Loads the given bean from the database using its primary key
	 * @param bean
	 * @return bean if found and loaded, null if not found
	 */
	protected T doSelect(T bean) {
		Query query = queryForIdentifyBean(bean,converter);
		return mongoTemplate.findOne(query, entityClass);

	}

	/**
	 * Returns all the occurrences existing in the database
	 * 
	 * @return
	 */
	protected List<T> doSelectAll() {
		return mongoTemplate.findAll(entityClass);
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	protected long doInsertIncr(T bean) {
		SequenceDAO sequenceDAO = new SequenceDAO(mongoTemplate,entityName);
		do {
			try {
				setAutoIncrementedKey(bean, sequenceDAO.nextVal(entityName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (doExists(bean) == true);
		mongoTemplate.insert(bean);
		return sequenceDAO.currVal(entityName);
	}

	/**
	 * Inserts the given bean in the database <br>
	 * only if the key does not already exist
	 * 
	 * @param bean
	 * @return Indicates if the insert is successful
	 */
	protected void doInsert(T bean) {
		mongoTemplate.insert(bean);
	}

	/**
	 * Updates the given bean in the database (Mongo)
	 * 
	 * @param bean
	 */
	protected boolean doUpdate(T bean) {
		Query query = queryForIdentifyBean(bean,converter);
		Update update = updateQuery(bean);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
	
	/**
	 * Saves the given bean in the database (creates or update using Spring Data "save")
	 * @param bean
	 */
	protected void doSave(T bean) {
		mongoTemplate.save(bean, entityName);
	}

	/**
	 * Deletes the given bean in the database (Mongo)
	 * 
	 * @param bean
	 * @return the Mongo return code (i.e. the row count affected by the DELETE
	 *         operation : 0 or 1 )
	 */
	protected boolean doDelete(T bean) {
		Query query = queryForIdentifyBean(bean, converter);
		mongoTemplate.remove(query, entityClass);
		return true;
	}

	/**
	 * Checks if the given bean exists in the database
	 * 
	 * @param bean
	 * @return true if bean exist false else 
	 */
	protected boolean doExists(T bean) {
		Query query = queryForIdentifyBean(bean,converter);
		return mongoTemplate.exists(query, entityClass);

	}

	/**
	 * Counts all the occurrences in the table
	 * 
	 * @return
	 */
	protected long doCountAll() {
		return mongoTemplate.count(new Query(), entityClass);
	}
}