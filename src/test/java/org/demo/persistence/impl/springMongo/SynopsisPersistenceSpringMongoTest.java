
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.SynopsisPersistence ;
import org.demo.persistence.SynopsisPersistenceGenericTest;
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
public class SynopsisPersistenceSpringMongoTest extends SynopsisPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(SynopsisPersistenceSpringMongoTest.class.getName());

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
    	SynopsisPersistence persistenceService = new SynopsisPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

