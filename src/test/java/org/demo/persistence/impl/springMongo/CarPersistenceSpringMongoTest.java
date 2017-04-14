
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.CarPersistenceGenericTest;
import org.demo.data.record.CarRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.demo.persistence.impl.springMongo.commons.MongoConfig;
import org.demo.persistence.impl.springMongo.commons.MongoEmbedded;
import org.junit.After;
import org.junit.BeforeClass;
import java.util.logging.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * JUnit tests for Car persistence service
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CarPersistenceSpringMongoTest extends CarPersistenceGenericTest {

	private static final Logger LOGGER = Logger.getLogger(CarPersistenceSpringMongoTest.class.getName());
	@Autowired
	private MongoTemplate mongoTemplate;

	@BeforeClass
	public static void startMongoServer() {
		try {
			MongoEmbedded.init();
		} catch (Exception e) {
			LOGGER.info("error start server");
		}
	}

	@After
	public void tearDown() throws Exception {
		mongoTemplate.dropCollection(CarRecord.class);
	}

	@Test
	public void testPersistenceService() throws Exception {

		CarPersistenceSpringMongo persistence = new CarPersistenceSpringMongo();
		persistence.setMongoTemplate(mongoTemplate);
		testPersistenceServiceWithAutoincrementedKey(persistence);
	}

}

