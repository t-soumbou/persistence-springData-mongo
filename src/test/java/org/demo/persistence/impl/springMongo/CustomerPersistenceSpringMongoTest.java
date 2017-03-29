
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.CustomerPersistence ;
import org.demo.persistence.CustomerPersistenceGenericTest;
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
public class CustomerPersistenceSpringMongoTest extends CustomerPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(CustomerPersistenceSpringMongoTest.class.getName());

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
    	CustomerPersistence persistenceService = new CustomerPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

