
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.AuthorPersistence ;
import org.demo.persistence.AuthorPersistenceGenericTest;
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
public class AuthorPersistenceSpringMongoTest extends AuthorPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(AuthorPersistenceSpringMongoTest.class.getName());

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
    	AuthorPersistence persistenceService = new AuthorPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

