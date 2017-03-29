
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.BookOrderItemPersistence ;
import org.demo.persistence.BookOrderItemPersistenceGenericTest;
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
public class BookOrderItemPersistenceSpringMongoTest extends BookOrderItemPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(BookOrderItemPersistenceSpringMongoTest.class.getName());

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
    	BookOrderItemPersistence persistenceService = new BookOrderItemPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

