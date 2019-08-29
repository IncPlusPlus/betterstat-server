package io.github.incplusplus.thermostat.test;

import static org.junit.Assert.assertEquals;

import io.github.incplusplus.thermostat.Application;
import io.github.incplusplus.thermostat.persistence.model.User;
import io.github.incplusplus.thermostat.persistence.repositories.UserRepository;
import io.github.incplusplus.thermostat.spring.TestDbConfig;
import io.github.incplusplus.thermostat.spring.TestIntegrationConfig;
import io.github.incplusplus.thermostat.web.dto.UserDto;
import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class,
        TestDbConfig.class,
        TestIntegrationConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationPasswordLiveTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${local.server.port}")
    int port;
    
    @Value("${server.ssl.key-store-password}")
    String p12Password;
    
    @Value("${server.ssl.key-store}")
    String keyStorePath;
    
    @Value("${server.ssl.enabled}")
    boolean sslEnabled;
    
    private FormAuthConfig formConfig;
    
    
    private String URL;
    
    @Before
    public void init() {
        User user = userRepository.findByEmail("test@test.com");
        if (user == null) {
            user = new User();
            user.setFirstName("Test");
            user.setLastName("Test");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail("test@test.com");
            user.setEnabled(true);
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode("test"));
            userRepository.save(user);
        }
    
        RestAssured.port = port;
        RestAssured.baseURI = (sslEnabled ? "https":"http") + "://localhost";
        RestAssured.useRelaxedHTTPSValidation();
//        RestAssured.config().getSSLConfig().with().keyStore(keyStorePath, p12Password);
        URL = "/user/registration";
        formConfig = new FormAuthConfig("/login", "username", "password");
    }
    
    @Ignore("We only want to run this locally and not on CI. We don't want to have to add mail credentials to application.properties")
    @Test
    public void givenInvalidPassword_thenBadRequest() {
        // too short
        assertEquals(HttpStatus.BAD_REQUEST.value(), getResponseForPassword("123"));

        // no special character
        assertEquals(HttpStatus.BAD_REQUEST.value(), getResponseForPassword("1abZRplYU"));

        // no upper case letter
        assertEquals(HttpStatus.BAD_REQUEST.value(), getResponseForPassword("1_abidpsvl"));

        // no number
        assertEquals(HttpStatus.BAD_REQUEST.value(), getResponseForPassword("abZRYUpl"));

        // alphabet sequence
        assertEquals(HttpStatus.BAD_REQUEST.value(), getResponseForPassword("1_abcZRYU"));

        // qwerty sequence
        assertEquals(HttpStatus.BAD_REQUEST.value(), getResponseForPassword("1_abZRTYU"));

        // numeric sequence
        assertEquals(HttpStatus.BAD_REQUEST.value(), getResponseForPassword("123_zqrtU"));

        // valid password
        assertEquals(HttpStatus.OK.value(), getResponseForPassword("12_zwRHIPKA"));
    }

    private int getResponseForPassword(String pass) {
        UserDto userDto = new UserDto();
        final String randomName = UUID.randomUUID().toString();
        userDto.setFirstName(randomName);
        userDto.setLastName("Doe");
        userDto.setEmail(randomName+"@x.com");
        userDto.setPassword(pass);
        userDto.setMatchingPassword(pass);
    
        final RequestSpecification request = RestAssured.given().auth().form("test@test.com", "test", formConfig);
        final Response response = request.with()
                .header("Content-Type","application/json" )
                .header("Accept","application/json" ).body(userDto).post(URL);

//        final Response response = RestAssured.given().body(userDto).accept(MediaType.APPLICATION_JSON_VALUE).post(URL);
        return response.getStatusCode();
    }
}
