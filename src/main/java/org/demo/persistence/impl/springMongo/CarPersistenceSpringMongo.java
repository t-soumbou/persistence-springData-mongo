package org.demo.persistence.impl.springMongo;

import java.util.List;


import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.WriteResult;
import org.demo.persistence.impl.springMongo.commons.SequenceDAO;

import org.demo.data.record.CarRecord;
import org.demo.persistence.CarPersistence;

/**
 * Car persistence service - SpringMongo implementation 
 */
@Named("CarPersistence")
public class CarPersistenceSpringMongo implements CarPersistence {

	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Class<CarRecord> entityClass = CarRecord.class;

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
	private CarRecord newInstanceWithPrimaryKey(int id) {
		CarRecord record = new CarRecord();
        record.setId(id); 
		return record;
	}

	
	private void setAutoIncrementedKey(CarRecord record, long value) {
		record.setId((int)value);
	}

	private Update updateQuery(CarRecord record) {
		Update update = new Update();
		update.set("lastname", record.getLastname());
		update.set("firstname", record.getFirstname());
		return update;
	}

	protected Query queryForIdentifyBean(CarRecord record) {
		Query query = new Query(Criteria.where("id").is(record.getId()));
	    return query;
	}

	private void inserIncr(CarRecord record) {
		SequenceDAO sequenceDAO = new SequenceDAO(mongoTemplate, "CarRecord");
		do {
			try {
				setAutoIncrementedKey(record, sequenceDAO.nextVal("CarRecord"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (exists(record) == true);
		mongoTemplate.insert(record);
	}
	//-------------------------------------------------------------------------------------
	// Persistence interface implementations
	//-------------------------------------------------------------------------------------
	@Override
	public long countAll() {
		return mongoTemplate.count(new Query(), CarRecord.class);
	}
	
	@Override
	public CarRecord create(CarRecord record) {
		inserIncr(record);
		return record;
	}

	@Override
	public boolean delete(CarRecord record) {
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean deleteById(Integer id) {
		CarRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		WriteResult result = mongoTemplate.remove(query, entityClass);
		return result.getN() > 0;
	}

	@Override
	public boolean exists(CarRecord record) {
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public boolean exists(Integer id) {
		CarRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.exists(query, entityClass);
	}

	@Override
	public List<CarRecord> findAll() {
		return mongoTemplate.findAll(entityClass);
	}

	@Override
	public CarRecord findById(Integer id) {
        CarRecord record = newInstanceWithPrimaryKey(id);
		Query query = queryForIdentifyBean(record);
		return mongoTemplate.findOne(query, entityClass);
	}

	@Override
	public CarRecord save(CarRecord record) {
		mongoTemplate.save(record);
		return record;
	}

	@Override
	public boolean update(CarRecord record) {
		Query query = queryForIdentifyBean(record);
		Update update = updateQuery(record);
		WriteResult result = mongoTemplate.updateFirst(query, update, entityClass);
		return result.getN() > 0;
	}
}
