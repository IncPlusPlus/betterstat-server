package io.github.incplusplus.thermostat.task;

import io.github.incplusplus.thermostat.persistence.repositories.PasswordResetTokenRepository;
import io.github.incplusplus.thermostat.persistence.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@Transactional
@Component
public class TokensPurgeTask
{

    @Autowired
    VerificationTokenRepository tokenRepository;

    @Autowired
    PasswordResetTokenRepository passwordTokenRepository;

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {

        Date now = Date.from(Instant.now());

        passwordTokenRepository.deleteAllByExpiryDateLessThanEqual(now);
        tokenRepository.deleteAllByExpiryDateLessThanEqual(now);
    }
}
