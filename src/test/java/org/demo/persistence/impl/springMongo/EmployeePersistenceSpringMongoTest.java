
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.EmployeePersistence ;
import org.demo.persistence.EmployeePersistenceGenericTest;
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
public class EmployeePersistenceSpringMongoTest extends EmployeePersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(EmployeePersistenceSpringMongoTest.class.getName());

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
    	EmployeePersistence persistenceService = new EmployeePersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

