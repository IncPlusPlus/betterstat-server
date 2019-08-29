package io.github.incplusplus.thermostat.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import io.github.incplusplus.thermostat.Application;
import io.github.incplusplus.thermostat.persistence.repositories.UserRepository;
import io.github.incplusplus.thermostat.persistence.repositories.VerificationTokenRepository;
import io.github.incplusplus.thermostat.persistence.model.User;
import io.github.incplusplus.thermostat.persistence.model.VerificationToken;
import io.github.incplusplus.thermostat.spring.TestDbConfig;
import io.github.incplusplus.thermostat.spring.TestTaskConfig;
import io.github.incplusplus.thermostat.task.TokensPurgeTask;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

//@Import(Application.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        TestDbConfig.class,
		TestTaskConfig.class
		})
//@AutoConfigureDataMongo
public class TokenExpirationIntegrationTest
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@Autowired
	private TokensPurgeTask tokensPurgeTask;
	
	private ObjectId token_id;
	private ObjectId user_id;
	
	//
	
	@Before
	public void givenUserWithExpiredToken()
	{
		User user = new User();
		user.setEmail(UUID.randomUUID().toString() + "@example.com");
		user.setPassword(UUID.randomUUID().toString());
		user.setFirstName("First");
		user.setLastName("Last");
		
		userRepository.save(user);
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token, user);
		verificationToken.setExpiryDate(Date.from(Instant.now().minus(2, ChronoUnit.DAYS)));
		
		tokenRepository.save(verificationToken);
		
		token_id = verificationToken.getId();
		user_id = user.getId();
	}
	
	@Test
	public void whenContextLoad_thenCorrect()
	{
		assertNotNull(user_id);
		assertNotNull(token_id);
		assertTrue(userRepository.findById(user_id).isPresent());
		
		Optional<VerificationToken> verificationToken = tokenRepository.findById(token_id);
		assertTrue(verificationToken.isPresent());
		assertTrue(tokenRepository.findAllByExpiryDateLessThan(Date.from(Instant.now())).anyMatch((token) -> token.equals(verificationToken.get())));
	}
	
	@After
	public void flushAfter()
	{
//		entityManager.flush();
	}
	
	@Test
	public void whenRemoveByGeneratedQuery_thenCorrect()
	{
		tokenRepository.deleteByExpiryDateLessThan(Date.from(Instant.now()));
		assertEquals(0, tokenRepository.count());
	}
	
	@Test
	public void whenRemoveByJPQLQuery_thenCorrect()
	{
		tokenRepository.deleteAllByExpiryDateLessThanEqual(Date.from(Instant.now()));
		assertEquals(0, tokenRepository.count());
	}
	
	@Test
	public void whenPurgeTokenTask_thenCorrect()
	{
		tokensPurgeTask.purgeExpired();
		assertFalse(tokenRepository.findById(token_id).isPresent());
	}
}
