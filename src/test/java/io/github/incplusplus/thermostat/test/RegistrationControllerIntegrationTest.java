package io.github.incplusplus.thermostat.test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.github.incplusplus.thermostat.Application;
import io.github.incplusplus.thermostat.persistence.model.User;
import io.github.incplusplus.thermostat.persistence.model.VerificationToken;
import io.github.incplusplus.thermostat.persistence.repositories.RoleRepository;
import io.github.incplusplus.thermostat.persistence.repositories.UserRepository;
import io.github.incplusplus.thermostat.persistence.repositories.VerificationTokenRepository;
import io.github.incplusplus.thermostat.spring.TestDbConfig;
import io.github.incplusplus.thermostat.spring.TestIntegrationConfig;
import io.github.incplusplus.thermostat.web.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class,
        TestDbConfig.class,
        TestIntegrationConfig.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
//@Transactional
public class RegistrationControllerIntegrationTest {
    
    @Autowired
    private VerificationTokenRepository tokenRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private String token;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        User user = new User();
        user.setEmail(UUID.randomUUID().toString() + "@example.com");
        user.setPassword(UUID.randomUUID().toString());
        user.setFirstName("First");
        user.setLastName("Last");
        user.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_ADMIN")));

        userRepository.save(user);
        token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationToken.setExpiryDate(Date.from(Instant.now().plus(2, ChronoUnit.DAYS)));

        tokenRepository.save(verificationToken);
    }

    @Test
    public void testRegistrationConfirm() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/registrationConfirm.html?token=" + token));
        resultActions.andExpect(status().is3xxRedirection());
        resultActions.andExpect(model().attribute("message", "Your account verified successfully"));
        resultActions.andExpect(view().name("redirect:/console.html?lang=en"));
    }

    @Test
    public void testRegistrationValidation() throws Exception {

        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("firstName", "");
        param.add("lastName", "");
        param.add("email", "");
        param.add("password", "");
        param.add("matchingPassword", "");
    
        UserDto anObject = new UserDto();
        anObject.setFirstName("");
        anObject.setLastName("");
        anObject.setEmail("");
        anObject.setPassword("");
        anObject.setMatchingPassword("");
    
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(anObject );

        ResultActions resultActions = this.mockMvc.perform(post("/user/registration").content(requestJson).contentType(APPLICATION_JSON_UTF8));
        resultActions.andExpect(status().is(400));
        resultActions.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.error", is("InvaliduserDto")))
                .andExpect(jsonPath("$.message", containsString("{\"field\":\"lastName\",\"defaultMessage\":\"Length must be greater than 1\"}")));
    }
}