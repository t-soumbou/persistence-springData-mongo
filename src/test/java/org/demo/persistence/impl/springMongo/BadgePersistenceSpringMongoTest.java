
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.BadgePersistence ;
import org.demo.persistence.BadgePersistenceGenericTest;
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
public class BadgePersistenceSpringMongoTest extends BadgePersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(BadgePersistenceSpringMongoTest.class.getName());

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
    	BadgePersistence persistenceService = new BadgePersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

