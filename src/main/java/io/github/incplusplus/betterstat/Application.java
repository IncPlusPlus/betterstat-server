package io.github.incplusplus.betterstat;

import com.mongodb.MongoClientSettings;
import com.mongodb.connection.SocketSettings;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class Application {

  private static final Log log = LogFactory.getLog(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
    propsConfig.setLocation(new ClassPathResource("git.properties"));
    propsConfig.setIgnoreResourceNotFound(true);
    propsConfig.setIgnoreUnresolvablePlaceholders(true);
    return propsConfig;
  }

  @Bean
  public MongoClientSettings mongoOptions() {
    return MongoClientSettings.builder()
        .applyToSocketSettings(
            builder ->
                builder.applySettings(
                    SocketSettings.builder().connectTimeout(15, TimeUnit.SECONDS).build()))
        .build();
  }
}
