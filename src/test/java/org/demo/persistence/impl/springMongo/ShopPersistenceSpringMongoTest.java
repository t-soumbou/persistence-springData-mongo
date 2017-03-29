
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.ShopPersistence ;
import org.demo.persistence.ShopPersistenceGenericTest;
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
public class ShopPersistenceSpringMongoTest extends ShopPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(ShopPersistenceSpringMongoTest.class.getName());

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
    	ShopPersistence persistenceService = new ShopPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

