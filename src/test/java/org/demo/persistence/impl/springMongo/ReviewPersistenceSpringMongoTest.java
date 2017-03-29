
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.ReviewPersistence ;
import org.demo.persistence.ReviewPersistenceGenericTest;
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
public class ReviewPersistenceSpringMongoTest extends ReviewPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(ReviewPersistenceSpringMongoTest.class.getName());

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
    	ReviewPersistence persistenceService = new ReviewPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

