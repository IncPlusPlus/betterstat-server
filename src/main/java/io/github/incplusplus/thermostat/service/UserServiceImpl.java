package io.github.incplusplus.thermostat.service;

import io.github.incplusplus.thermostat.persistence.model.Client;
import io.github.incplusplus.thermostat.persistence.repositories.ClientRepository;
import io.github.incplusplus.thermostat.persistence.repositories.PasswordResetTokenRepository;
import io.github.incplusplus.thermostat.persistence.repositories.RoleRepository;
import io.github.incplusplus.thermostat.persistence.repositories.VerificationTokenRepository;
import io.github.incplusplus.thermostat.web.dto.UserDto;
import io.github.incplusplus.thermostat.web.error.UserAlreadyExistException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService
{
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";
	
	public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
	public static String APP_NAME = "SpringRegistration";
	
	// API
	
	@Override
	public Client registerNewUserAccount(final UserDto accountDto) {
		if (emailExists(accountDto.getEmail())) {
			throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());
		}
		final Client client = new Client();
		
		client.setFirstName(accountDto.getFirstName());
		client.setLastName(accountDto.getLastName());
		client.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		client.setEmail(accountDto.getEmail());
		client.setUsing2FA(accountDto.isUsing2FA());
		client.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_USER")));
		return clientRepository.save(client);
	}
	
	@Override
	public Client getUser(final String verificationToken) {
		final VerificationToken token = tokenRepository.findByToken(verificationToken);
		if (token != null) {
			return token.getUser();
		}
		return null;
	}
	
	@Override
	public VerificationToken getVerificationToken(final String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}
	
	@Override
	public void saveRegisteredUser(final Client client) {
		clientRepository.save(client);
	}
	
	@Override
	public void deleteUser(final Client client) {
		final VerificationToken verificationToken = tokenRepository.findByUser(client);
		
		if (verificationToken != null) {
			tokenRepository.delete(verificationToken);
		}
		
		final PasswordResetToken passwordToken = passwordTokenRepository.findByUser(client);
		
		if (passwordToken != null) {
			passwordTokenRepository.delete(passwordToken);
		}
		
		clientRepository.delete(client);
	}
	
	@Override
	public void createVerificationTokenForUser(final Client client, final String token) {
		final VerificationToken myToken = new VerificationToken(token, client);
		tokenRepository.save(myToken);
	}
	
	@Override
	public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
		VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID()
				.toString());
		vToken = tokenRepository.save(vToken);
		return vToken;
	}
	
	@Override
	public void createPasswordResetTokenForUser(final Client client, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, client);
		passwordTokenRepository.save(myToken);
	}
	
	@Override
	public Client findUserByEmail(final String email) {
		return clientRepository.findByEmail(email);
	}
	
	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordTokenRepository.findByToken(token);
	}
	
	@Override
	public Client getUserByPasswordResetToken(final String token) {
		return passwordTokenRepository.findByToken(token)
				.getUser();
	}
	
	@Override
	public Optional<Client> getUserByID(final ObjectId id) {
		return clientRepository.findById(id);
	}
	
	@Override
	public void changeUserPassword(final Client client, final String password) {
		client.setPassword(passwordEncoder.encode(password));
		clientRepository.save(client);
	}
	
	@Override
	public boolean checkIfValidOldPassword(final Client client, final String oldPassword) {
		return passwordEncoder.matches(oldPassword, client.getPassword());
	}
	
	@Override
	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = tokenRepository.findByToken(token);
		if (verificationToken == null) {
			return TOKEN_INVALID;
		}
		
		final Client client = verificationToken.getUser();
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate()
				.getTime()
				- cal.getTime()
				.getTime()) <= 0) {
			tokenRepository.delete(verificationToken);
			return TOKEN_EXPIRED;
		}
		
		client.setEnabled(true);
		// tokenRepository.delete(verificationToken);
		clientRepository.save(client);
		return TOKEN_VALID;
	}
	
	@Override
	public String generateQRUrl(Client client) throws UnsupportedEncodingException
	{
		return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, client.getEmail(), client.getSecret(), APP_NAME), StandardCharsets.UTF_8.name());
	}
	
	@Override
	public Client updateUser2FA(boolean use2FA) {
		final Authentication curAuth = SecurityContextHolder.getContext()
				.getAuthentication();
		Client currentClient = (Client) curAuth.getPrincipal();
		currentClient.setUsing2FA(use2FA);
		currentClient = clientRepository.save(currentClient);
		final Authentication auth = new UsernamePasswordAuthenticationToken(currentClient, currentClient.getPassword(), curAuth.getAuthorities());
		SecurityContextHolder.getContext()
				.setAuthentication(auth);
		return currentClient;
	}
	
	private boolean emailExists(final String email) {
		return clientRepository.findByEmail(email) != null;
	}
	
	@Override
	public List<String> getUsersFromSessionRegistry() {
		return sessionRegistry.getAllPrincipals()
				.stream()
				.filter((u) -> !sessionRegistry.getAllSessions(u, false)
						.isEmpty())
				.map(o -> {
					if (o instanceof Client) {
						return ((Client) o).getEmail();
					} else {
						return o.toString();
					}
				})
				.collect(Collectors.toList());
		
	}
	
}
