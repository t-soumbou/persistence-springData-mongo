
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.PublisherPersistenceGenericTest;
import org.demo.data.record.PublisherRecord;
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
public class PublisherPersistenceSpringMongoTest extends PublisherPersistenceGenericTest {

	private static final Logger LOGGER = Logger.getLogger(PublisherPersistenceSpringMongoTest.class.getName());
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
		mongoTemplate.dropCollection(PublisherRecord.class);
	}

	@Test
	public void testPersistenceService() throws Exception {

		PublisherPersistenceSpringMongo persistence = new PublisherPersistenceSpringMongo();
		persistence.setMongoTemplate(mongoTemplate);
    	testPersistenceService(persistence);
	}

}

