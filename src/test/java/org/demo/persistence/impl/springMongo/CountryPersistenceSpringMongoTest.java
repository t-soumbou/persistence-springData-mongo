
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.CountryPersistence ;
import org.demo.persistence.CountryPersistenceGenericTest;
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
public class CountryPersistenceSpringMongoTest extends CountryPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(CountryPersistenceSpringMongoTest.class.getName());

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
    	CountryPersistence persistenceService = new CountryPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

