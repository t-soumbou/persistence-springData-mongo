
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.BookPersistence ;
import org.demo.persistence.BookPersistenceGenericTest;
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
public class BookPersistenceSpringMongoTest extends BookPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(BookPersistenceSpringMongoTest.class.getName());

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
    	BookPersistence persistenceService = new BookPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}
