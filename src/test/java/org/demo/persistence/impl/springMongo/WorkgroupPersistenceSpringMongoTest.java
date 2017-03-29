
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.WorkgroupPersistence ;
import org.demo.persistence.WorkgroupPersistenceGenericTest;
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
public class WorkgroupPersistenceSpringMongoTest extends WorkgroupPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(WorkgroupPersistenceSpringMongoTest.class.getName());

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
    	WorkgroupPersistence persistenceService = new WorkgroupPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

