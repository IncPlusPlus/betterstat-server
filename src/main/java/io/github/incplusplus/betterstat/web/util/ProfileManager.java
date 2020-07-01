package io.github.incplusplus.betterstat.web.util;

import io.github.incplusplus.betterstat.Application;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProfileManager {

  private static final Log log = LogFactory.getLog(Application.class);

  @Value("${spring.profiles.active}")
  private String activeProfiles;

  public void getActiveProfiles() {
    for (String profileName : activeProfiles.split(",")) {
      log.info("Currently active profile - " + profileName);
    }
  }

  @PostConstruct
  public void printActiveProfiles() {
    getActiveProfiles();
  }
}
