
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.BookOrderPersistence ;
import org.demo.persistence.BookOrderPersistenceGenericTest;
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
public class BookOrderPersistenceSpringMongoTest extends BookOrderPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(BookOrderPersistenceSpringMongoTest.class.getName());

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
    	BookOrderPersistence persistenceService = new BookOrderPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

