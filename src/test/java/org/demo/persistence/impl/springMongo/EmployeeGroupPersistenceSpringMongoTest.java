
package org.demo.persistence.impl.springMongo;

import org.demo.persistence.EmployeeGroupPersistence ;
import org.demo.persistence.EmployeeGroupPersistenceGenericTest;
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
public class EmployeeGroupPersistenceSpringMongoTest extends EmployeeGroupPersistenceGenericTest {
	private static final Logger LOGGER = Logger.getLogger(EmployeeGroupPersistenceSpringMongoTest.class.getName());

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
    	EmployeeGroupPersistence persistenceService = new EmployeeGroupPersistenceSpringMongo();
    	testPersistenceService(persistenceService);
	}

}

