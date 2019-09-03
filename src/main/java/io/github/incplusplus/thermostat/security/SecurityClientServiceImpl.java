package io.github.incplusplus.thermostat.security;

import io.github.incplusplus.thermostat.persistence.model.PasswordResetToken;
import io.github.incplusplus.thermostat.persistence.model.User;
import io.github.incplusplus.thermostat.persistence.repositories.PasswordResetTokenRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Calendar;

public class SecurityClientServiceImpl implements SecurityClientService
{
	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;
	
	// API
	
	@Override
	public String validatePasswordResetToken(ObjectId id, String token) {
		final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
		if ((passToken == null) || (!passToken.getClient().get_id().equals(id))) {
			return "invalidToken";
		}
		
		final Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return "expired";
		}
		
		final User user = passToken.getClient();
		final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return null;
	}
}
