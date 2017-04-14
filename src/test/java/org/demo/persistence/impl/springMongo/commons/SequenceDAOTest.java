package org.demo.persistence.impl.springMongo.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.demo.data.record.AuthorRecord;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Spring Mongo Sequence class Integration Test <br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class SequenceDAOTest {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Logger LOGGER = Logger.getLogger(SequenceDAOTest.class.getName());
	
	@BeforeClass
	public static void startMongoServer() {
		try {
			MongoEmbedded.init();
		} catch (Exception e) {
			LOGGER.info("error start server");
		}
	}

	@After
	public void tearDown() throws Exception {
		mongoTemplate.dropCollection(AuthorRecord.class);
	}

	@Test
	public void test1() throws Exception {
		LOGGER.info("--- test1");
    	SequenceDAO sequence = new SequenceDAO(mongoTemplate,"seqTest");

    	assertEquals( 0L, sequence.currVal("seqTest") );

    	assertEquals( 1L, sequence.nextVal("seqTest") );
    	LOGGER.info("Sequence currVal() = " + sequence.currVal("seqTest") );
    	
    	for ( int i=0 ; i < 10 ; i++ ) {
        	sequence.nextVal("seqTest") ;
    	}
    	LOGGER.info("Sequence currVal() = " + sequence.currVal("seqTest") );
    	assertEquals( 11L, sequence.currVal("seqTest") );

		boolean b = sequence.delete("seqTest");
        assertTrue(b);
        LOGGER.info("Sequence deleted");	
	}	
}
