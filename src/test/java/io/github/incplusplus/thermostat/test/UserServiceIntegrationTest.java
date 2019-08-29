package io.github.incplusplus.thermostat.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import io.github.incplusplus.thermostat.persistence.repositories.RoleRepository;
import io.github.incplusplus.thermostat.persistence.repositories.UserRepository;
import io.github.incplusplus.thermostat.persistence.repositories.VerificationTokenRepository;
import io.github.incplusplus.thermostat.persistence.model.Privilege;
import io.github.incplusplus.thermostat.persistence.model.Role;
import io.github.incplusplus.thermostat.persistence.model.User;
import io.github.incplusplus.thermostat.persistence.model.VerificationToken;
import io.github.incplusplus.thermostat.service.IUserService;
import io.github.incplusplus.thermostat.service.UserService;
import io.github.incplusplus.thermostat.spring.LoginNotificationConfig;
import io.github.incplusplus.thermostat.spring.ServiceConfig;
import io.github.incplusplus.thermostat.spring.TestDbConfig;
import io.github.incplusplus.thermostat.spring.TestIntegrationConfig;
import io.github.incplusplus.thermostat.validation.EmailExistsException;
import io.github.incplusplus.thermostat.web.dto.UserDto;
import io.github.incplusplus.thermostat.web.error.UserAlreadyExistException;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        TestDbConfig.class,
        ServiceConfig.class, TestIntegrationConfig.class, LoginNotificationConfig.class})
public class UserServiceIntegrationTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;
    
    @Bean
    @Primary
    public JavaMailSender javaMailSender() {
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        return javaMailSender;
    }

    @Test
    public void givenNewUser_whenRegistered_thenCorrect() throws EmailExistsException {
        final String userEmail = UUID.randomUUID().toString();
        final UserDto userDto = createUserDto(userEmail);

        final User user = userService.registerNewUserAccount(userDto);

        assertNotNull(user);
        assertNotNull(user.getEmail());
        assertEquals(userEmail, user.getEmail());
        assertNotNull(user.getId());
    }

    @Test
    public void givenDetachedUser_whenAccessingEntityAssociations_thenCorrect() throws EmailExistsException {
        final User user = registerUser();
        assertNotNull(user.getRoles());
        user.getRoles().stream().filter(r -> r != null).forEach(Role::getId);
        user.getRoles().stream().filter(r -> r != null).forEach(Role::getName);
        user.getRoles().stream().filter(r -> r != null).forEach(r -> r.getPrivileges());
        user.getRoles().stream().filter(r -> r != null).forEach(r -> r.getPrivileges().stream().filter(p -> p != null).forEach(Privilege::getId));
        user.getRoles().stream().filter(r -> r != null).forEach(r -> r.getPrivileges().stream().filter(p -> p != null).forEach(Privilege::getName));
        user.getRoles().stream().filter(r -> r != null).forEach(r -> r.getPrivileges().stream().map(Privilege::getRoles).forEach(Assert::assertNotNull));
    }

    @Test
    public void givenDetachedUser_whenServiceLoadById_thenCorrect() throws EmailExistsException {
        final User user = registerUser();
        final User user2 = userService.getUserByID(user.getId()).get();
        assertEquals(user, user2);
    }

    @Test
    public void givenDetachedUser_whenServiceLoadByEmail_thenCorrect() throws EmailExistsException {
        final User user = registerUser();
        final User user2 = userService.findUserByEmail(user.getEmail());
        assertEquals(user, user2);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void givenUserRegistered_whenDuplicatedRegister_thenCorrect() {
        final String email = UUID.randomUUID().toString();
        final UserDto userDto = createUserDto(email);

        userService.registerNewUserAccount(userDto);
        userService.registerNewUserAccount(userDto);
    }

    @Transactional
    public void givenUserRegistered_whenDtoRoleAdmin_thenUserNotAdmin() {
        assertNotNull(roleRepository);
        final UserDto userDto = new UserDto();
        userDto.setEmail(UUID.randomUUID().toString());
        userDto.setPassword("SecretPassword");
        userDto.setMatchingPassword("SecretPassword");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        assertNotNull(roleRepository.findRoleByName("ROLE_ADMIN"));
        final ObjectId adminRoleId = roleRepository.findRoleByName("ROLE_ADMIN").getId();
        assertNotNull(adminRoleId);
        userDto.setRole(adminRoleId);
        final User user = userService.registerNewUserAccount(userDto);
        assertFalse(user.getRoles().stream().map(Role::getId).anyMatch(ur -> ur.equals(adminRoleId)));
    }

    @Test
    public void givenUserRegistered_whenCreateToken_thenCorrect() {
        final User user = registerUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
    }

    @Test
    public void givenUserRegistered_whenCreateTokenCreateDuplicate_thenCorrect() {
        final User user = registerUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        userService.createVerificationTokenForUser(user, token);
    }

    @Test
    public void givenUserAndToken_whenLoadToken_thenCorrect() {
        final User user = registerUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        final VerificationToken verificationToken = userService.getVerificationToken(token);
        assertNotNull(verificationToken);
        assertNotNull(verificationToken.getId());
        assertNotNull(verificationToken.getUser());
        assertEquals(user, verificationToken.getUser());
        assertEquals(user.getId(), verificationToken.getUser().getId());
        assertEquals(token, verificationToken.getToken());
        assertTrue(verificationToken.getExpiryDate().toInstant().isAfter(Instant.now()));
    }

    @Test
    public void givenUserAndToken_whenRemovingUser_thenCorrect() {
        final User user = registerUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        userService.deleteUser(user);
    }

//    @Test(expected = DataIntegrityViolationException.class)
    public void givenUserAndToken_whenRemovingUserByDao_thenFKViolation() {
        final User user = registerUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        final ObjectId userId = user.getId();
        userService.getVerificationToken(token).getId();
        userRepository.deleteById(userId);
    }

    @Test
    public void givenUserAndToken_whenRemovingTokenThenUser_thenCorrect() {
        final User user = registerUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        final ObjectId userId = user.getId();
        final ObjectId tokenId = userService.getVerificationToken(token).getId();
        tokenRepository.deleteById(tokenId);
        userRepository.deleteById(userId);
    }

    @Test
    public void givenUserAndToken_whenRemovingToken_thenCorrect() {
        final User user = registerUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        final ObjectId tokenId = userService.getVerificationToken(token).getId();
        tokenRepository.deleteById(tokenId);
    }

    @Test
    public void givenUserAndToken_whenNewTokenRequest_thenCorrect() {
        final User user = registerUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        final VerificationToken origToken = userService.getVerificationToken(token);
        final VerificationToken newToken = userService.generateNewVerificationToken(token);
        assertNotEquals(newToken.getToken(), origToken.getToken());
        assertNotEquals(newToken.getExpiryDate(), origToken.getExpiryDate());
        assertNotEquals(newToken, origToken);
    }

    @Test
    public void givenTokenValidation_whenValid_thenUserEnabled_andTokenRemoved() {
        User user = registerUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        final ObjectId userId = user.getId();
        final String token_status = userService.validateVerificationToken(token);
        assertEquals(token_status, UserService.TOKEN_VALID);
        user = userService.getUserByID(userId).get();
        assertTrue(user.isEnabled());
    }

    @Test
    public void givenTokenValidation_whenInvalid_thenCorrect() {
        final User user = registerUser();
        final String token = UUID.randomUUID().toString();
        final String invalid_token = "INVALID_" + UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        userService.getVerificationToken(token).getId();
        final String token_status = userService.validateVerificationToken(invalid_token);
        token_status.equals(UserService.TOKEN_INVALID);
    }

    @Test
    public void givenTokenValidation_whenExpired_thenCorrect() {
        final User user = registerUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationTokenForUser(user, token);
        user.getId();
        final VerificationToken verificationToken = userService.getVerificationToken(token);
        verificationToken.setExpiryDate(Date.from(verificationToken.getExpiryDate().toInstant().minus(2, ChronoUnit.DAYS)));
        tokenRepository.save(verificationToken);
        final String token_status = userService.validateVerificationToken(token);
        assertNotNull(token_status);
        token_status.equals(UserService.TOKEN_EXPIRED);
    }

    //

    private UserDto createUserDto(final String email) {
        final UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setPassword("SecretPassword");
        userDto.setMatchingPassword("SecretPassword");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setRole(ObjectId.get());
        return userDto;
    }

    private User registerUser() {
        final String email = UUID.randomUUID().toString();
        final UserDto userDto = createUserDto(email);
        final User user = userService.registerNewUserAccount(userDto);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(email, user.getEmail());
        return user;
    }

}
