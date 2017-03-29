
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.PublisherPersistence ;
import org.demo.persistence.PublisherPersistenceGenericTest;
import org.junit.Test;
import org.demo.persistence.impl.springMongo.commons.MongoUtil;
import org.junit.BeforeClass;
import java.util.logging.*;

/**
 * JUnit tests for Car persistence service
 * 
 * @author Telosys Tools
 *
 */
public class PublisherPersistenceSpringMongoTest extends PublisherPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(PublisherPersistenceSpringMongoTest.class.getName());

	@BeforeClass
	public static void startMongoServer() {
		try {
			MongoUtil.init();
		} catch (Exception e) {
			LOGGER.info("error start server");
		}
	}

	@Test
	public void testPersistenceService() throws Exception {
    	PublisherPersistence persistenceService = new PublisherPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

